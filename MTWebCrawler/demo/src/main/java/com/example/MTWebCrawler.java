package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import java.util.ArrayList;
import java.io.IOException;

public class MTWebCrawler implements Runnable{
  private static final int MAX_DEPTH = 3;
  private int ID;
  private Thread thread;
  private String first_link;
  private ArrayList<String> visitedLinks = new ArrayList<String>();

  /** Instantiates a new MTWebCrawler object.
      @param starting link
      @param ID number of web crawler
  */
  public MTWebCrawler(String first_link, int ID){
    System.out.println("Web Crawler "+ ID + " created.");
    this.first_link = first_link;
    this.ID = ID;

    thread = new Thread(this);
    thread.start();
  }//MTWebCrawler 
  
  // Runs the web crawler
  @Override
  public void run(){
    crawl(1, first_link);
  }//run

  /** Recursively crawls webpages
      @param the current depth of the crawler
      @param the current link
  */
  private void crawl(int level, String url){
    if (level <= MAX_DEPTH){
      Document doc = request(url);

      if (doc != null){
        for (Element link : doc.select("a[href]")){
          String next_link = link.absUrl("href");
          if(visitedLinks.contains(next_link) == false){
            crawl(level++, next_link);
          }
        }
      }
    }
  }//crawl
  
  /** Requests a webpage
      @param the link to request
      @return the webpage
  */
  private Document request(String url){
    try{
      Connection con = Jsoup.connect(url);
      Document doc = con.get();

      if(con.response().statusCode() == 200){
        System.out.println("\nBot ID: " +ID+"\nURL: "+url);
        
        String title = doc.title();
        System.out.println("Title: "+title);
        visitedLinks.add(url);
        
        return doc;
      }
      return null;
    } 
    catch(IOException e){
      return null;
    }
  }//request

  /** Gets the thread object
      @retun the thread object
  */
  public Thread getThread(){
    return thread;
  }//getThread

}//MTWebCrawler
