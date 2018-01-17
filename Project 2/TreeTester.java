/**
* CS5900-06 Algorithms
* Dr. Zhao
* Project 2
*
* Author: Steve Jia
* Date: 4/13/2017
* Filename: TreeTester.java
* Description: a tester program for the Binary Search Tree data structure. the
*   program will start with two small tests to make sure the binary search
*   tree is working correctly, and then it will generate a random set of
*   integers, store them in a tree, and then find the tree's height. Finally,
*   the program will read in some text files, store distinct words in trees,
*   and then report the tree height.
**/

import java.io.*;
import java.util.*;

public class TreeTester
{
   /**
   * Method: main()
   * Description: entry of the program
   **/
   public static void main(String[] args)
   {
      //BST Test
      bstIntTest();
      bstStrTest();
      
      //Integer Section:
      int arraySize = 1200;
      int[] heightArray = new int[arraySize];
      for(int i = 0; i < arraySize; i++)
      {
         heightArray[i] = getTreeHeight();
      }
      
      try
      {
         int[] trips = ArrayTools.saveToFile(heightArray);
         System.out.println();
         System.out.println("Integer Array Tree Heights: ");
         System.out.println("     Min: " + trips[0]);
         System.out.println("     Max: " + trips[1]);
         System.out.println("     Avg: " + trips[2]);
         System.out.println();
      }
      catch(Exception x)
      {
         System.out.println(x.getMessage());
         return;
      }

      //Books Section:
      File[] books = FileTools.findBooks();
      if(books == null || books.length < 1)
      {
         System.out.println("No Books Found or Invalid Directory");
         return;
      }
      for(File book : books)
      {
         System.out.println("Book Tree Height: " + getBookHeight(book));
         System.out.println();
      }
      
      //Extra
      ostIntTest();
   }//end: main()

   /**
   * Method: getTreeHeight()
   * Description: generate an array of distinct numbers and store them in a
   * BST, and then report the tree's height
   **/
   private static int getTreeHeight()
   {
      //generate an integer array
      int[] intArray = ArrayTools.getRandArray();
      //create a new BST object
      BinarySearchTree<Integer> intBst = new BinarySearchTree<Integer>();
      //insert integers into the tree
      for(int i : intArray)
      {
         intBst.insert(i);
      }
      //report the tree's height
      return (intBst.findHeight(intBst.root));
   }//end: getTreeHeight()

   /**
   * Method: getBookHeight()
   **/
   private static int getBookHeight(File book)
   {
      //read in all the words in a book
      List<String> words = FileTools.getWords(book);
      //declare a new BST object
      BinarySearchTree<String> strBst = new BinarySearchTree<String>();
      //insert all the words into the tree, insert method will only store
      //  distinct words
      for (String str : words)
      {
         strBst.insert(str);
      }
      //report number of distinct words
      int numDistinct = strBst.size(strBst.root);
      System.out.println("Title: " + book.toString() + " Total Words: " + words.size() + " Distinct Words: " + numDistinct);
      //report tree's height
      return (strBst.findHeight(strBst.root));
   }//end: getBookHeight()
   
   private static void bstIntTest()
   {
      System.out.println("---Small Array Test---");
      int[] smallArray = new int[]{45,5,136,7,326,12,67,32,11,80,59};
      System.out.println("Original Array: " + ArrayTools.toString(smallArray));
      BinarySearchTree<Integer> smallTree = new BinarySearchTree<Integer>();
      for(int i : smallArray){ smallTree.insert(i); }
      System.out.println("Tree: " + smallTree.toString());
      System.out.println("Tree Size: " + smallTree.size(smallTree.root));
      System.out.println("Tree Height: " + smallTree.findHeight(smallTree.root));
      System.out.println("Tree Min: " + smallTree.bstMin(smallTree.root).getKey());
      System.out.println("Tree Max: " + smallTree.bstMax(smallTree.root).getKey());
      smallTree.delete(smallTree.search(11));
      smallTree.delete(smallTree.search(45));
      System.out.println("Tree: " + smallTree.toString());
      System.out.println("---End---");
   }
   
   
   private static void ostIntTest()
   {
      try
      {
         System.out.println("---Order Statistics Test---");
         int[] smallArray = new int[]{34,5,136,70,226,58,67,21,311,180,112};
         System.out.println("Original Array: " + ArrayTools.toString(smallArray));
         BinarySearchTree<Integer> smallTree = new BinarySearchTree<Integer>();
         for(int i : smallArray){ smallTree.insert(i); }
         System.out.println("Tree: " + smallTree.toString());
         System.out.println("4th Smallest: " + smallTree.select(smallTree.root, 4).getKey());
         System.out.println("67's Rank: " + smallTree.rank(smallTree.root, 67));
      }
      catch(Exception x)
      {
         System.out.println(x.getMessage());
      }
   }
   
   
   private static void bstStrTest()
   {
      System.out.println("---Small Array Test---");
      String[] smallArray = new String[]{"jack","cold","cars","car","bottlerocket","phone","android","jackhammer"};
      System.out.println("Original Array: " + ArrayTools.toString(smallArray));
      BinarySearchTree<String> smallTree = new BinarySearchTree<String>();
      for(String i : smallArray){ smallTree.insert(i); }
      System.out.println("Tree: " + smallTree.toString());
      System.out.println("Tree Size: " + smallTree.size(smallTree.root));
      System.out.println("Tree Height: " + smallTree.findHeight(smallTree.root));
      System.out.println("Tree Min: " + smallTree.bstMin(smallTree.root).getKey());
      System.out.println("Tree Max: " + smallTree.bstMax(smallTree.root).getKey());
      smallTree.delete(smallTree.search("cold"));
      smallTree.delete(smallTree.search("jack"));
      System.out.println("Tree: " + smallTree.toString());
      System.out.println("---End---");
   }
   
}//end: TreeTester class

class ArrayTools
{
  public static int[] getRandArray()
  {
     //initialize an array size of 2^14
     int[] tempArr = initArray((int)Math.pow(2, 14));
     //randomize the array
     randomize(tempArr);
     return tempArr;
  }//end: getRandArray

  public static int[] initArray(int arraySize)
  {
     //initialize an array of certain size
     int[] ar = new int[arraySize];
     //populate array with sequential numbers
     for(int i = 0; i < arraySize; i++)
     {
        ar[i] = i+1;
     }
     //return object reference
     return ar;
  }//end: initArray()

  public static void swap(int[] ar, int i, int j)
  {
     //swap two values in a given array
     int temp = ar[i];
     ar[i] = ar[j];
     ar[j] = temp;
  }//end: swap()

  public static void randomize(int[] ar)
  {
     Random rand = new Random();
     for(int i = ar.length-1; i > 0; i--)
     {
       //generate a random index number
       int randIndex = rand.nextInt(i+1);
       //swap the values within an array
       swap(ar, i, randIndex);
     }
  }//end: randomize()

  public static String toString(int[] ar)
  {
     //print out elements in an array
     String str = "[";
     for(int i : ar)
     {
        str += i + " ";
     }
     str += "]";
     return str;
  }//end: toString()
  
  public static int[] saveToFile(int[] ar)
      throws IOException
  {      
      int min = new Integer(ar[0] > ar[1] ? ar[1] : ar[0]);
      int max = new Integer(ar[0] > ar[1] ? ar[0] : ar[1]);
      
      int sum = 0;
      sum += ar[0];
      sum += ar[1];

      PrintStream ps = new PrintStream(
                              new FileOutputStream("HeightValues.txt",true));
      ps.println(ar[0]);
      ps.println(ar[1]);
      ps.flush();
      
      for(int i = 2; i < ar.length; i++)
      {
         if(ar[i] > max)
         {
            max = ar[i];
         }
         else if(ar[i] < min)
         {
            min = ar[i];
         }
         sum += ar[i];
         ps.println(ar[i]);
      }//end for-loop
            
      int avg = sum/ar.length;
      ps.println("MIN: " + min);
      ps.println("MAX: " + max);
      ps.println("AVG: " + avg);

      ps.flush();
      ps.close();
      
      return (new int[]{min,max,avg});
  }//end saveToFile()
  
  public static String toString(String[] ar)
  {
     //print out elements in an array
     String str = "[";
     for(String i : ar)
     {
        str += i + " ";
     }
     str += "]";
     return str;
  }//end: toString()

}//end: ArrayTools class


class FileTools
{
   public static File[] findBooks()
   {
      File[] books = null;
      //create a File object on current directory
      File dir = new File(".\\Books\\");
      if(dir != null)
      {
         //list all the book files in the directory. BookFilter() is not
         //  necessary if all the book files are in a directory by themselves
         books = dir.listFiles(new BookFilter());
      }
      return books;
   }//end: findBooks()

   public static List<String> getWords(File book)
   {
      //use a scanner object to open the text files
      Scanner scan = null;
      try
      {
         scan = new Scanner(book);
      }
      catch(Exception e)
      {
         System.out.println(e.toString());
         return null;
      }

      //create an ArrayList object to store the words
      List<String> words = new ArrayList<String>();

      while(scan.hasNext())
      {
         /* read in each word, use a StringBuffer to store the alphanumeric
         *  characters, and use the toLowerCase() method to convert all
         *  upper case characters */
         String word = scan.next();
         StringBuffer strBuff = new StringBuffer();
         for(int i = 0; i < word.length(); i++)
         {
            if(Character.isLetterOrDigit(word.charAt(i)))
            {
               strBuff.append(Character.toLowerCase(word.charAt(i)));
            }
            //add the cleaned up string into the list
            words.add(strBuff.toString());
         }
      }
      return words;
   }//end: getWords()
}//end: FileTools class

//implements the FilenameFilter interface and override its accept() method
class BookFilter implements FilenameFilter{
   @Override
   public boolean accept(File dir, String name)
   {
      //include only book text files
      return name.startsWith("Book") && name.endsWith(".txt");
   }
}//end: BookFilter class

/*class MinMaxAvg<Integer, Integer, Integer>
{
   public Integer min;
   public Integer max;
   public Integer avg;
   public MinMaxAvg(int x, int y, int avg)
   {
      min = new Integer(x);
      max = new Integer(y);
      avg = new Integer(avg);
   }
}*/