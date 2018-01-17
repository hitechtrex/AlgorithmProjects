/***
* <h1>CS59000 - Algorithms</h1>
* <h2>Project 1 - Maximum Subarray Analysis</h2>
* <p><b>Professor:</b>Dr. Zhao</p>
* <p><b>Description:</b>
* The RuntimeTester class can generate and initilize
* an array of random size and value, and it will
* utilize four different find-max-subarray algorithms
* to record their running times and then report them.
* </p>
*
* Filename: RuntimeTester.java
* @author: Steve Jia
* @since: 2017-03-02
* @version: 0.0
**/

import java.util.*;

public class RuntimeTester
{
   //MaxSubarray is defined for the Dynamic Programming
   // algorithm, but it can be applied to other ones as well
   private static class MaxSubarray
   {
      public int left;
      public int right;
      public int sum;
      
      public MaxSubarray(int newLeft, int newRight, int newSum)
      {
         left = newLeft;
         right = newRight;
         sum = newSum;
      }
      
      public MaxSubarray(MaxSubarray anotherObj)
      {
         this.left = anotherObj.left;
         this.right = anotherObj.right;
         this.sum = anotherObj.sum;
      }
   }//end private class
   
   
   //this is the crossover point for brute force and divide-and-conqure
   private static int crossOverPoint = 360;
   
   //main method for the program
   public static void main (String[] args) throws java.lang.Exception
	{
		int numberOfOuterLoops = 1000;
      int numberOfInnerLoops = 20;
      Report runtimeReport = null;
      //main loop for the program
      for(int i = 0; i < numberOfOuterLoops; i++)
      {
         //generate a new array
         int[] newArray = GenerateArray();
         //create a new report object
         runtimeReport = new Report(newArray.length);
         
         System.out.println("New Array Size: " + newArray.length);
         
         //run the algorithms many times and log their running time in ns
         for(int j = 0; j < numberOfInnerLoops; j++)
         {
            System.out.println("Run #" + i + "." + j);
            //run brute force algorithm
            System.out.print("Starting Brute Force...");
            long startTime = System.nanoTime();
            FindMaxSubBrute(newArray, 0, newArray.length);
            runtimeReport.AddToBruteList(System.nanoTime() - startTime);
            //System.out.println(System.nanoTime() - startTime);
            
            //run recursive algorithm
            System.out.print(" Recursive...");
            startTime = System.nanoTime();
            FindMaxSubRecursive(newArray, 0, newArray.length-1);
            runtimeReport.AddToRecursiveList(System.nanoTime() - startTime);
            //System.out.println(System.nanoTime() - startTime);
         
            //run combined algorithm
            System.out.print(" Combined...");
            startTime = System.nanoTime();
            FindMaxSubCombine(newArray, 0, newArray.length-1);
            runtimeReport.AddToCombineList(System.nanoTime() - startTime);
            //System.out.println(System.nanoTime() - startTime);
         
            //run dynamic programming algorithm
            System.out.print(" Dynamic...");
            startTime = System.nanoTime();
            FindMaxSubDynamic(newArray, 0, newArray.length);
            runtimeReport.AddToDynamicList(System.nanoTime() - startTime);
            //System.out.println(System.nanoTime() - startTime);
            
            System.out.println();
            
            //save the report into a file
            try
            {
               runtimeReport.SaveResults();
            }
            catch(Exception x)
            {
               System.out.println(x.getMessage());
            }
         }//end inner-for-loop
         
         System.out.println("END Loop for Size " + newArray.length);
         System.out.println();
                  
         //save the report into a file
         /*try
         {
            runtimeReport.SaveResults();
         }
         catch(Exception x)
         {
            System.out.println(x.getMessage());
         }*/
      }//end outer-for-loop
	}//end main
   
   /**
   * declares and initialize an array of random size and value
   **/
   private static int[] GenerateArray() throws Exception
   {
      int maxArraySize = 2000;
      int valueLimit = 100;
      Random arraySize = new Random();
      int[] newArray = new int[(arraySize.nextInt(maxArraySize))];
      Random arrayValue = new Random();
      for(int i = 0; i < newArray.length; i++)
      {
         newArray[i] = arrayValue.nextInt(valueLimit)-valueLimit;
      }
      return newArray;
   }//end GenerateArray()
   
   
   /**
   * Find-Max-Subarray Brute Force Algorithm
   * low: start index, high:length
   **/
   private static int[] FindMaxSubBrute(int[] array, int low, int high)
   {
      int sum = Integer.MIN_VALUE;
      int left = 0;
      int right = 0;
      for(int i = low; i < high; i++)
      {
         int currentSum = 0;
         for(int j = i; j < high; j++)
         {
            currentSum += array[i];
            if(sum < currentSum)
            { 
               sum = currentSum;
               left = i;
               right = j;
            }
         }//end j-for-loop
      }//end i-for-loop
      
      return (new int[]{left, right, sum});
   }//end FindMaxSubBrute()
   
   /**
   * Find-Max-Subarray Divide-and-Conqure Algorithm
   **/
   private static int[] FindMaxSubRecursive(int[] array, int low, int high)
   {  
      if(low == high)
      {
         return(new int[]{low, high, array[low]});
      }
      else
      {
         int mid = (high+low)/2;
         int[] leftMax = FindMaxSubRecursive(array, low, mid);
         int[] rightMax = FindMaxSubRecursive(array, mid+1, high);
         int[] crossMax = FindCrossMax(array, low, mid, high);
         
         if(leftMax[2] >= rightMax[2] && leftMax[2] >= crossMax[2])
         {
            return leftMax;
         }
         else if(rightMax[2] >= leftMax[2] && rightMax[2] >= crossMax[2])
         {
            return rightMax;
         }
         else
         {
            return crossMax;
         }
      }//end if-else
   }//end FindMaxSubRecursive()
   
   /**
   * Find-Max-Subarray Combined Algorithm
   **/
   private static int[] FindMaxSubCombine(int[] array, int low, int high)
   {
      if(high - low < crossOverPoint )
      {
         return FindMaxSubBrute(array, low, high);
      }
      else
      {
         int mid = (high+low)/2;
         int[] leftMax = FindMaxSubRecursive(array, low, mid);
         int[] rightMax = FindMaxSubRecursive(array, mid+1, high);
         int[] crossMax = FindCrossMax(array, low, mid, high);
         
         if(leftMax[2] >= rightMax[2] && leftMax[2] >= crossMax[2])
         {
            return leftMax;
         }
         else if(rightMax[2] >= leftMax[2] && rightMax[2] >= crossMax[2])
         {
            return rightMax;
         }
         else
         {
            return crossMax;
         }
      }//end if-else
   }//end FindMaxSubCombine()
   
   /**
   * Find max sum of the cross array
   **/
   private static int[] FindCrossMax(int[] array, int low, int mid, int high)
   {
      int leftSum = Integer.MIN_VALUE;
      int sum = 0;
      int left = mid;
      int right = mid;
      for(int i = mid-1; i >= low; i--)
      {
         sum += array[i];
         if(sum > leftSum)
         {
            leftSum = sum;
            left = i;
         }
      }//end for-loop
      
      int rightSum = Integer.MIN_VALUE;
      sum = 0;
      for(int j = mid+1; j < high; j++)
      {
         sum += array[j];
         if(sum > rightSum)
         {
            rightSum = sum;
            right = j;
         }
      }//end for-loop
      return(new int[]{left, right, leftSum+rightSum});
   }//end FindCrossMax()
   
   /**
   * Find-Max-Subarray Dynamic Programming Algorithm
   **/
   private static MaxSubarray FindMaxSubDynamic(int[] array, int low, int high)
   {
      MaxSubarray aux = new MaxSubarray(low, low+1, array[low]);
      MaxSubarray opt = new MaxSubarray(low, low+1, array[low]);
      
      for(int i = low+1; i < high; i++)
      {
         aux = (aux.sum < 0) ? 
            (new MaxSubarray(i, i+1, array[i])) : (new MaxSubarray(aux.left, i+1, aux.sum+array[i]));
         
         if(aux.sum > opt.sum){ opt = new MaxSubarray(aux); }      
      }//end for-loop
      return opt;
   }//end FindMaxSubDynamic
   
}//end class