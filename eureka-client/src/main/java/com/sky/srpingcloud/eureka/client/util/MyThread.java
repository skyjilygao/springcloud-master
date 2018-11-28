package com.sky.srpingcloud.eureka.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyThread extends Thread {
    private Process p;
    public MyThread(Process p){
        this.p = p;
    }

    @Override
    public void run(){
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line = null;

        try {
            while ((line = err.readLine()) != null) {
                System.out.println("err: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                err.close();
                try {
                    p.waitFor();
                    p.destroy();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
