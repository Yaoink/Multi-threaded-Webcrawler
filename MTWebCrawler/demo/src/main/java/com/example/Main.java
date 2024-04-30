package com.example;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {
  
  
  public static void main(String[] args) {
    System.out.printf("Minimum priority of the thread is '%s' \n", Thread.MIN_PRIORITY);
    System.out.printf("Maxmimum priority of the thread is '%s' \n", Thread.MAX_PRIORITY);
    System.out.printf("Normal priority of the thread is '%s' \n", Thread.NORM_PRIORITY);
    ArrayList<MTWebCrawler> bots = new ArrayList<>();
    
    //asks user for input
    ArrayList<String> links=new ArrayList<>();
    String link;
    Scanner scanner = new Scanner(System.in);
    System.out.println("How many links would you like to explore?");
    int i=scanner.nextInt();
    scanner.nextLine();
    for(int j=1; j<=i; j++){
      System.out.println("Paste a link:");
      link=scanner.nextLine();
      links.add(link);
    }
    KeyEventDemo demo=new KeyEventDemo("demo");
    Thread inputThread=new Thread(demo);
    inputThread.start();

    //creates MTWebCrawler object, each MTWebCralwer starting link has its own thread of execution
    for(int k=1; k<=i; k++){
      bots.add(new MTWebCrawler(links.get(k-1), k));
    }
    
    scanner.close();

    if(bots.size()==i){
    System.out.println("Web crawler is running. Type 'p' to pause execution, 'r' to resume execution and control c to quit");
    
    
    for(MTWebCrawler bot : bots){
      try{
        bot.getThread().join();
      }//try
      catch(InterruptedException e){
        e.printStackTrace();
      }//catch  
    }

  }

  //all the MTWebCrawler threads have been closed at this point
  String[] phrases=new String[]{"Whew, executed with no exceptions", "TFW you absolutely slayed this OS project", "I found all the links!", "I challenge you to find more links than this (;))",
"Why's it called a webcrawler when I found "+ links.size()+ "in like two seconds? Should be called a web sprinter"};
  int index=(int)(Math.random()*phrases.length);
  System.out.println(phrases[index]);

  
}
}



