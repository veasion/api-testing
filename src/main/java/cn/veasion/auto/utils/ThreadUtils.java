package cn.veasion.auto.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * ThreadUtils
 *
 * @author luozhuowei
 * @date 2021/9/10
 */
public class ThreadUtils {

    /**
     * 并发执行
     *
     * @param threadCount      并发线程数
     * @param loopCount        总共执行多少次
     * @param intervalInMillis 每次执行间隔时间（毫秒）
     * @param task             任务
     * @return 执行时间
     */
    public static <T> long concurrentInCount(int threadCount, int loopCount, long intervalInMillis, final Callable<T> task)
            throws InterruptedException, ExecutionException {
        return concurrent(threadCount, loopCount, -1, intervalInMillis, task, null, -1);
    }

    /**
     * 并发执行
     *
     * @param threadCount      并发线程数
     * @param timeInMillis     执行时间
     * @param intervalInMillis 每次执行间隔时间（毫秒）
     * @param task             任务
     * @return 执行时间
     */
    public static <T> long concurrentInTime(int threadCount, long timeInMillis, long intervalInMillis, final Callable<T> task)
            throws InterruptedException, ExecutionException {
        return concurrent(threadCount, -1, timeInMillis, intervalInMillis, task, null, -1);
    }

    /**
     * 并发执行
     *
     * @param threadCount      并发线程数
     * @param loopCount        总共执行多少次
     * @param timeInMillis     执行时间（不限制次数，此时 loopCount 失效）
     * @param intervalInMillis 每次执行间隔时间（毫秒）
     * @param task             任务
     * @return 执行时间
     */
    public static <T> long concurrent(int threadCount, int loopCount, long timeInMillis, long intervalInMillis, final Callable<T> task)
            throws InterruptedException, ExecutionException {
        return concurrent(threadCount, loopCount, timeInMillis, intervalInMillis, task, null, -1);
    }

    /**
     * 并发执行
     *
     * @param threadCount      并发线程数
     * @param loopCount        总共执行多少次
     * @param timeInMillis     执行时间（不限制次数，此时 loopCount 和 timeoutSeconds 失效）
     * @param intervalInMillis 每次执行间隔时间（毫秒）
     * @param task             任务
     * @param resultHandler    结果处理器
     * @param timeoutSeconds   执行任务总超时（秒）
     * @return 执行时间, -1表示执行超时
     */
    public static <T> long concurrent(int threadCount, int loopCount, long timeInMillis, long intervalInMillis,
                                      final Callable<T> task, Consumer<T> resultHandler, long timeoutSeconds)
            throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        if (timeInMillis > 0) {
            timeoutSeconds = -1;
            loopCount = Integer.MAX_VALUE;
        }
        List<Future<T>> results = new ArrayList<>();
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < loopCount; i++) {
            if (i > 0 && intervalInMillis > 0) {
                Thread.sleep(intervalInMillis);
            }
            results.add(executor.submit(task));
            if (timeInMillis > 0 && System.currentTimeMillis() - startTimeMillis >= timeInMillis) {
                for (Future<T> r : results) {
                    r.cancel(true);
                }
                break;
            }
        }
        executor.shutdown();
        if (timeoutSeconds > 0) {
            boolean completeWithTimeout = executor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS);
            if (!completeWithTimeout) {
                for (Future<T> r : results) {
                    r.cancel(true);
                }
                return -1;
            }
        } else {
            while (true) {
                if (executor.awaitTermination(200, TimeUnit.MILLISECONDS)) {
                    break;
                }
            }
        }
        long totalCostTimeMillis = System.currentTimeMillis() - startTimeMillis;
        if (resultHandler != null) {
            for (Future<T> r : results) {
                resultHandler.accept(r.get());
            }
        }
        return totalCostTimeMillis;
    }

}
