package com.sky.srpingcloud.eureka.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ErrStreamRead implements Runnable {
    private Process pi;

    public ErrStreamRead(Process pi) {
        this.pi = pi;
    }

    @Override
    public void run() {
        BufferedReader err = new BufferedReader(new InputStreamReader(pi.getErrorStream()));
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
                    pi.waitFor();
                    pi.destroy();
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
