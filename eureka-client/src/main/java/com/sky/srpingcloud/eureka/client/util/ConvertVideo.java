package com.sky.srpingcloud.eureka.client.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ConvertVideo {
    private final static String PATH = "【需要转码的视频路径】";
    private final static String ffmpeg = "D:\\!Soft\\ffmpeg-20181127-1035206-win64-static\\ffmpeg-20181127-1035206-win64-static\\bin\\ffmpeg.exe";
    public static void main(String[] args) throws InterruptedException {
        String source = "E:\\test\\video2.mp4";
        String target = "E:\\test\\video2.m3u8";
        processM3U8(source, target);
        /*while (true){
            System.out.println("还在跑"+LocalDateTime.now());
            Thread.sleep(1000 * 3);
        }*/
    }

    /**
     * 拼接ffmpeg命令：ffmpeg -i test.mp4 -c:v libx264 -hls_time 60 -hls_list_size 0 -c:a aac -strict -2 -f hls output.m3u8
     * @param source
     * @return
     */
    public static boolean processM3U8(String source, String target) {
        File sourceFile = new File(source);
        File targetFile = new File(target);
        File parentDir  = targetFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpeg);
        commend.add("-i");
        commend.add(source);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-hls_time");
        commend.add("60");
        commend.add("-hls_list_size");
        commend.add("0");
        commend.add("-c:a");
        commend.add("aac");
        commend.add("-strict");
        commend.add("-2");
        commend.add("-f");
        commend.add("hls");
        commend.add(target);
        // 通过ProcessBuilder创建
//        processBuilder(commend);

        // 通过runtime创建
        StringBuffer bf = new StringBuffer();
        for (String str : commend) {
            bf.append(" ").append(str);
        }
        String cmdStr = bf.toString();
        System.out.println(cmdStr);
        runtimeBuilder(cmdStr);
        return true;
    }

    private static boolean processBuilder(List<String> commend){
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proce = null;
            //视频截图命令，封面图。  8是代表第8秒的时候截图
            int sec = 1;
            String cmd = "";
          /*  String cut = "ffmpeg -i   "
                    + source
                    + "   -y   -f   image2   -ss   " + sec + "   -t   0.001   -s   600x500  "+parentDir.getAbsolutePath()
                    + "\\a.jpg";*/
//            String cutCmd = cmd + cut;
//            proce = runtime.exec(cutCmd);
            //调用线程命令进行转码
            ProcessBuilder builder = new ProcessBuilder(commend);
            builder.command(commend);
            new Thread(){
                @Override
                public void run(){
                    try {
                        Process p = builder.start();
                        VideoStatus.p = p;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static void runtimeBuilder(String cmdStr){
        try {
            Process process = Runtime.getRuntime().exec(cmdStr);
            /*ErrStreamRead streamRead = new ErrStreamRead(process);
            streamRead.run();*/
            VideoStatus.p = process;
            MyThread myThread = new MyThread(process);
            myThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static boolean checkfile(String path) {
        File file = new File(path);
        if (!file.isFile()) {
            return false;
        }
        return true;
    }

    private static boolean process() {
        // 判断视频的类型
        int type = checkContentType();
        boolean status = false;
        //如果是ffmpeg可以转换的类型直接转码，否则先用mencoder转码成AVI
        if (type == 0) {
            System.out.println("直接将文件转为flv文件");
            status = processFLV(PATH);// 直接将文件转为flv文件
        } else if (type == 1) {
            String avifilepath = processAVI(type);
            if (avifilepath == null)
                return false;// avi文件没有得到
            status = processFLV(avifilepath);// 将avi转为flv
        }
        return status;
    }

    private static int checkContentType() {
        String type = PATH.substring(PATH.lastIndexOf(".") + 1, PATH.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if (type.equals("avi")) {
            return 0;
        } else if (type.equals("mpg")) {
            return 0;
        } else if (type.equals("wmv")) {
            return 0;
        } else if (type.equals("3gp")) {
            return 0;
        } else if (type.equals("mov")) {
            return 0;
        } else if (type.equals("mp4")) {
            return 0;
        } else if (type.equals("asf")) {
            return 0;
        } else if (type.equals("asx")) {
            return 0;
        } else if (type.equals("flv")) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if (type.equals("wmv9")) {
            return 1;
        } else if (type.equals("rm")) {
            return 1;
        } else if (type.equals("rmvb")) {
            return 1;
        }
        return 9;
    }


    // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
    private static String processAVI(int type) {
        List<String> commend = new ArrayList<String>();
        commend.add("D:\\ffmpeg\\mencoder");
        commend.add(PATH);
        commend.add("-oac");
        commend.add("lavc");
        commend.add("-lavcopts");
        commend.add("acodec=mp3:abitrate=64");
        commend.add("-ovc");
        commend.add("xvid");
        commend.add("-xvidencopts");
        commend.add("bitrate=600");
        commend.add("-of");
        commend.add("avi");
        commend.add("-o");
        commend.add("【存放转码后视频的路径，记住一定是.avi后缀的文件名】");
        try {
            //调用线程命令启动转码
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.start();
            return "【存放转码后视频的路径，记住一定是.avi后缀的文件名】";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
    private static boolean processFLV(String oldfilepath) {

        if (!checkfile(PATH)) {
            System.out.println(oldfilepath + " is not file");
            return false;
        }

        // 文件命名
        Calendar c = Calendar.getInstance();
        String savename = String.valueOf(c.getTimeInMillis()) + Math.round(Math.random() * 100000);
        List<String> commend = new ArrayList<String>();
        commend.add("D:\\ffmpeg\\ffmpeg");
        commend.add("-i");
        commend.add(oldfilepath);
        commend.add("-ab");
        commend.add("56");
        commend.add("-ar");
        commend.add("22050");
        commend.add("-qscale");
        commend.add("8");
        commend.add("-r");
        commend.add("15");
        commend.add("-s");
        commend.add("600x500");
        commend.add("【存放转码后视频的路径，记住一定是.flv后缀的文件名】");

        try {
            Runtime runtime = Runtime.getRuntime();
            Process proce = null;
            //视频截图命令，封面图。  8是代表第8秒的时候截图
            String cmd = "";
            String cut = "     c:\\ffmpeg\\ffmpeg.exe   -i   "
                    + oldfilepath
                    + "   -y   -f   image2   -ss   8   -t   0.001   -s   600x500   c:\\ffmpeg\\output\\"
                    + "a.jpg";
            String cutCmd = cmd + cut;
            proce = runtime.exec(cutCmd);
            //调用线程命令进行转码
            ProcessBuilder builder = new ProcessBuilder(commend);
            builder.command(commend);
            builder.start();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}