"""
该脚本用于模拟登录北理珠个人门户页面获得学生信息
一、Java 输入用户名和密码后将数据传入该脚本, 先验证用户名
二、截取验证码图片, 通过 EasyOCR 识别验证码
三、利用 Selenium 模拟手动填写用户名、密码和验证码
四、如果登录失败返回状态码给 Java， 并让用户重新填写信息，若成功登录获得 Ticket 和 Cookie 重定向到个人门户管理中心
四、抓包获得学生信息, 并返回给 Java
"""
import threading

from selenium import webdriver
from selenium.common import NoSuchElementException
from selenium.webdriver.edge.service import Service
from selenium.webdriver.edge.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from PIL import Image, ImageFilter, ImageEnhance
import requests
import easyocr
import time
import os
import sys
import json
import io
import certifi
from requests.adapters import HTTPAdapter
import ssl

from datetime import datetime
# 日志文件路径
LOG_FILE_PATH = "logs/script_log.txt"
# 确保日志目录存在
log_dir = os.path.dirname(LOG_FILE_PATH)
def log_to_file(message):
    if not os.path.exists(log_dir):
        os.makedirs(log_dir)
    """将日志信息以 UTF-8 编码写入文件"""
    with open(LOG_FILE_PATH, "a", encoding="utf-8") as log_file:
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        log_file.write(f"[{timestamp}] {message}\n")

driver = None
edge_options = None

# 配置参数
os.environ["CUDA_VISIBLE_DEVICES"] = "" # 禁用 GPU
edge_driver_path = r'D:\edgedriver\msedgedriver.exe' # EdgeDriver 路径
reader = easyocr.Reader(['en']) # 初始化 EasyOCR 阅读器（支持英文）
max_retries = 100 # 尝试登录的最大次数

# 自定义 HTTPAdapter，强制使用 TLSv1.2 并降低安全级别
class TLSAdapter(HTTPAdapter):
    def init_poolmanager(self, *args, **kwargs):
        context = ssl.create_default_context()
        context.minimum_version = ssl.TLSVersion.TLSv1_2  # 强制使用 TLSv1.2
        context.set_ciphers("DEFAULT:@SECLEVEL=1")  # 降低安全级别
        kwargs["ssl_context"] = context
        return super().init_poolmanager(*args, **kwargs)

# 验证用户名是否正确
def is_correct_username(username):
    # 请求URL
    url = "https://cas.bitzh.edu.cn/isuserlogin"

    # 请求头
    headers = {
        "Accept": "*/*",
        "Accept-Encoding": "gzip, deflate, br, zstd",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
        "Cache-Control": "no-cache",
        "Connection": "keep-alive",
        "Content-Length": "17",
        "Content-Type": "application/x-www-form-urlencoded",
        "Cookie": "SESSION=OTdkMDY4MGYtOWE5MC00YWMzLTk2ZGYtZGNmMDI5OTdjMmZj",
        "Host": "cas.bitzh.edu.cn",
        "Origin": "https://cas.bitzh.edu.cn",
        "Pragma": "no-cache",
        "Referer": "https://cas.bitzh.edu.cn/cas3/login?service=https://s.bitzh.edu.cn",
        "Sec-Ch-Ua": '"Not(A:Brand";v="99", "Microsoft Edge";v="133", "Chromium";v="133"',
        "Sec-Ch-Ua-Mobile": "?0",
        "Sec-Ch-Ua-Platform": '"Windows"',
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36 Edg/133.0.0.0",
        "X-Requested-With": "XMLHttpRequest"
    }

    # 请求参数
    data = { "username": username, "password": "1" }

    try:
        # 创建会话并挂载自定义适配器
        session = requests.Session()
        session.mount("https://", TLSAdapter())

        session.trust_env = False  # 忽略系统代理设置
        # 发送POST请求，启用SSL验证
        response = session.post(url, headers=headers, data=data, verify=certifi.where())

        # 检查请求是否成功
        if response.status_code == 200:
            # 解析响应内容
            response_json = response.json()

            # 获取validation的值
            validation = response_json.get("validation")

            # print("validation: ", validation)
            # 判断validation的值
            if validation == "0":
                return {'message': '用户名错误', 'data': None}
            elif validation == "1" or validation == "3":
                return {'message': '本月第一次登录, 请前往 https://cas.bitzh.edu.cn/cas3/login?service=https://s.bitzh.edu.cn 进行验证', 'data': None}
        else:
            return {'message': f"请求失败，状态码: {response.status_code}", 'data': None}
    except requests.exceptions.SSLError as e:
        return {'message': f"SSL错误: {str(e)}", 'data': None}
    except requests.exceptions.RequestException as e:
        return {'message': f"请求异常: {str(e)}", 'data': None}
# endregion

# region # 处理验证码图片
def process_captcha(image_path):
    """
    对验证码图片进行预处理，包括灰度化、去噪、增强对比度等。
    :param image_path: 验证码图片路径
    :return: 识别出的验证码文本
    """
    image = Image.open(image_path) # 打开图片
    image = image.convert('L') # 灰度化
    image = image.filter(ImageFilter.MedianFilter(size=3)) # 去噪（中值滤波）
    enhancer = ImageEnhance.Contrast(image) # 增强对比度
    image = enhancer.enhance(2.0)

    processed_image_path = "src/main/resources/python/processed_captcha.png"
    # 创建目录（如果不存在）
    os.makedirs(os.path.dirname(processed_image_path), exist_ok=True)

    # 保存处理后的图片到指定路径
    image.save(processed_image_path)

    # 使用 EasyOCR 识别验证码
    result = reader.readtext(processed_image_path, detail=0)  # detail=0 只返回文本内容
    if result:
        captcha_text = result[0]
        captcha_text = ''.join(filter(str.isdigit, captcha_text)) # 只保留数字
        captcha_text = captcha_text[:4]  # 只取前 4 个数字
        return captcha_text
    return None
# endregion

# region # 验证码截图并进行识别
def retry_captcha(driver):
    """ 截取并识别验证码 """
    captcha_raw_path = "src/main/resources/python/captcha_raw.png"

    captcha_element = driver.find_element(By.ID, 'verifycode')
    captcha_element.screenshot(captcha_raw_path)  # 截取验证码图片
    captcha_text = process_captcha(captcha_raw_path)  # 识别验证码
    return captcha_text
# endregion

# region # 在个人门户管理中心获取学生信息
def get_student_info(driver):
    """
    从页面中提取学生信息
    :param driver: Selenium WebDriver 实例
    :return: 包含学生信息的字典
    """
    info = {}

    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.CSS_SELECTOR, 'div[style*="float:left;width:300px;"]'))
        )
        # 定位包含学生信息的 div 元素
        base_info_div = driver.find_element(By.CSS_SELECTOR, 'div[style*="float:left;width:300px;"]')

        # 获取 div 的文本内容
        text = base_info_div.text

        # 按行分割文本
        lines = text.split('\n')

        # 提取每行信息
        for line in lines:
            if '：' in line:  # 使用中文冒号分隔
                key, value = line.split('：', 1)  # 只分割第一个冒号
                info[key.strip()] = value.strip()

    except Exception as e:
        return None

    return info
# endregion

# region # 获取课表信息 ( 已删 )
# def get_course_schedule(driver):
#     """
#     从网页中提取课表信息。
#
#     :param driver: 已初始化的 Selenium WebDriver 对象
#     :return: 包含课表信息的列表，每个元素为一个字典，表示一天的课程
#     """
#     # 等待表格内容完全加载
#     # 获取表格元素
#     table_element = driver.find_element(By.ID, "datatable")
#     rows = table_element.find_elements(By.TAG_NAME, "tr")
#     # 提取表头（星期几）
#     headers = [header.text for header in rows[0].find_elements(By.TAG_NAME, "td")]
#     # 提取课程信息
#     course_schedule = []
#     for row in rows[1:]:
#         # 获取每一行的所有单元格
#         cells = row.find_elements(By.TAG_NAME, "td")
#         if not cells:
#             continue
#         # 获取时间段（第一列）
#         time_slot = cells[0].text
#         # 获取每天的课程信息
#         for i in range(1, len(cells)):
#             day = headers[i]  # 星期几
#             course_info = cells[i].text  # 课程信息
#             # 如果课程信息不为空，添加到课表中
#             if course_info.strip():
#                 course_schedule.append({
#                     "time_slot": time_slot,  # 时间段
#                     "day": day,  # 星期几
#                     "course_info": course_info  # 课程信息
#                 })
#     return course_schedule
#endregion

# region ===== 开始登录 =====
def login_bitzh(username, password):
    result = {
        'message': '',
        'data': {}
    } # 返回 Java 的结果 ( 包含登录状态和抓包数据 )

    log_to_file("浏览器启动完成")
    # 开始尝试登录
    try:
        for attempt in range(max_retries):
            # 直接访问目标系统触发认证流程
            driver.get('https://s.bitzh.edu.cn/manage/index')
            # 自动处理重定向链
            # redirect_count = 0
            while True:
                current_url = driver.current_url
                # 检测是否进入CAS登录页
                if 'cas.bitzh.edu.cn/cas3/login' in current_url:
                    # 填写登录表单
                    try:
                        WebDriverWait(driver, 10).until(
                            EC.presence_of_element_located((By.ID, 'username'))
                        ).send_keys(username)

                        driver.find_element(By.ID, 'password').send_keys(password)
                        # 验证码处理（带重试）
                        for _ in range(10):
                            captcha_text = retry_captcha(driver)

                            # print("识别验证码为：", captcha_text)

                            if len(captcha_text) == 4 and captcha_text.isdigit():
                                driver.find_element(By.ID, 'authcode').clear()
                                driver.find_element(By.ID, 'authcode').send_keys(captcha_text)
                                break

                        # 提交登录
                        driver.find_element(By.ID, 'submit1').click()
                        time.sleep(0.00002)

                        log_to_file("错误检测开始")

                        # ===== 错误检测开始 =====
                        error_message = None  # 初始化错误信息变量

                        if not error_message:  # 如果之前没有检测到错误信息
                            try:
                                # 1. 检测 class 为 errors 的元素
                                errors = driver.find_elements(By.CSS_SELECTOR, 'div.errors')

                                # 2. 遍历所有 errors 元素
                                for error in errors:
                                    # 3. 检查元素是否可见
                                    if driver.execute_script("return arguments[0].style.display !== 'none'", error):
                                        # 4. 如果可见，提取错误信息
                                        log_to_file(error.text.strip())
                                        if error.text.strip() == "图形验证码错误":
                                            continue
                                        error_message = error.text.strip()
                                        break  # 找到第一个可见的错误信息后退出
                            except NoSuchElementException:
                                # 5. 如果找不到 errors 元素，忽略错误
                                pass
                        # 错误处理
                        if error_message:
                            result['message'] = error_message
                            return result  # 直接返回错误
                        # ==== 错误检测结束 ====

                    except Exception as e:
                        result['message'] = str(e)
                        break

                # 检测Ticket重定向
                # elif 'ticket=' in current_url:
                #     print(f"处理Ticket重定向: {current_url.split('ticket=')[1][:15]}...")
                #     # 必须重新加载以完成验证
                #     driver.get(current_url)
                #     redirect_count += 1
                #     if redirect_count > 5:
                #         result['message'] = '登录异常'
                #         break

                elif 'manage/index' in current_url:
                    start_time = time.time()
                    while True:
                    # 检查页面是否加载完成
                        if driver.execute_script("return document.readyState") == "complete":
                            is_request_complete = driver.execute_script("""
                                return window.performance.getEntries().some(entry => {
                                    return entry.name === 'https://s.bitzh.edu.cn/manage/protal/gettabletime' &&
                                           entry.responseStatus === 200;
                                });
                            """)
                            if is_request_complete:
                                student_info = get_student_info(driver)
                                # course_schedule = get_course_schedule(driver)
                                result['message'] = '登录成功'
                                result['data'] = student_info
                                # result["data"]["课表"] = course_schedule
                                log_to_file("Over")
                                return result
                        if time.time() - start_time > 30:
                            result['message'] = "运行超时"
                            return result
                        time.sleep(0.5)

                # 其他情况处理
                else:
                    result['message'] = '登录异常'
                    break
            # ===== while 识别验证码 + 重定向 =======
        # ===== for 尝试登录 =====

        # 尝试 100 次 (约计 2 s) 无法通过验证就退出
        result['message'] = '登录异常'
        return result


    except Exception as e:
        result['message'] = f"系统错误: {str(e)}"
        return result
    finally:
        if 'manage/index' not in driver.current_url:
            driver.quit()
# endregion ===== 登录结束 =====

def check_username():
    result = is_correct_username(username)
    if result:
        print(json.dumps(result, ensure_ascii=False))
        return True

if __name__ == "__main__":
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
    # region # 访问参数配置
    edge_options = Options()
    edge_options.add_argument("--disable-blink-features=AutomationControlled")
    edge_options.add_experimental_option("excludeSwitches", ["enable-automation", "enable-logging"])  # 禁用日志
    edge_options.add_argument("--log-level=3")  # 关闭所有日志（FATAL 级别）
    edge_options.add_argument("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
    edge_options.add_argument("--headless=new")  # 启用无头模式
    # endregion
    service = Service(edge_driver_path)
    driver = webdriver.Edge(service=service, options=edge_options)
    try:
        username = sys.stdin.readline().strip()
        password = sys.stdin.readline().strip()
        log_to_file("脚本启动")

        if check_username():
            exit(1)

        result = login_bitzh(username, password)
        print(json.dumps(result, ensure_ascii=False))

    except Exception as e:
        print(json.dumps({"message": f"程序异常错误: {str(e)}", "data": None}))