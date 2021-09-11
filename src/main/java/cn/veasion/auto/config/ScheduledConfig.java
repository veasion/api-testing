package cn.veasion.auto.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledConfig
 *
 * @author luozhuowei
 * @date 2021/9/11
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduledConfig implements SchedulingConfigurer {

    private static ScheduledTaskRegistrar scheduledTaskRegistrar;
    private static final Map<String, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();

    @Bean
    public Executor defaultExecutor() {
        return new ThreadPoolExecutor(8, 100, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ScheduledConfig.scheduledTaskRegistrar = scheduledTaskRegistrar;
    }

    public static void addCronTask(String key, Runnable runnable, String expression) {
        TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
        if (scheduler == null) {
            throw new RuntimeException("获取调度器失败");
        }
        if (scheduledFutureMap.containsKey(key)) {
            removeCronTask(key);
        }
        ScheduledFuture<?> future = scheduler.schedule(runnable, new CronTrigger(expression));
        scheduledFutureMap.put(key, future);
    }

    public static void removeCronTask(String key) {
        ScheduledFuture<?> cronTask = scheduledFutureMap.get(key);
        if (cronTask == null) {
            return;
        }
        cronTask.cancel(true);
        scheduledFutureMap.remove(key);
    }

    public static void resetCronTask(String key, Runnable runnable, String expression) {
        removeCronTask(key);
        addCronTask(key, runnable, expression);
    }

    public static boolean containsKey(String key) {
        return scheduledFutureMap.containsKey(key);
    }

    public static String key(Class<?> clazz, Object id) {
        return clazz.getSimpleName() + "_" + id;
    }

}
