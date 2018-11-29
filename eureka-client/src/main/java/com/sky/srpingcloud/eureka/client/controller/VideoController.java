package com.sky.srpingcloud.eureka.client.controller;

import com.sky.srpingcloud.eureka.client.util.VideoConvert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("video")
public class VideoController {
    VideoConvert videoConvert;

    @RequestMapping("")
    public String main(){
        return "hello video";
    }
    @RequestMapping("start")
    public String start() throws FileNotFoundException, InterruptedException {
        String source = "E:\\test\\video2.mp4";
        String target = "E:\\test\\video2.m3u8";
        VideoConvert convert = new VideoConvert("D:\\!Soft\\ffmpeg-20181127-1035206-win64-static\\ffmpeg-20181127-1035206-win64-static\\bin\\ffmpeg.exe");
        convert.start(source, target);
        videoConvert = convert;
        return "已经开始。。。";
    }

    @RequestMapping("isAlive")
    public String isAlive(){
        return videoConvert.getStatus();
    }

    @RequestMapping("stop")
    public String stop(){
        videoConvert.stop();
        return "stoped";
    }
}
