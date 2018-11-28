package com.sky.srpingcloud.eureka.client.controller;

import com.sky.srpingcloud.eureka.client.util.ConvertVideo;
import com.sky.srpingcloud.eureka.client.util.VideoStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("video")
public class VideoController {

    @RequestMapping("")
    public String main(){
        return "hello video";
    }
    @RequestMapping("start")
    public String start(){
        String source = "E:\\test\\video2.mp4";
        String target = "E:\\test\\video2.m3u8";
        ConvertVideo.processM3U8(source, target);
        return "已经开始。。。";
    }

    @RequestMapping("isAlive")
    public String isAlive(){
        Process p = VideoStatus.p;
        if (p.isAlive()) {
            System.out.println("正在处理。。。" + LocalDateTime.now());
            return "正在处理。。。" + LocalDateTime.now();
        }
        return "处理完成。。。" + LocalDateTime.now();
    }

}
