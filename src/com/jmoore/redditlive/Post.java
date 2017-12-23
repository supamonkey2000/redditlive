package com.jmoore.redditlive;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Post extends Thread {
    private String last_comment = "";
    private String post_url;

    private Document post;

    Post(String post_url) {
        this.post_url = post_url;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(RLive.refresh_time);
            } catch(Exception ex) {
                System.err.println("ERROR: Refresh failed!");
                return;
            }
            refreshPage();
            getLatestComment();
        }
    }

    private void refreshPage() {
        try {
            post = Jsoup.parse(new URL(post_url), RLive.timeout);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getLatestComment() {
        String comment = post.select("div.thing.comment").first().select("form.usertext").first().select("div.usertext-body").first().select("div.md").first().text();
        if(!comment.equals(last_comment)) {
            last_comment = comment;
            System.out.println(last_comment);
        }
    }
}