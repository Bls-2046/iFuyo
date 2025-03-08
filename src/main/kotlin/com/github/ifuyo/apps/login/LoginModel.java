package com.github.ifuyo.apps.login;

import com.github.ifuyo.config.Settings;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Objects;
import static com.github.ifuyo.apps.Api.getVerseV1;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class LoginModel {
    // 获取诗句
    public String getVerse() {
        return Objects.requireNonNull(getVerseV1()).getContent();
    }

    // 获取诗人
    public String getAuthor() {
        return "—" + getVerseV1().getAuthor();
    }

    public JSONObject validateLogin(String username, String password) {
        try {
            // 调用 Python 脚本验证密码
            String pythonInterpreter = ".venv\\Scripts\\python.exe";
            String scriptPath = "src/main/resources/python/login_bitzh.py";

            // 启动 Python 进程
            System.out.println(scriptPath);
            Process process = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath, username, password});

            // 读取 Python 脚本的标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
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

    // #region
    /**
     *  自动登录账号
     */
    public static final String APP_DIR = System.getenv("LOCALAPPDATA") + "\\iFuyo\\";
    private static final String FILE_PATH = APP_DIR + "credentials.txt";
    private static final String ENCRYPTION_KEY = "mySecretKey12345"; // 加密密钥（需妥善保管）

    /**
     * 存储账号和密码
     */
    public void saveCredentials(String username, String password) throws Exception {
        // 加密密码
        String encryptedPassword = encrypt(password);
        Path filePath = Paths.get(FILE_PATH);

        // 创建文件
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
                System.out.println("文件创建成功！");
            } catch (IOException e) {
                System.err.println("文件创建失败: " + e.getMessage());
            }
        }

        // 写入文件
        String content = "username=" + username + "\npassword=" + encryptedPassword;
        Files.write(filePath, content.getBytes());
        System.out.println("Credentials saved successfully.");
    }

    /**
     * 读取账号和密码
     */
    public String[] readCredentials() throws Exception {
        // 检查文件是否存在
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            System.out.println("Credentials file not found.");
            return null;
        }

        // 读取文件内容
        String content = new String(Files.readAllBytes(path));

        // 解析文件内容
        String username = content.split("\n")[0].split("=")[1];
        String encryptedPassword = content.split("\n")[1].split("=")[1];

        // 解密密码
        String password = decrypt(encryptedPassword);

        return new String[]{username, password};
    }

    /**
     * 删除存储账号和密码的文件
     */
    public void deleteCredentials() throws Exception {
        // 检查文件是否存在
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            // 删除文件
            Files.delete(path);
            System.out.println("Credentials file deleted successfully.");
        } else {
            System.out.println("Credentials file not found.");
        }
    }

    /**
     * 加密
     */
    private String encrypt(String data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密
     */
    private String decrypt(String encryptedData) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
    // #endregion
}