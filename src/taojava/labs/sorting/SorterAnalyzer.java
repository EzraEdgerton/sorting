package taojava.labs.sorting;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Random;

/**
 * Tools for analyzing sorters.
 *
 * @author Samuel A. Rebelsky
 * @author Ezra Edgerton
 * @author William Royle
 * @author Patrick Slough
 */
public class SorterAnalyzer
{
  // +---------------+---------------------------------------------------
  // | Configuration |
  // +---------------+

  /**
   * The number of repetitions we do in gathering statistics.
   */
  static final int REPETITIONS = 12;

  /**
   * The smallest array size we use.
   */
  static final int SMALLEST = 10000;

  /**
   * The largest array size we use.
   */
  static final int LARGEST = 40000;

  /**
   * The amount we scale the array size between tests.
   */
  static final int SCALE = 2;

  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * A comparator for integers.
   */
  public static final Comparator<Integer> standardIntComparator =
      (left, right) -> left.compareTo(right);

  /**
   * Build arrays of random integer values.
   */
  public static final ArrayBuilder<Integer> randomIntArrBuilder = (length) ->
    {
      Integer[] vals = new Integer[length];
      Random random = new Random();
      for (int i = 0; i < length; i++)
        vals[i] = random.nextInt(length);
      return vals;
    }; // randomIArrayBuilder

  /** 
   * Build arrays of integer values in increasing order.
   */
  public static final ArrayBuilder<Integer> increasingIntArrBuilder =
      (length) ->
        {
          Integer[] vals = new Integer[length];
          for (int i = 0; i < length; i++)
            vals[i] = i;
          return vals;
        };
  /**
   * Build arrays of integer values in decreasing order.
   */
  public static final ArrayBuilder<Integer> decreasingIntArrBuilder =
      (length) ->
        {
          Integer[] vals = new Integer[length];
          for (int i = 0; i < length; i++)
            vals[i] = length - i;
          return vals;
        };
        
/**
 * Build arrays of integer values that are mostly sorted
 */
        public static final ArrayBuilder<Integer> semiSortedIntArrBuilder = 
            (length) ->
        {
          Random random = new Random();
          int switchNum = length/20; //maybe different number here?
          Integer[] vals = new Integer[length];
          for (int i = 0; i < length; i++)
            {
            vals[i] = i;
            }
          for (int i = 0; i < switchNum; i++)
            {
              Utils.swap(vals, random.nextInt(length), random.nextInt(length));
            }
          return vals;
        };
  // +--------------+----------------------------------------------------
  // | Class Fields |
  // +--------------+

  // +---------------+---------------------------------------------------
  // | Class Methods |
  // +---------------+
  /**
   * Determine the amount of time sorter takes to sort an array of
   * the given size created by builder.
   *
   * @param sorter
   *   The sorting routine we are testing.
   * @param builder
   *   The constructor for the array we will sort.
   * @param order
   *   The comparator we use in sorting.
   * @param size
   *   The size of the array that we sort.
   */
  public static <T> long basicAnalysis(Sorter<T> sorter, Comparator<T> order,
                                       ArrayBuilder<T> builder, int size)
  {
    // Prepare for timing
    SimpleTimer timer = new SimpleTimer();

    // Build the array.
    T[] values = builder.build(size);

    // Run the garbage collector so that garbage collection
    // is less likely to be counted as part of the time for
    // sorting.
    gc();

    // Start the timer.  (Duh.)
    timer.start();

    // Do the real work.
    try
      {
        sorter.sort(values, order);
      }
    catch (StackOverflowError soe)
      {
        // It didn't run.  Return a value to indicate failure
        return -1;   
      }

    // Stop the timer.
    timer.pause();

    // And report the time taken
    return timer.elapsed();
  } // basicAnalysis(Sorter<T>, ArrayBuilder<T>, int)

  /**
   * Repeatedly perform basic analysis and gather statistics
   * (e.g., minimum time, maximum time, average time.
   */
  public static <T> long[] compoundAnalysis(Sorter<T> sorter,
                                            Comparator<T> order,
                                            ArrayBuilder<T> builder, int size,
                                            int repetitions)
  {
    long max = basicAnalysis(sorter, order, builder, size);
    long min = max;
    long avg = 0;
    long temp;
    for (int i = 0; i < repetitions; i++)
      {
        temp = basicAnalysis(sorter, order, builder, size);
        avg += temp;
        if (temp < min)
          {
            min = temp;
          }
        if (temp > max)
          {
            max = temp;
          }
      }//for
    //calculate average
    avg = avg / repetitions;
    //puts into array returns
    return new long[] { avg, min, max };
  } // compoundAnalysis(Sorter<T>, ArrayBuilder<T>, int, int)

  /**
   * Given a variety of sorters and builders, does some analysis
   * of each sorter/builder pair using a variety of array sizes
   * and prints out the results.
   *
   * @param pen
   *   Where to send the output
   * @param sorters
   *   The objects that do the sorting
   * @param sorterNames
   *   The names of those sorters
   * @param builders
   *   The objects to build the arrays
   * @param builderNames
   *    The names of those builders
   */
  public static <T> void combinedAnalysis(PrintWriter pen, Sorter<T>[] sorters,
                                          String[] sorterNames,
                                          Comparator<T> order,
                                          ArrayBuilder<T> builders[],
                                          String[] builderNames)
  {
    pen.printf("%-16s%-16s%-16s%-16s%-16s%-16s\n", "Sorter", "Builder",
               "Input Size", "Average Time", "Minimum Time", "Maximum Time");
    pen.printf("%-16s%-16s%-16s%-16s%-16s%-16s\n", "------", "-------",
               "------------", "------------", "------------", "------------");
    for (int b = 0; b < builders.length; b++)
      {
        for (int s = 0; s < sorters.length; s++)
          {
            for (int size = SMALLEST; size <= LARGEST; size *= SCALE)
              {
                long[] stats =
                    compoundAnalysis(sorters[s], order, builders[b], size,
                                     REPETITIONS);
                pen.printf("%-16s%-16s%12d    %12d    %12d    %12d\n",
                           sorterNames[s], builderNames[b], size, stats[0],
                           stats[1], stats[2]);
              } // for size
          }//for sorters : sorters
      } // for builder : builders
  } // combinedAnalysis(PrintWRiter, Sorter<T>, String[], ...)

  /**
   * Force garbage collection to the best of our ability.
   */
  public static void gc()
  {
    // Right now, we use the quick and dirty "suggest garbage
    // collection".  Over the long term, we will probably try
    // something like "get the pid and execute 'jcmd <pid> GC.run'"
    // The pid *might* be in the environment.
    System.gc();
  } // gc()
} // class SorterAnalyzer