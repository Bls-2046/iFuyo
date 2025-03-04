package com.github.ifuyo.config;

import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;

public class Settings {

    // 默认主题
    private static final String DEFAULT_THEME = "FlatIntelliJLaf";

    // 私有构造函数，防止外部实例化
    private Settings() {}

    // 应用主题
    private static void applyTheme() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf()); // 设置 FlatIntelliJLaf 主题
            System.out.println("Initialized with theme: " + DEFAULT_THEME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 初始化配置
    public static void initialize() {
        applyTheme(); // 应用默认主题

    }
}
