package taojava.labs.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sort using iterative merge sort.
 * 
 * @author Samuel A. Rebelsky
 * @author Your Name Here.
 */
public class IterativeMergeSorter<T>
    extends SorterBridge<T>
{
  /**
   * Sort vals using iterative merge sort. See the Sorter<T> interface for
   * additional details.
   */
  @Override
  public T[] sorti(T[] vals, Comparator<T> order)
  {
    // STUB
    int size = 1;
    T[] scratch = vals;
    while (size < vals.length)
      {
        for(int i = 0; i < vals.length; i += 2*size){
          //System.out.println("i = " + i + " +size = " + (i+size) + " +2size " + (i+2*size));
          int midIndex = Math.min(i + size, vals.length);
          int maxIndex = Math.min(i + 2*size, vals.length);
          vals = Utils.mergeWithinArray(order, vals, i, midIndex, midIndex, maxIndex, scratch, i, maxIndex);
        }
        // The merged subarrays are now twice as large
        size *= 2;
      } // while
    return vals;
  } // sorti(T[], Comparator<T>)
} // IterativeMergeSorter<T>
