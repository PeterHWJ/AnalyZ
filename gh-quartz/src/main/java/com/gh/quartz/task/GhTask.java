package com.gh.quartz.task;

import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * @author gh
 */
@Component("ghTask")
public class GhTask {
    public void ghParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ghNoParams() {
        System.out.println("执行无参方法");
    }
}
