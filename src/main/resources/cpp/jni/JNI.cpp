#include <jni.h>
#include <string>
#include "cache/Cache.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_cache_CacheWrapper_get(JNIEnv* env, jobject /* this */, jstring key) {
    const char* keyStr = env->GetStringUTFChars(key, nullptr);
    std::string value = Cache::getInstance().get(keyStr);
    env->ReleaseStringUTFChars(key, keyStr);
    return env->NewStringUTF(value.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_cache_CacheWrapper_set(JNIEnv* env, jobject /* this */, jstring key, jstring value) {
    const char* keyStr = env->GetStringUTFChars(key, nullptr);
    const char* valueStr = env->GetStringUTFChars(value, nullptr);
    Cache::getInstance().set(keyStr, valueStr);
    env->ReleaseStringUTFChars(key, keyStr);
    env->ReleaseStringUTFChars(value, valueStr);
}