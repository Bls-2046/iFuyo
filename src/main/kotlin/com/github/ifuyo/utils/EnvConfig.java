package com.github.ifuyo.utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

import java.util.HashMap;
import java.util.Map;

public class EnvConfig {
    private static Map<String, String> envMap;

    static {
        envMap = new HashMap<>();
        loadEnvFile(".env"); // 默认加载项目根目录下的 .env 文件
    }

    /**
     * 加载指定路径的 .env 文件
     *
     * @param filePath .env 文件路径
     */
    public static void loadEnvFile(String filePath) {
        try {
            Dotenv dotenv = Dotenv.configure().filename(filePath).load();
            dotenv.entries().forEach(entry -> envMap.put(entry.getKey(), entry.getValue()));
        } catch (DotenvException e) {
            System.err.println("Failed to load .env file from '" + filePath + "': " + e.getMessage());
        }
    }

    /**
     * 从环境变量或 .env 文件中获取指定参数的值，如果未找到则返回默认值
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 参数值，如果未找到则返回默认值
     */
    public static String getEnvParameter(String key, String defaultValue) {
        // 优先从环境变量中获取
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }
        // 如果未找到，再从 .env 文件中获取
        if (envMap == null) {
            System.err.println(".env file is not loaded.");
            return defaultValue;
        }
        return envMap.getOrDefault(key, defaultValue);
    }

    /**
     * 从 .env 文件中获取指定参数的值，并转换为整数
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 参数值（整数），如果未找到或转换失败则返回默认值
     */
    public static int getEnvParameterAsInt(String key, int defaultValue) {
        String value = getEnvParameter(key, null);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Failed to convert parameter '" + key + "' to integer: " + e.getMessage());
            return defaultValue;
        }
    }

    /**
     * 从 .env 文件中获取指定参数的值，并转换为布尔值
     *
     * @param key          参数名
     * @param defaultValue 默认值
     * @return 参数值（布尔值），如果未找到或转换失败则返回默认值
     */
    public static boolean getEnvParameterAsBoolean(String key, boolean defaultValue) {
        String value = getEnvParameter(key, null);
        if (value == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
}