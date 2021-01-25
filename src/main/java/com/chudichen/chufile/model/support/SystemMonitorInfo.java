package com.chudichen.chufile.model.support;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chudichen
 * @date 2021-01-25
 */
@Data
public class SystemMonitorInfo implements Serializable {

    private static final long serialVersionUID = -30309947183265345L;

    /**
     * 服务器基本信息
     */
    private Sys sys;

    /**
     * JVM 信息
     */
    private Jvm jvm;

    /**
     * 系统内存
     */
    private Mem mem;

    public SystemMonitorInfo() {
        this.jvm = new Jvm();
        this.sys = new Sys();
        this.mem = new Mem();
    }
}
