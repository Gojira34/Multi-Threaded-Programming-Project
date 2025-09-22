/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.multithreadingproject;
import java.util.Scanner;
import java.util.Arrays;
/**
 * Main MultiThreading project that demonstrates splitting an array into two parts 
 * sorting them separately in parallel threads
 * and then retrieving the sorted arrays
 * Merging the two sorted arrays into one
 * @authors Nil Patel, Jaylen Perry, Payne Micael, Josue Ndeko
 */

//@author Jaylen Perry, Nil Patel
// Runnable class that performs selection sort on an array
class SortThread implements Runnable {
    private int[] SortingArray;     // The array this thread will sort
    
    // Constructor: takes an array and makes a copy of it
    public SortThread(int[] SortingArray) {
        this.SortingArray = Arrays.copyOfRange(SortingArray, 0, SortingArray.length);
    }
    
    @Override
    public void run() {
        // Perform Selection Sort on SortingArray
        for (int i = 0; i < SortingArray.length; i++) {
            int minNumberIndex = i;     // Assume current element is smallest
            for (int j = i + 1; j < SortingArray.length; j++) {
                // Find index of smallest element
                if (SortingArray[minNumberIndex] > SortingArray[j]) {
                    minNumberIndex = j;
                }
            }
            // Swap smallest element with current index if needed
            if (minNumberIndex != i) {
                int temp = SortingArray[i];
                SortingArray[i] = SortingArray[minNumberIndex];
                SortingArray[minNumberIndex] = temp;
            }
        }
        
        //This will print the Thread that sorted the corresponding array
        System.out.println("Thread " + Thread.currentThread().threadId() + " sorted Array: " + Arrays.toString(SortingArray));
    }
    
    // Method to retrieve the sorted array after thread finishes
    public int[] getSortedArray() {
        return SortingArray;
    }
}

public class MultiThreadingProject {
    
    public static void main(String[] args) {
        //@author Payne Micael
        Scanner input = new Scanner(System.in);
        
        // Declares Array for data
        int[] Array;            // Full array entered by user
        int[] FirstHalfArray;   // First half of Array
        int[] SecondHalfArray;  // Second half of Array
        int[] sortedMerged;     // Fully Sorted and Merged Array
        
        // Asks for user to enter the Size of the array
        System.out.print("Enter the Size of the Sequence: ");
        Array = new int[input.nextInt()];
        
        // Ask for user to enter the numbers to be sorted
        System.out.print("Enter the numbers to be sorted with a space in between: ");
        // inputs the numbers in the array
        for (int i = 0; i < Array.length; i++) {
            Array[i] = input.nextInt();
        }
        
        // Print the Original Array
        System.out.println("Original Array: " + Arrays.toString(Array));
        
        // Splits the Array into two Array
        FirstHalfArray = Arrays.copyOfRange(Array, 0, Array.length/2);
        SecondHalfArray = Arrays.copyOfRange(Array, Array.length/2, Array.length);
        
        //print both halves
        System.out.println("First half of the array: " + Arrays.toString(FirstHalfArray));
        System.out.println("Second half of the array: " + Arrays.toString(SecondHalfArray));
        
        //@author Nil Patel
        // Create SortThread objects for each half
        SortThread sortFirstHalf = new SortThread(FirstHalfArray);
        SortThread sortSecondHalf = new SortThread(SecondHalfArray);
        
        // Create and start two separate threads
        Thread thread0 = new Thread(sortFirstHalf);
        Thread thread1 = new Thread(sortSecondHalf);
        thread0.start();
        thread1.start();
        
        try {
            // Wait for both threads to finish before continuing
            thread0.join();
            thread1.join();
	} catch (InterruptedException ie) { }
        
        // sends the firsthalfarray and secondhalfarray to get merged and returns it into sortedMerged
        sortedMerged = mergeArrays(sortFirstHalf.getSortedArray(), sortSecondHalf.getSortedArray());
        
        //prints the merged array
        System.out.println("Merged Array: " + Arrays.toString(sortedMerged));
    }
    
    //@author Josue Ndeko, Nil Patel
    // method to merge two sorted arrays into one
    static int[] mergeArrays(int[] firstHalf, int[] secondHalf) {
        int i = 0; //pointer for frstHalf Array
        int j = 0; // pointer for secondHalf Array
        int k = 0; //pointer for merged array
        int[] merged = new int[firstHalf.length + secondHalf.length];

        // merge elements in sorted order
        while (i < firstHalf.length && j < secondHalf.length) {
            if (firstHalf[i] <= secondHalf[j]) {
                merged[k++] = firstHalf[i++];
            } else {
                merged[k++] = secondHalf[j++];
            }
        }

        // copy remaining elements from array1
        while (i < firstHalf.length) {
            merged[k++] = firstHalf[i++];
        }

        // copy remaining elements from array2
        while (j < secondHalf.length) {
            merged[k++] = secondHalf[j++];
        }

        return merged;
    }
}