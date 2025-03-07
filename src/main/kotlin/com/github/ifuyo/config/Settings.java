package com.github.ifuyo.config;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import java.io.IOException;
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
    }
    // 应用主题
    private static void globalTheme() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); // 设置 FlatIntelliJLaf 主题
            // 默认主题
            String DEFAULT_THEME = "FlatIntelliJLaf";
            System.out.println("Initialized with theme: " + DEFAULT_THEME);
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
}
