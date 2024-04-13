package com.example;

import java.util.ArrayList;


public class Main {
  public static void main(String[] args) {
    ArrayList<WebCrawler> bots = new ArrayList<>();
    bots.add(new WebCrawler("https://www.google.com", 1));
    bots.add(new WebCrawler("https://www.nytimes.com", 2));
    bots.add(new WebCrawler("https://www.npr.org", 3));

    for(WebCrawler bot : bots){
      try{
        bot.getThread().join();
      }//try
      catch(InterruptedException e){
        e.printStackTrace();
      }//catch  
    }//for
  }//main
}//Main