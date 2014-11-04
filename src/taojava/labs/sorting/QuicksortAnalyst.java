package taojava.labs.sorting;

import java.io.PrintWriter;

/**
 * A very simple analysis of a few sorting algorithms.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class QuicksortAnalyst
{
  public static void main(String args[])
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    @SuppressWarnings("unchecked")
    Sorter<Integer>[] sorters =
        (Sorter<Integer>[]) new Sorter[] {new NewQuicksorter<Integer>(),
                                          new NewQuicksorterB<Integer>(),
                                          new NewQuicksorterC<Integer>(),
                                          new NewQuicksorterD<Integer>()
                                         };
    String[] sorterNames = { "Quick Sort A", "Quick Sort B", "Quick Sort C", "Quick Sort D"};

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
 * 
 * Here is a sample run of the test suite:
 * Sorter          Builder         Input Size      Average Time    Minimum Time    Maximum Time    
------          -------         ------------    ------------    ------------    ------------    
Quick Sort A    SemiSorted             10000               2               2              24
Quick Sort A    SemiSorted             20000               7               5              10
Quick Sort A    SemiSorted             40000              11              11              14
Quick Sort B    SemiSorted             10000               1               0               5
Quick Sort B    SemiSorted             20000               2               2               4
Quick Sort B    SemiSorted             40000               4               4               5
Quick Sort C    SemiSorted             10000               1               1               8
Quick Sort C    SemiSorted             20000               3               2               6
Quick Sort C    SemiSorted             40000               6               6               8
Quick Sort D    SemiSorted             10000               1               1               7
Quick Sort D    SemiSorted             20000               4               3               7
Quick Sort D    SemiSorted             40000               7               7               9
Quick Sort A    Decreasing             10000             295             290             302
Quick Sort A    Decreasing             20000              -1              -1              -1
Quick Sort A    Decreasing             40000              -1              -1              -1
Quick Sort B    Decreasing             10000               1               1               2
Quick Sort B    Decreasing             20000               2               1               4
Quick Sort B    Decreasing             40000               3               3               5
Quick Sort C    Decreasing             10000               1               1               3
Quick Sort C    Decreasing             20000               3               2               6
Quick Sort C    Decreasing             40000               5               5               6
Quick Sort D    Decreasing             10000               2               1               4
Quick Sort D    Decreasing             20000               4               3               7
Quick Sort D    Decreasing             40000               7               6              12
Quick Sort A    Increasing             10000               4               3               7
Quick Sort A    Increasing             20000              12              10              16
Quick Sort A    Increasing             40000              26              25              31
Quick Sort B    Increasing             10000               0               0               1
Quick Sort B    Increasing             20000               2               1               4
Quick Sort B    Increasing             40000               3               3               5
Quick Sort C    Increasing             10000               1               1               3
Quick Sort C    Increasing             20000               3               2               6
Quick Sort C    Increasing             40000               6               5              10
Quick Sort D    Increasing             10000               2               1               4
Quick Sort D    Increasing             20000               4               3               8
Quick Sort D    Increasing             40000               6               6              11
Quick Sort A    Random                 10000               1               1               3
Quick Sort A    Random                 20000               3               3               6
Quick Sort A    Random                 40000               6               5              11
Quick Sort B    Random                 10000               1               1               2
Quick Sort B    Random                 20000               4               2               7
Quick Sort B    Random                 40000               6               6              10
Quick Sort C    Random                 10000               2               1               4
Quick Sort C    Random                 20000               4               3               7
Quick Sort C    Random                 40000               7               7              10
Quick Sort D    Random                 10000               2               1               3
Quick Sort D    Random                 20000               5               3               8
Quick Sort D    Random                 40000               8               7              12

A always chooses the leftmost array element as the pivot, B chooses the middle, C chooses a 
random pivot, and D chooses the median of 3 random samples as the pivot. In the table, -1
means a stack overflow occured. A is terrible because it will overflow on decreasing arrays.
B and C are the two fastest methods, but they hypothetically could overflow. D is slower because
of the median selecting process which happens even at small array sizes, but this may be the 
safest to pick the pivot, because it essentially eliminates the chance of a bad pivot.

*/
