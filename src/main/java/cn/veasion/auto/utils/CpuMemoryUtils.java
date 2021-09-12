package cn.veasion.auto.utils;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 * CpuMemoryUtils
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
public class CpuMemoryUtils {

    private static final Runtime runtime = Runtime.getRuntime();
    private static final OperatingSystemMXBean systemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    public static int jvmLoad() {
        double totalMemory = runtime.totalMemory();
        double freeMemory = runtime.freeMemory();
        double value = freeMemory / totalMemory;
        return (int) ((1 - value) * 100);
    }

    public static int cpuLoad() {
        double cpuLoad = systemMXBean.getSystemCpuLoad();
        return (int) (cpuLoad * 100);
    }

    public static int memoryLoad() {
        double totalMemory = systemMXBean.getTotalPhysicalMemorySize();
        double freeMemory = systemMXBean.getFreePhysicalMemorySize();
        double value = freeMemory / totalMemory;
        return (int) ((1 - value) * 100);
    }

}
