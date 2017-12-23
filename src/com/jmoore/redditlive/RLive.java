package com.jmoore.redditlive;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

public class RLive {
    static int refresh_time = 5000, timeout = 5000;
    static Timer timer = new Timer();

    public static void main(String[] args) {
        timer.start();
        new RLive().start();
    }

    private void start() {
        new Frontpage().start();
    }

    class Frontpage extends Thread {
        Document frontpage;
        String[] postTitles = new String[25];
        String[] postLinks = new String[25];

        Post[] threads = new Post[25];

        public void run() {
            for(int postArrayFiller = 0; postArrayFiller < postTitles.length; postArrayFiller++) {
                postTitles[postArrayFiller] = "";
                postLinks[postArrayFiller] = "";
            }

            while(true) {
                try {
                    frontpage = Jsoup.parse(new URL("http://reddit.com/r/popular"), timeout);
                } catch(Exception ex) {
                    System.err.println("ERROR: JSoup failed.");
                    return;
                }

                Elements posts = frontpage.select("div.thing");

                for(int i = 0; i < postTitles.length; i++) {
                    try {
                        Thread.sleep(2000);
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    threads[i] = new Post(posts.get(i).select("a.bylink.comments").first().attr("href") + "?sort=new");
                    threads[i].start();
                }

                try {
                    //Thread.sleep(1000 * 60);
                    Thread.sleep(900000); //15 minutes before refreshing the frontpage
                } catch(Exception ex) {
                    ex.printStackTrace();
                }

                for(Post post : threads) {
                    post.suspend();
                    post.stop();
                }
            }
        }
    }
}