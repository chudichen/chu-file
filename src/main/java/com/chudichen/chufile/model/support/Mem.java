package com.chudichen.chufile.model.support;

import lombok.Data;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
public class Mem {

    /**
     * 内存总量
     */
    private Double total;

    /**
     * 已用内存
     */
    private Double used;

    /**
     * 剩余内存
     */
    private Double free;

    public Mem() {
        OperatingSystemMXBean osb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        // 总的物理内存 + 虚拟内存
        long totalVirtualMemory = osb.getTotalSwapSpaceSize();
        // 剩余的物理内存
        long freePhysicalMemorySize = osb.getFreePhysicalMemorySize();
        this.total = (double) totalVirtualMemory;
        this.free = (double) freePhysicalMemorySize;
        this.used = (double) totalVirtualMemory - freePhysicalMemorySize;
    }
}
