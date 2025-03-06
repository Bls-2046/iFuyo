package com.github.ifuyo.apps.login;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import static com.github.ifuyo.apps.Api.getVerseV1;

public class LoginModel {
    // 获取诗句
    public String getVerse() {
        return Objects.requireNonNull(getVerseV1()).getContent();
    }

    // 获取作者
    public String getAuthor() {
        return "—" + getVerseV1().getAuthor();
    }

    public JSONObject validateLogin(String username, String password) {
        try {
            // 调用 Python 脚本验证密码
            String pythonInterpreter = ".venv\\Scripts\\python.exe";
            String scriptPath = "src/main/resources/python/login_bitzh.py";

            // 启动 Python 进程
            Process process = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath, username, password});

            // 读取 Python 脚本的标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            String result = output.toString();
            System.out.println("Raw Python Output: " + result);

            // 解析 JSON 响应
            return new JSONObject(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
