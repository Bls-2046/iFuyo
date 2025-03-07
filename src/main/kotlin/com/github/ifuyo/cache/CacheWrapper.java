package com.github.ifuyo.cache;

public class CacheWrapper {
    static {
        System.loadLibrary("cachejni"); // 加载共享库
    }

    public native String get(String key);
    public native void set(String key, String value);
}
