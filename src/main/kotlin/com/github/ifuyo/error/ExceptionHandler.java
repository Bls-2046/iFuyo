package com.github.ifuyo.error;

public class ExceptionHandler {
    /**
     * 执行可能抛出异常的操作，并返回结果
     *
     * @param supplier     包含可能抛出异常的操作
     * @param errorMessage 异常时的错误信息
     * @param <T>          返回类型
     * @return 操作结果，如果发生异常则返回 null
     */
    public static <T> T handle(SupplierWithException<T> supplier, String errorMessage) {
        try {
            return supplier.get();
        } catch (Exception e) {
            System.err.println(errorMessage + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * 执行可能抛出异常的操作，不返回结果
     *
     * @param runnable     包含可能抛出异常的操作
     * @param errorMessage 异常时的错误信息
     */
    public static void handle(RunnableWithException runnable, String errorMessage) {
        try {
            runnable.run();
        } catch (Exception e) {
            System.err.println(errorMessage + ": " + e.getMessage());
        }
    }

    /**
     * 定义带异常的 Supplier 接口
     */
    @FunctionalInterface
    public interface SupplierWithException<T> {
        T get() throws Exception;
    }

    /**
     * 定义带异常的 Runnable 接口
     */
    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws Exception;
    }
}
