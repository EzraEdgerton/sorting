package taojava.labs.sorting;

import java.io.PrintWriter;

/**
 * A very simple analysis of a few sorting algorithms.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class MergeSortAnalyst
{
  public static void main(String args[])
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    @SuppressWarnings("unchecked")
    Sorter<Integer>[] sorters =
        (Sorter<Integer>[]) new Sorter[] {new MergeSorter<Integer>(),
                                          new MergeSorterB<Integer>(),
                                          new IterativeMergeSorter<Integer>()
                                         };
    String[] sorterNames = { "Merge Sort A", "Merge Sort B", "Iterative Merge"};

    @SuppressWarnings("unchecked")
    ArrayBuilder<Integer>[] builders =
        (ArrayBuilder<Integer>[]) new ArrayBuilder[] {
                                                      SorterAnalyzer.semiSortedIntArrBuilder,
                                                      SorterAnalyzer.decreasingIntArrBuilder,
                                                      SorterAnalyzer.increasingIntArrBuilder,
                                                      SorterAnalyzer.randomIntArrBuilder};
    String[] builderNames = { "SemiSorted", "Decreasing", "Increasing", "Random" };

    SorterAnalyzer.combinedAnalysis(pen, sorters, sorterNames,
                                    SorterAnalyzer.standardIntComparator,
                                    builders, builderNames);
  } // main(String[]
} // SampleAnalysis

/*
 * Notes
 * Here is a sample run of the merge sort test:
 * Sorter          Builder         Input Size      Average Time    Minimum Time    Maximum Time    
------          -------         ------------    ------------    ------------    ------------    
Merge Sort A    SemiSorted             10000               2               0               8
Merge Sort A    SemiSorted             20000               2               2               4
Merge Sort A    SemiSorted             40000               6               4               9
Merge Sort B    SemiSorted             10000               5               2               7
Merge Sort B    SemiSorted             20000               5               1              13
Merge Sort B    SemiSorted             40000               4               3               7
Iterative Merge SemiSorted             10000               1               1               3
Iterative Merge SemiSorted             20000               2               2               4
Iterative Merge SemiSorted             40000               6               4              10
Merge Sort A    Decreasing             10000               0               0               1
Merge Sort A    Decreasing             20000               2               1               3
Merge Sort A    Decreasing             40000               4               3               7
Merge Sort B    Decreasing             10000               1               0               2
Merge Sort B    Decreasing             20000               2               1               4
Merge Sort B    Decreasing             40000               5               3               9
Iterative Merge Decreasing             10000               1               1               3
Iterative Merge Decreasing             20000               3               2               5
Iterative Merge Decreasing             40000               7               4              10
Merge Sort A    Increasing             10000               0               0               1
Merge Sort A    Increasing             20000               2               1               4
Merge Sort A    Increasing             40000               5               3               8
Merge Sort B    Increasing             10000               0               0               1
Merge Sort B    Increasing             20000               1               1               3
Merge Sort B    Increasing             40000               4               3               7
Iterative Merge Increasing             10000               1               1               3
Iterative Merge Increasing             20000               2               2               5
Iterative Merge Increasing             40000               6               4              10
Merge Sort A    Random                 10000               2               1               4
Merge Sort A    Random                 20000               4               3               8
Merge Sort A    Random                 40000              10               7              15
Merge Sort B    Random                 10000               1               0               2
Merge Sort B    Random                 20000               1               1               2
Merge Sort B    Random                 40000               5               3               9
Iterative Merge Random                 10000               1               1               3
Iterative Merge Random                 20000               3               2               5
Iterative Merge Random                 40000               6               4              10

Merge Sort A is the one that performs log(n) array creations, B has one scratch array, and Iterative
is the iterative merge sort. For Semisorted and Random arrays, Iterative and B tend to be the best
but for Increasing and Decreasing arrays, A and B tend to be better. 

*/
