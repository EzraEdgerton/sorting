package taojava.labs.sorting;

import java.util.Comparator;

public class InsertionSorterC<T> extends SorterBridge<T> {
	/**
	 * Sort vals using insertion sort. See the Sorter<T> interface for
	 * additional details.
	 */
	@Override
	public T[] sorti(T[] vals, Comparator<T> order) {
		for (int i = 1; i < vals.length; i++) {
			T item = vals[i]; //store item
			int k = i - 1; //move elements up while item is smaller
			while((k >= 0) && (order.compare(item, vals[k]) < 0)){
				vals[k + 1] = vals[k];
				k--;
			}//while
			vals[k + 1] = item; //place item in proper place
		} // for
		return vals;
	} // sorti(T[], Comparator<T>)
} // InsertionSorter<T>

