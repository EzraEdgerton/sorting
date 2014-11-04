package taojava.labs.sorting;

import java.io.PrintWriter;

/**
 * A very simple analysis of a few sorting algorithms.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class InsertionSortAnalyst
{
  public static void main(String args[])
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    @SuppressWarnings("unchecked")
    Sorter<Integer>[] sorters =
        (Sorter<Integer>[]) new Sorter[] {new InsertionSorter<Integer>(),
                                          new InsertionSorterB<Integer>(),
                                          new InsertionSorterC<Integer>()
                                         };
    String[] sorterNames = { "InsertionA", "InsertionB", "InsertionC"};

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
 * Here is a sample run of the Analyst:
 * Sorter          Builder         Input Size      Average Time    Minimum Time    Maximum Time    
------          -------         ------------    ------------    ------------    ------------    
InsertionA      SemiSorted             10000              12               9              24
InsertionA      SemiSorted             20000              36              33              40
InsertionA      SemiSorted             40000             129             126             134
InsertionB      SemiSorted             10000              10               8              28
InsertionB      SemiSorted             20000              38              34              45
InsertionB      SemiSorted             40000             139             135             144
InsertionC      SemiSorted             10000               9               4              49
InsertionC      SemiSorted             20000              22              19              27
InsertionC      SemiSorted             40000              79              76              84
InsertionA      Decreasing             10000             127             124             132
InsertionA      Decreasing             20000             500             494             516
InsertionA      Decreasing             40000            1985            1978            2012
InsertionB      Decreasing             10000             144             139             150
InsertionB      Decreasing             20000             552             546             572
InsertionB      Decreasing             40000            2186            2170            2233
InsertionC      Decreasing             10000              76              71              84
InsertionC      Decreasing             20000             291             288             295
InsertionC      Decreasing             40000            1154            1150            1163
InsertionA      Increasing             10000               0               0               1
InsertionA      Increasing             20000               0               0               0
InsertionA      Increasing             40000               0               0               1
InsertionB      Increasing             10000               0               0               0
InsertionB      Increasing             20000               0               0               1
InsertionB      Increasing             40000               0               0               1
InsertionC      Increasing             10000               0               0               0
InsertionC      Increasing             20000               0               0               1
InsertionC      Increasing             40000               0               0               1
InsertionA      Random                 10000              68              64              73
InsertionA      Random                 20000             269             265             271
InsertionA      Random                 40000            1183            1158            1215
InsertionB      Random                 10000              73              70              81
InsertionB      Random                 20000             293             288             297
InsertionB      Random                 40000            1237            1228            1244
InsertionC      Random                 10000              43              40              48
InsertionC      Random                 20000             169             164             176
InsertionC      Random                 40000             741             735             748
 * 
 * Insertion A is the Insertion Sort with the call to the swap method, B is the Insertion
 * Sort with an inline swap, and C is the one with shifting instead of swapping. Looking at the
 * times, C is by far the best method because we perform half as many steps as with swapping. A 
 * and B are comparable, but for some reason B is slightly slower. 
 * 
 */