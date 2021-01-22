package com.chudichen.chufile.util;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * 项目启动监听器，当项目启动时，遍历当前对象存储的所有内容，添加到缓存中
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Slf4j
@Component
public class StartupListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private Environment environment;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        printStartInfo();
    }

    private void printStartInfo() {
        String serverPort = environment.getProperty("server.port", "13000");
        LinkedHashSet<String> localIps = NetUtil.localIps();
        StringBuilder indexAddr = new StringBuilder();
        StringBuilder indexAdminAddr = new StringBuilder();
        localIps.stream().findFirst().ifPresent(localIp -> {
            String addr = String.format("http://%s:%s", localIp, serverPort);
            indexAddr.append(addr).append("\t");
            indexAdminAddr.append(addr).append("/#/admin").append("\t");
        });

        log.info("Chu-File started at          " + indexAddr);
        log.info("Chu-File Admin started at    " + indexAdminAddr);
    }
}
