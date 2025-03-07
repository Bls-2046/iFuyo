cmake_minimum_required(VERSION 3.10)
project(CacheLibrary)

# 添加缓存库
add_library(cache SHARED src/main/cpp/cache/Cache.cpp src/main/cpp/cache/Cache.h)

# 添加 JNI 库
add_library(cachejni SHARED src/main/cpp/jni/CacheJNI.cpp)
target_link_libraries(cachejni cache)

# 设置安装路径
install(TARGETS cache cachejni DESTINATION lib)
install(FILES src/main/cpp/cache/Cache.h DESTINATION include/cache)