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
		T[] aux = (T[]) new Object[vals.length];
		return mergesort(vals, order, aux, 0, vals.length - 1);
	} // sort(T[], Comparator<T>)
	
	public T[] mergesort(T[] vals, Comparator<T> order, T[] aux, int lb, int ub){
		if (lb < ub){
			int mid = (ub - lb)/2;
			mergesort(vals, order, aux, lb, mid);
			mergesort(vals, order, aux, mid + 1, ub);
			vals = Utils.merge(order, vals, lb, mid, vals, mid+1, ub, aux, lb, ub);
		}
		return vals;
	}
	
	
	
	
} // MergeSorter<T>

