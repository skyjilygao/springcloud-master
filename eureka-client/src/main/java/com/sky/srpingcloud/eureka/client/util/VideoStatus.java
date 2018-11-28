package com.sky.srpingcloud.eureka.client.util;

import java.time.LocalDateTime;

public class VideoStatus {
    public static Process p;

    public static void main(String[] args) throws InterruptedException {
        while (p.isAlive()) {
            System.out.println("正在处理。。。" + LocalDateTime.now());
            Thread.sleep(1000 * 3);
        }
        System.out.println("处理完成。。。" + LocalDateTime.now());
    }
}
