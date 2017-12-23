package com.jmoore.redditlive;

public class Timer extends Thread {

    static int current_step = 0;

    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
                current_step++;
            } catch(Exception ex) {
                System.err.println("ERROR: Timer has failed!");
                return;
            }
        }
    }
}
