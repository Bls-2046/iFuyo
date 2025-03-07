package com.github.ifuyo.utils.cache;

public interface ImageCallback {
    void onSuccess(byte[] imageData);
    void onError(Throwable error);
}
