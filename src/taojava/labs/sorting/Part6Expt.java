package taojava.labs.sorting;

import java.io.PrintWriter;
import java.util.Random;


/*
 * Testing suite for comparing our the efficiency of a generic insertion sorter with an 
 * insertion sorter build exclusively for ints
 */
public class Part6Expt
{
  public static void main(String args[])
  {
    // We make sorter for generics
    IntegerInsertionSorter intSort = new IntegerInsertionSorter();
    InsertionSorterC<Integer> TSort = new InsertionSorterC<Integer>();
    PrintWriter pen = new PrintWriter(System.out, true);

    String[] sorterNames = { "Generic Sorter", "Specialized" };
    @SuppressWarnings("unchecked")
    ArrayBuilder<Integer>[] builders =
        (ArrayBuilder<Integer>[]) new ArrayBuilder[] {
                                                      SorterAnalyzer.semiSortedIntArrBuilder,
                                                      SorterAnalyzer.decreasingIntArrBuilder,
                                                      SorterAnalyzer.increasingIntArrBuilder,
                                                      SorterAnalyzer.randomIntArrBuilder };
    // Generate table
    String[] builderNames =
        { "SemiSorted", "Decreasing", "Increasing", "Random" };

    pen.printf("%-16s%-16s%-16s%-16s%-16s%-16s\n", "Sorter", "Builder",
               "Input Size", "Average Time", "Minimum Time", "Maximum Time");
    pen.printf("%-16s%-16s%-16s%-16s%-16s%-16s\n", "------", "-------",
               "------------", "------------", "------------", "------------");
    // We loop through all the possible builders we created for int
    for (int b = 0; b < 4; b++)
      {
        // We test both the generic and integer specific sorters
        for (int s = 0; s < 2; s++)
          {
            // Test sizes 10000,20000,40000
            for (int sz = 10000; sz <= 40000; sz *= 2)
              {
                // If s==0, we run generic insertion sorter
                if (s == 0)
                  {
                    long[] stats =
                        SorterAnalyzer.compoundAnalysis(TSort,
                                                        SorterAnalyzer.standardIntComparator,
                                                        builders[b], sz, 12);
                    pen.printf("%-16s%-16s%12d    %12d    %12d    %12d\n",
                               sorterNames[0], builderNames[b], sz, stats[0],
                               stats[1], stats[2]);
                  }// if
                // Otherwise, we run int specific insertion sorter
                else{
                  long[] stats = 
                      IntInsertionSorterCompoundAnalysis(intSort, b, sz, 12);
                  pen.printf("%-16s%-16s%12d    %12d    %12d    %12d\n",
                             sorterNames[1], builderNames[b], sz, stats[0],
                             stats[1], stats[2]);
                }// else
              }// for
          }// for
      }// for

  }// main(String args[])
  
  /**
   * Build arrays of integer values that are mostly sorted
   */
  public static int[] buildSemiSortedArray(int length)
  {
    Random random = new Random();
    // We want 10% of the array to be random, so we perform one swap for 
    // every 20 elements, as each swap makes 2 elements randomly ordered
    int switchNum = length / 20; 
    int[] vals = new int[length];
    // First generate sorted array
    for (int i = 0; i < length; i++)
      {
        vals[i] = i;
      }// for
    // Swap around 10% of elements
    for (int i = 0; i < switchNum; i++)
      {
        int a = random.nextInt(length);
        int b = random.nextInt(length);
        // Actual swap
        int tmp = vals[a];
        vals[a] = vals[b];
        vals[b] = tmp;
      }// for
    return vals;
  }// buildSemiSortedArray(int length)
  
  /**
   * Build arrays of integer values in decreasing order.
   */
  public static int[] buildDecreasingArray(int length)
  {
    int[] vals = new int[length];
    // Fill array with descending values
    for (int i = 0; i < length; i++)
      vals[i] = length - i;
    // Return array
    return vals;
  }// buildDecreasingArray(int length)

  
  /** 
   * Build arrays of integer values in increasing order.
   */
  public static int[] buildIncreasingArray(int length)
  {
    int[] vals = new int[length];
    // Fill array with ascending values
    for (int i = 0; i < length; i++)
      vals[i] = i;
    // Return array
    return vals;
  }// buildIncreasingArray(int length)

  
  /**
   * Build arrays of random integer values.
   */
  public static int[] buildRandomArray(int length)
  {
    int[] vals = new int[length];
    Random random = new Random();
    // Loop through, filling with random elements
    for (int i = 0; i < length; i++)
      vals[i] = random.nextInt(length);
    return vals;
  }// buildRandomArray(int length)

  
  /**
   * Determine the amount of time sorter takes to sort an array of
   * the given size created by builder
   *
   * @param sorter
   *   The sorting routine we are testing.
   * @param size
   *   The size of the array that we sort.
   */
  public static long IntInsertionSorterAnalysis(IntegerInsertionSorter sorter,
                                         int[] vals)
  {
    SimpleTimer timer = new SimpleTimer();
    System.gc();

    timer.start();

    // Do the real work.
    try
      {
        sorter.sort(vals);
      }// try
    catch (StackOverflowError soe)
      {
        return -1;
      }// catch

    // Stop the timer.
    timer.pause();

    // And report the time taken
    return timer.elapsed();
  } // basicAnalysis(Sorter<T>, ArrayBuilder<T>, int)

  
  /**
   * Given a variety of sorters and builders, does some analysis
   * of the specified builder and sorter
   *
   * @param sorter
   *   The objects that do the sorting
   * @param type
   *   Specifies the kind of array to be sorted
   * @param size
   *   size of the array to be sorted
   * @param repetitions
   *   The number of times to test the sorting algorithm
   */
  public static long[]
    IntInsertionSorterCompoundAnalysis(IntegerInsertionSorter sorter, int type,
                                       int size, int repetitions)
  {
    // We initialize max, min to highest and lowest possible values respectively
    int[] vals = null;
    long max = 0;
    long min = Long.MAX_VALUE;
    long avg = 0;
    long temp;
    // We sort each array repetitions times
    for (int i = 0; i < repetitions; i++)
      {
        // Type determines the type of array we sort
        switch (type)
          {
            case 0:
              vals = buildSemiSortedArray(size);
              break;
            case 1:
              vals = buildDecreasingArray(size);
              break;
            case 2:
              vals = buildIncreasingArray(size);
              break;
            case 3:
              vals = buildRandomArray(size);
              break;
          }// switch
        temp = IntInsertionSorterAnalysis(sorter, vals);
        avg += temp;
        // Update min / max if needed
        if (temp < min)
          {
            min = temp;
          }// if
        if (temp > max)
          {
            max = temp;
          }// if
      }// for
    // Calculate average
    avg = avg / repetitions;
    // Return results
    return new long[] { avg, min, max };
  }// IntInsertionSorterCompoundAnalysis
}
/*
 * Notes
 * 
 * Here is a sample run of the test suite:
 * Sorter          Builder         Input Size      Average Time    Minimum Time    Maximum Time    
------          -------         ------------    ------------    ------------    ------------    
Generic Sorter  SemiSorted             10000               7               5              47
Generic Sorter  SemiSorted             20000              22              18              28
Generic Sorter  SemiSorted             40000              78              75              82
Specialized     SemiSorted             10000               2               1               9
Specialized     SemiSorted             20000               7               6              10
Specialized     SemiSorted             40000              25              23              29
Generic Sorter  Decreasing             10000              72              70              77
Generic Sorter  Decreasing             20000             286             281             292
Generic Sorter  Decreasing             40000            1124            1118            1128
Specialized     Decreasing             10000              25              23              31
Specialized     Decreasing             20000              95              93             100
Specialized     Decreasing             40000             372             370             376
Generic Sorter  Increasing             10000               0               0               1
Generic Sorter  Increasing             20000               0               0               1
Generic Sorter  Increasing             40000               0               0               0
Specialized     Increasing             10000               0               0               0
Specialized     Increasing             20000               0               0               0
Specialized     Increasing             40000               0               0               1
Generic Sorter  Random                 10000              42              40              46
Generic Sorter  Random                 20000             171             165             178
Generic Sorter  Random                 40000             751             746             757
Specialized     Random                 10000              14              12              16
Specialized     Random                 20000              48              47              54
Specialized     Random                 40000             189             185             196

 * The Specialized sorter is significantly faster in all tests than the generic sorter,
 * likely because of the overhead of having generics in Java
 * 
 */
