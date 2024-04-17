package com.example;

import java.util.ArrayList;
import java.util.Scanner;
public class Main {
  
  
  public static void main(String[] args) {
    ArrayList<MTWebCrawler> bots = new ArrayList<>();
    ArrayList<String> links=new ArrayList<>();
    //String command;
    String link;
    Scanner scanner = new Scanner(System.in);
    // Create bots
    System.out.println("How many links would you like to explore?");
    int i=scanner.nextInt();
    scanner.nextLine();
    for(int j=1; j<=i; j++){
      System.out.println("Paste a link:");
      link=scanner.nextLine();
      links.add(link);
    }
    for(int k=1; k<=i; k++){
      bots.add(new MTWebCrawler(links.get(k-1), k));
    }
    
    scanner.close();
    Scanner pauseOrResume= new Scanner(System.in);
    System.out.println("Press p to pause the webcrawler, press r to resume the webcrawler");
    char input=pauseOrResume.next().charAt(0);
    if(input=='p'){
      bots.get(0).setPause(true);
    }
    if(input=='r'){
      bots.get(0).setPause(false);
    }
    if(bots.size()==i){
    System.out.println("Web crawler is running. Type 'pause' to pause, 'resume' to resume, 'exit' to quit.");
    
    // Start bots
    for(MTWebCrawler bot : bots){
      try{
        bot.getThread().join();
      }//try
      catch(InterruptedException e){
        e.printStackTrace();
      }//catch  
    }

  }
  String[] phrases=new String[]{"Whew, executed with no exceptions", "TFW you absolutely slayed this OS project", "I found all the links!", "I challenge you to find more links than this at depth level "+i+ " of course (;)",
"Why's it called a webcrawler when I found "+ links.size()+ "in like two seconds?"};
  int index=(int)(Math.random()*phrases.length);
  System.out.println(phrases[index]);
  pauseOrResume.close();
  }
}