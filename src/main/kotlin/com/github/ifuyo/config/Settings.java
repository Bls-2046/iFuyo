package com.github.ifuyo.config;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    // 私有构造函数，防止外部实例化
    private Settings() {}

    public static void initApplication() throws IOException {
        // 应用主题
        createFiles();
        // 初始化文件
        globalTheme();

         // startPythonProcess();
    }
    // 应用主题
    private static void globalTheme() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); // 设置 FlatIntelliJLaf 主题
            // 默认主题
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createFiles() throws IOException {
        String[] FILE_PATH = {
            System.getenv("LOCALAPPDATA") + "\\iFuyo\\",
        };
        for (String filePath : FILE_PATH) {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Process pythonProcess;
    private static BufferedWriter writer;
    private static BufferedReader reader;

    // 启动 Python 进程（预加载）
    private static void startPythonProcess() throws IOException {
        String pythonInterpreter = ".venv\\Scripts\\python.exe";
        String scriptPath = "src/main/resources/python/login_bitzh.py";
        pythonProcess = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath});
        System.out.println("Python process started");

        // 获取进程的输入输出流
        writer = new BufferedWriter(new OutputStreamWriter(pythonProcess.getOutputStream(), StandardCharsets.UTF_8));
        reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream(), StandardCharsets.UTF_8));
    }

    // 向 Python 进程发送数据并获取结果
    public static String executePythonScript(String username, String password) throws IOException {
        // 构造输入数据
        String input = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
        System.out.println("Executing python script: " + input);
        // 发送输入数据
        writer.write(input + "\n");
        writer.flush();

        // 读取 Python 进程的输出
        return reader.readLine();
    }

    // 停止 Python 进程
    public static void stopPythonProcess() throws IOException {
        try {
            pythonProcess.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
