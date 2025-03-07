#include "Cache.h"

Cache& Cache::getInstance() {
    static Cache instance;
    return instance;
}

std::string Cache::get(const std::string& key) {
    std::lock_guard<std::mutex> lock(cacheMutex);

    if (memoryCache.find(key) != memoryCache.end()) {
        return memoryCache[key]; // 从内存缓存中读取
    }

    if (!fs::exists(key)) {
        return ""; // 文件不存在
    }

    std::ifstream file(key, std::ios::binary);
    if (!file) {
        return ""; // 文件打开失败
    }

    std::string content((std::istreambuf_iterator<char>(file)), std::istreambuf_iterator<char>());
    memoryCache[key] = content; // 存入内存缓存
    return content;
}

void Cache::set(const std::string& key, const std::string& value) {
    std::lock_guard<std::mutex> lock(cacheMutex);
    memoryCache[key] = value; // 存入内存缓存
}