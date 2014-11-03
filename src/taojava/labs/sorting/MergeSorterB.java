package taojava.labs.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sort using recursive merge sort.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here.
 */
public class MergeSorterB<T> extends SorterBridge<T> {
    /**
     * Sort vals using iterative merge sort. See the Sorter<T> interface for
     * additional details.
     */
    @Override
    public T[] sort(T[] vals, Comparator<T> order) {
        T[] scratch = vals;
        return mergesort(vals, order, scratch, 0, vals.length - 1);
    } // sort(T[], Comparator<T>)
    
    public T[] mergesort(T[] vals, Comparator<T> order, T[] scratch, int lb, int ub){
        if ((ub - lb) > 1){
            int mid = lb + (ub - lb)/2;
            mergesort(vals, order, scratch, lb, mid);
            mergesort(vals, order, scratch, mid + 1, ub);
            vals = Utils.mergeWithinArray(order, vals, lb, mid, mid+1, ub, scratch, lb, ub);
        }
        return vals;
    }
} // MergeSorter<T>