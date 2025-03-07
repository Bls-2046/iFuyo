#ifndef CACHE_H
#define CACHE_H

#include <string>
#include <unordered_map>
#include <filesystem>
#include <fstream>
#include <mutex>

namespace fs = std::filesystem;

class Cache {
public:
    static Cache& getInstance();

    std::string get(const std::string& key);
    void set(const std::string& key, const std::string& value);

private:
    Cache() = default; // 单例模式，禁止外部构造
    std::unordered_map<std::string, std::string> memoryCache; // 内存缓存
    std::mutex cacheMutex; // 线程安全
};

#endif // CACHE_H