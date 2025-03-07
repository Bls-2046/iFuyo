#include <unordered_map>
#include <string>
#include <vector>
#include <mutex>
#include <thread>
#include <functional>
#include <fstream>
#include <sstream>

class ImageCache {
private:
    std::unordered_map<std::string, std::vector<unsigned char>> cache;
    std::mutex cacheMutex; // 用于线程安全

    // 从文件加载图片数据
    std::vector<unsigned char> loadImageFromFile(const std::string& path) {
        std::ifstream file(path, std::ios::binary);
        if (!file) {
            throw std::runtime_error("Failed to open file: " + path);
        }

        // 读取文件内容
        std::vector<unsigned char> data((std::istreambuf_iterator<char>(file)), std::istreambuf_iterator<char>());
        return data;
    }

public:
    std::vector<unsigned char> loadImage(const std::string& path) {
        std::lock_guard<std::mutex> lock(cacheMutex); // 加锁
        auto it = cache.find(path);
        if (it != cache.end()) {
            return it->second; // 返回缓存中的图片
        }

        // 从文件加载图片
        std::vector<unsigned char> imageData = loadImageFromFile(path);
        cache[path] = imageData; // 存入缓存
        return imageData;
    }

    void clearCache() {
        std::lock_guard<std::mutex> lock(cacheMutex); // 加锁
        cache.clear();
    }
};

// JNI 接口
extern "C" {
    JNIEXPORT void JNICALL Java_com_github_ifuyo_utils_cache_ImageCache_loadImageAsync(JNIEnv* env, jobject obj, jstring path, jobject callback);
    JNIEXPORT void JNICALL Java_com_github_ifuyo_utils_cache_ImageCache_clearCache(JNIEnv* env, jobject obj);
}

// 异步加载图片
JNIEXPORT void JNICALL Java_com_github_ifuyo_utils_cache_ImageCache_loadImageAsync(JNIEnv* env, jobject obj, jstring path, jobject callback) {
    const char* pathStr = env->GetStringUTFChars(path, 0);

    // 在子线程中执行缓存加载
    std::thread([env, pathStr, callback]() {
        try {
            ImageCache cache;
            std::vector<unsigned char> imageData = cache.loadImage(pathStr);

            // 将C++的std::vector<unsigned char>转换为Java的jbyteArray
            jbyteArray result = env->NewByteArray(imageData.size());
            env->SetByteArrayRegion(result, 0, imageData.size(), reinterpret_cast<jbyte*>(imageData.data()));

            // 调用Java回调方法
            jclass callbackClass = env->GetObjectClass(callback);
            jmethodID onSuccessMethod = env->GetMethodID(callbackClass, "onSuccess", "([B)V");
            env->CallVoidMethod(callback, onSuccessMethod, result);
        } catch (const std::exception& e) {
            // 调用Java回调方法的onError
            jclass callbackClass = env->GetObjectClass(callback);
            jmethodID onErrorMethod = env->GetMethodID(callbackClass, "onError", "(Ljava/lang/String;)V");
            env->CallVoidMethod(callback, onErrorMethod, env->NewStringUTF(e.what()));
        }

        env->ReleaseStringUTFChars(path, pathStr);
    }).detach(); // 分离线程，避免阻塞主进程
}

// 清理缓存
JNIEXPORT void JNICALL Java_com_github_ifuyo_utils_cache_ImageCache_clearCache(JNIEnv* env, jobject obj) {
    ImageCache cache;
    cache.clearCache();
}