/* package com.example; */

package com.example;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class WebCrawler implements Runnable{
  private static final int MAX_DEPTH = 3;
  //private static final int MAX_PAGES = 10;
  //private static final int MAX_THREADS = 5;
  private Thread thread;
  private String first_url;
  private ArrayList<String> visitedLinks = new ArrayList<String>();
  private int ID;

  public WebCrawler(String link, int num){
    System.out.println("WebCrawler created.");
    first_url = link;
    ID = num;

    thread = new Thread(this);
    thread.start();
  }//WebCrawler

  @Override
  public void run(){
    crawl(1, first_url);
  }//run

  private void crawl(int level, String url){
    if (level <= MAX_DEPTH){
      Document doc = request(url);

      if(doc != null){
        for(Element link : doc.select("a[href]")){
          String next_link = link.absUrl("href");
          if(visitedLinks.contains(next_link) == false){
            crawl(level++, next_link);
          }//if
        }//for
      }//inner if
    }//if
  }//crawl

  private Document request(String url){
    try{
      Connection con = Jsoup.connect(url);
      Document doc = con.get();

      if(con.response().statusCode() == 200){
        System.out.println("\nBot ID: "+ID+"\nURL: "+url);

        String title = doc.title();
        System.out.println("Title: "+title);
        visitedLinks.add(url);

        return doc;
      }//if
      return null;
    }//try
    catch(IOException e){
      return null;
    }//catch
  }//request

  public Thread getThread(){
    return thread;
  }//getThread
}//WebCrawler