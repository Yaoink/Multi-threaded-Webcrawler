package com.example;
//import java.util.Scanner;
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
  protected static boolean paused=false;
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
    //this.paused=false; //Default value of paused is false
    thread = new Thread(this);
    thread.start();
    System.out.println("This is the thread id " +thread.getId());
  }
  
 
  //MTWebCrawler 
  //A set pause method to make the private variable paused, publicly accessible
  /** public synchronized void setPause(boolean pause){
    this.paused=pause;
    notifyAll(); //Notify potentially waiting threads
    protected synchronized void checkPaused(){
    while(paused=false){
      try{
        Thread.sleep(5000); //Busy wait until pause is set to false
      }
      catch(InterruptedException e){
        Thread.currentThread().interrupt();
      }
    }
    
  }
  } */
 
  //The synchronized keyword ensures that all threads exhibit the same behavior.
  /* public void handleUserInput() {
    Scanner pauseOrResume= new Scanner(System.in);
    System.out.println("Press p to pause the webcrawler, press r to resume the webcrawler");
    
        char input=pauseOrResume.next().charAt(0);
        while(input=='p'){
          paused=true;
        }
        if(input=='r'){
          paused=false;
        }
        pauseOrResume.close();
    } */
 

    

  // Runs the web crawler
  @Override
  public void run(){
    crawl(1, first_link);
  }//run
  private static final Object lock = new Object();

  private static void getPause() {
    synchronized (lock) {
        while (paused) {
            try {
                lock.wait();  // Wait until notified
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
public static void setPause(boolean b) {
  synchronized (lock) {
      paused=b;
      if (!paused) {
          lock.notifyAll();
      }
  }
}

  /** Recursively crawls webpages
      @param the current depth of the crawler
      @param the current link
  */
  private void crawl(int level, String url){
    getPause();
    if (level <= MAX_DEPTH){
      Document doc = request(url);

      if (doc != null){
        for (Element link : doc.select("a[href]")){
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
      System.out.println("The webcralwer of "+ ID + " encountered an error of some kind");
      e.printStackTrace();
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
