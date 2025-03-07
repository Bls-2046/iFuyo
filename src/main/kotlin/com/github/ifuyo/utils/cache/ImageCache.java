package com.github.ifuyo.utils.cache;

public class ImageCache {

    // 加载动态链接库
    static {
        System.loadLibrary("ImageCache");
    }

    // 定义 native 方法
    private native void loadImageAsync(String path, ImageCallback callback);
    private native void clearCache();

    // 异步加载图片
    public void loadImage(String path, ImageCallback callback) {
        try {
            loadImageAsync(path, callback);
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    // 清理缓存
    public void clear() {
        clearCache();
    }
}
