package com.github.ifuyo.config;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Settings {
    // 私有构造函数，防止外部实例化
    private Settings() {}

    public static void initApplication() throws IOException {
        // 应用主题
        createFiles();
        // 初始化文件
        globalTheme();

        startPythonProcess(".venv\\Scripts\\python.exe", "src/main/resources/python/login_bitzh.py");
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
    private static boolean isPythonProcessRunning = false;

    // 启动 Python 进程（预加载）
    private static void startPythonProcess(String pythonInterpreter, String scriptPath) throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                pythonProcess = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath});

                // 获取 Python 脚本的输出流（用于向脚本传递数据）
                writer = new BufferedWriter(new OutputStreamWriter(pythonProcess.getOutputStream(), StandardCharsets.UTF_8));
                // 获取 Python 脚本的输入流（用于读取脚本的输出）
                reader = new BufferedReader(new InputStreamReader(pythonProcess.getInputStream(), StandardCharsets.UTF_8));

                isPythonProcessRunning = true;
                System.out.println("Python 进程已启动");
            } catch (IOException e) {
                e.printStackTrace();
                isPythonProcessRunning = false;
            }
        });
        executor.shutdown();
    }

    /**
     * 执行 Python 脚本并返回输出结果
     *
     * @param username 用户名
     * @param password 密码
     * @return Python 脚本的输出结果
     * @throws IOException 如果发生 I/O 错误
     */
    public static String executePythonScript(String username, String password) throws IOException {
        while (true) {
            if (isPythonProcessRunning) {
                break;
            }
        }
        // 构造输入数据
        writer.write(username + "\n");
        writer.write(password + "\n");
        writer.flush();

        // 读取 Python 进程的输出（仅一行）
        return reader.readLine();
    }

    // 停止 Python 进程
    public static void stopPythonProcess() {
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (pythonProcess != null) {
                pythonProcess.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
