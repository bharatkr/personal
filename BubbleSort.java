
//package sort3;

import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;

/**
 *
 * Bharat Radhakrishnan
 * 9/29/2017
 * INFS 519 Assignment 1
 */
class Output
{
 public void print(int[] Arrayline)
 {
     //writing the output file to a destination path
    File output =new File ("F:/output/isort.txt");
         try
         {    
	FileOutputStream fos = new FileOutputStream(output);
 
	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        int i=0;
       	while(i<Arrayline.length)
            {
                
                bw.write(String.valueOf(Arrayline[i]));
		bw.newLine();
                 i++;
            }
         bw.close();     
        } 
        catch (Exception ex)
        {
            System.out.print(ex.getMessage());
        }
        
 }  
}
 
class BubbleSort
{
     public double bubbleSort(String Arrayline[])
    {
        //converting the input string to int
        int intArray[];
        intArray=new int[Arrayline.length];
        for(int i=0;i<Arrayline.length;i++)
            intArray[i]=Integer.parseInt(Arrayline[i]);
        // Bubble Sort begins
        
        long start,end,duration;
        start=System.nanoTime();
        double Inversions =0;
        int temp;
        int n = intArray.length;
        
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (intArray[j] > intArray[j+1])
                {
                    // swap the variables
                    temp = intArray[j];
                    intArray[j] = intArray[j+1];
                    intArray[j+1] = temp;
                    Inversions++;
                }
        //end of bubble sort
        end=System.nanoTime();
        duration = end-start;
        System.out.println("BUBBLE SORT for" +" " + intArray.length +" " +"integers");
        System.out.println("The duration of Bubble Sort is " +duration);
        Output o =new Output();
        o.print(intArray);
        return Inversions;
    }

}
class InsertionSort
{
     public double Sort2(String Arrayline[])
    {
        //converting the input string to int
        int intArray[];
        intArray=new int[Arrayline.length];
        for(int i=0;i<Arrayline.length;i++)
                    intArray[i]=Integer.parseInt(Arrayline[i]);
        // Insertion Sort begins    
        long start,end,duration;
        start=System.nanoTime();
        double Inversions=0;
        int temp;
        int n = intArray.length;
         for (int i = 1; i < n; i++) 
         	  for (int j = i; j > 0; j--) 
          	  if (intArray[j] < intArray[j - 1])
          {		
            //Swaping the variables 
            temp = intArray[j];
            intArray[j] = intArray[j - 1];
	    intArray[j - 1] = temp;
	    Inversions++;
          }
         
          
        //end of Insertion Sort      
        end=System.nanoTime();
        duration = end-start;
        System.out.println("INSERTION SORT for" +" " + intArray.length +" " +"integers");
        
        System.out.println("The duration of Insertion Sort is " +duration);
        Output o =new Output();
        o.print(intArray);
        return Inversions;
   
}
    }
class MergeSort
{

public void sort4(int[] intArray , int a, int m, int n)
	    {
             
        long start,end,duration;
        start=System.nanoTime();
        
	        // Find sizes of two subarrays to be merged
	        int n1 = m - a + 1;
	        int n2 = n - m;
	 
	      //   Create temp arrays 
	        int L[] = new int [n1];
	        int R[] = new int [n2];
	 
	      //  Copy data to temp arrays
	        for (int i=0; i<n1; ++i)
	            L[i] = intArray[a + i];
	        for (int j=0; j<n2; ++j)
	            R[j] = intArray[m + 1+ j];
	 	
	        int i = 0, j = 0;
	 
	        // Initial index of merged subarry array
	        int k = a;
	        while (i < n1 && j < n2)
	        {
	            if (L[i] <= R[j])
	            {
	                intArray[k] = L[i];
	                i++;
	            }
	            else
	            {
	                intArray[k] = R[j];
	                j++;
	            }
	            k++;
	        }
	 
	        // Copy remaining elements of L[] 
	        while (i < n1)
	        {
	            intArray[k] = L[i];
	            i++;
	            k++;
	        }
	 
	        // Copy remaining elements of R[]
	        while (j < n2)
	        {
	            intArray[k] = R[j];
	            j++;
	            k++;
	        }
                
                Output o =new Output();
                o.print(intArray); 
            }
	 
	    // mergesort begins
	    public void sort(int[] intArray, int a, int n)
	    {  
                        //Sort begins

	        if (a < n)
	        {
                    // Find the middle point
	            int m = (a+n)/2;
                    // Sort first and second halves
	            sort(intArray, a, m);
	            sort(intArray , m+1, n);
	            // Merge the sorted halves
	            sort4(intArray, a, m, n);
        	}
        
	    }

}
    


public class Sort3
{

    public static void main(String[] args) 
{
      File input = new File("F:/INFS 519/Assignment/A1-100-integers.txt");
      
      try 
        {
            List<String> inputData = new ArrayList<String>();
            Scanner inputFile = new Scanner(input);
            int index=0;
            String[] Arrayline;
         
            while(inputFile.hasNext())
            {
              inputData.add(inputFile.next());
                
            }
            Arrayline=new String[inputData.size()];
            Arrayline=inputData.toArray(Arrayline);
            double Inversions1,Inversions;
            
            //Calling Bubble Sort
            BubbleSort bubble = new BubbleSort();
            Inversions1 = bubble.bubbleSort(Arrayline);
           
            System.out.println("The number of inversions for Bubble Sort are " + Inversions1);
            
             //Calling Insertion Sort
            InsertionSort insert = new InsertionSort();
            Inversions = insert.Sort2(Arrayline);
           
            System.out.println("The number of inversions for Insertion Sort are " + Inversions);
           
           //Calling Merge Sort
         
           
           int[] intArray=new int[Arrayline.length];
           for(int i=0;i<Arrayline.length;i++)
            intArray[i]=Integer.parseInt(Arrayline[i]);
          
           
           MergeSort merge = new MergeSort();    
           
           long start,end,duration;
           start = System.nanoTime();
           merge.sort(intArray,0,intArray.length-1);
           end=System.nanoTime();
           duration = end-start;
             System.out.println("MERGE SORT for" +" " + intArray.length +" " +"integers");
           System.out.println("The duration of Merge Sort is " +duration);
        }
      catch (Exception ex) 
        {
            Logger.getLogger(Sort3.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}






     
    
  

