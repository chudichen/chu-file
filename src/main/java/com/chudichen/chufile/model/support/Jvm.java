package com.chudichen.chufile.model.support;

import lombok.Data;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
public class Jvm {

    /**
     * 当前JVM占用的内存总数(M)
     */
    private Double total;

    /**
     * JVM最大可用内存总数(M)
     */
    private Double max;

    /**
     * JVM空闲内存(M)
     */
    private Double free;

    /**
     * JDK版本
     */
    private String version;

    public Jvm() {
        Runtime runtime = Runtime.getRuntime();
        this.total = (double) runtime.totalMemory();
        this.free = (double) runtime.freeMemory();
        this.max = (double) runtime.maxMemory();
        this.version = System.getProperty("java.version");
    }
}
