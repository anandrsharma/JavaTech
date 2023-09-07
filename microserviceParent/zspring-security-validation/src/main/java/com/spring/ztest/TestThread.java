package com.spring.ztest;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.query.sqm.InterpretationException;

public class TestThread {
	
   public static void main(final String[] arguments) throws InterruptedException, ExecutionException {
      ExecutorService executor = Executors.newFixedThreadPool(2);
      // Cast the object to its class type
      ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

      //Stats before tasks execution
      System.out.println("Largest executions: "+ pool.getLargestPoolSize());
      System.out.println("Maximum allowed threads: "+ pool.getMaximumPoolSize());
      System.out.println("Current threads in pool: "+ pool.getPoolSize());
      System.out.println("Currently executing threads: "+ pool.getActiveCount());
      System.out.println("Total number of threads(ever scheduled): "+ pool.getTaskCount());

      Future f1 = executor.submit(new Task());
      Future f2 = executor.submit(new Task());
      System.out.println("Future f1: " + f1.get());
      System.out.println("Future f2: " + f2.get());
      
      Future f3 = executor.submit(new CallTask1());
      System.out.println("Future f3: " + f3.get());
      
      //Stats after tasks execution
      System.out.println("Core threads: " + pool.getCorePoolSize());
      System.out.println("Largest executions: "+ pool.getLargestPoolSize());
      System.out.println("Maximum allowed threads: "+ pool.getMaximumPoolSize());
      System.out.println("Current threads in pool: "+ pool.getPoolSize());
      System.out.println("Currently executing threads: "+ pool.getActiveCount());
      System.out.println("Total number of threads(ever scheduled): "+ pool.getTaskCount());
      executor.shutdown();
      ConcurrentHashMap<String, String> c = new ConcurrentHashMap();
      c.put("A","A");
   }  

   static class Task implements Runnable {
      public void run() {
         try {
            Long duration = (long) (Math.random() * 5);
            System.out.println("Running Task! Thread Name: " +Thread.currentThread().getName());
               TimeUnit.SECONDS.sleep(duration);
            System.out.println("Task Completed! Thread Name: " +Thread.currentThread().getName());
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
   }
   
   static class CallTask1 implements Callable<String> {
	      public String call() {
	    	  try {
	    		  Thread.sleep(10000);
	    	  } catch(InterruptedException ie) {
	    		  System.out.println("Error in call() method: "+ie);
	    	  }
	    	  return "Hello Future Method... I am Call Method";
	         
	      }
	}
}