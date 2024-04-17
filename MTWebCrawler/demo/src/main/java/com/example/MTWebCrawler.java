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
  private boolean paused;
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
    this.paused=false; //Default value of paused is false
    thread = new Thread(this);
    thread.start();
  }//MTWebCrawler 
  //A set pause method to make the private variable paused, publicly accessible
  public synchronized void setPause(boolean pause){
    this.paused=pause;
    notifyAll(); //Notify potentially waiting threads
  }
  //The synchronized keyword ensures that all threads exhibit the same behavior.
  protected synchronized void checkPaused(){
    while(paused){
      try{
        wait(); //Busy wait until pause is set to false
      }
      catch(InterruptedException e){
        Thread.currentThread().interrupt();
      }
    }
  }
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
          checkPaused();
          String next_link = link.absUrl("href");
          if (!next_link.startsWith("javascript:")) {  // Check if it's not a JavaScript link that leads to nothing
                    if (!visitedLinks.contains(next_link)) {
                        visitedLinks.add(next_link);
                        crawl(level + 1, next_link);
                    }
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
