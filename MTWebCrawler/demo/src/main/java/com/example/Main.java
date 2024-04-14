package com.example;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<MTWebCrawler> bots = new ArrayList<>();

    // Create bots
    bots.add(new MTWebCrawler("https://www.google.com", 1));
    bots.add(new MTWebCrawler("https://www.yahoo.com", 2));
    bots.add(new MTWebCrawler("https://www.nytimes.com", 3));

    // Start bots
    for (MTWebCrawler bot : bots){
      try{
        bot.getThread().join();
      }
      catch(InterruptedException e){
        e.printStackTrace();
      }
    }
  }
}