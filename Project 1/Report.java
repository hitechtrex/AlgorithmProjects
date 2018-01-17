/***
* <h1>CS59000 - Algorithms</h1>
* <h2>Project 1 - Maximum Subarray Analysis</h2>
* <p><b>Professor:</b>Dr. Zhao</p>
* <p><b>Description:</b>
* The report class keeps track of running times
* for each algorithm by utilizing ArrayList objects.
* The class also has functions that computes running
* time averages and stores the results in a text file.
* </p>
*
* Filename: Report.java
* @author: Steve Jia
* @since: 2017-03-02
* @version: 0.0
**/
import java.util.*;
import java.io.*;

public class Report
{  
   private int n;
   private List<Long> runtimesBrute;
   private List<Long> runtimesRecursive;
   private List<Long> runtimesCombine;
   private List<Long> runtimesDynamic;
   
   public Report(int sampleSize)
   {
      n = sampleSize;
      runtimesBrute = new ArrayList<Long>();
      runtimesRecursive = new ArrayList<Long>();
      runtimesCombine = new ArrayList<Long>();
      runtimesDynamic = new ArrayList<Long>();
   }//end constructor
   
   public void AddToBruteList(long runtime)
   {
      if(runtime > 0L){ runtimesBrute.add(runtime); }
   }
   
   public void AddToRecursiveList(long runtime)
   {
      if(runtime > 0L){ runtimesRecursive.add(runtime); }
   }
   
   public void AddToCombineList(long runtime)
   {
      if(runtime > 0L){ runtimesCombine.add(runtime); }
   }
   
   public void AddToDynamicList(long runtime)
   {
      if(runtime > 0L){ runtimesDynamic.add(runtime); }
   }
   
   /**
   * this method add all the running times together and then returns
   * the average
   **/
   private long Average(List<Long> timeList)
   {
      long sum = 0;
      for(Long time : timeList)
      {
         sum += time.longValue();
      }
      return (sum / timeList.size());
   }
   
   /**
   * this method saves the average running time results into a text file
   **/
   public void SaveResults() throws IOException
   {
      PrintStream ps = new PrintStream(
                           new FileOutputStream("RuntimeReport.txt", true));
      ps.println(this.n + "\t" 
                     + Average(runtimesBrute) + "\t" 
                     + Average(runtimesRecursive) + "\t"
                     + Average(runtimesCombine) + "\t"
                     + Average(runtimesDynamic));
      ps.flush();
      ps.close();
   }
}//end class