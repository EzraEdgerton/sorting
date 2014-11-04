package taojava.labs.sorting;

public class IntegerInsertionSorter
{
  public int[] sort(int[] vals) {
    for (int i = 1; i < vals.length; i++) {
            int item = vals[i];
            int k = i - 1;
            while((k >= 0) && (item < vals[k])){
                    vals[k + 1] = vals[k];
                    k--;
            }
            vals[k + 1] = item;
    } // for
    return vals;
} // sorti(T[], Comparator<T>)
}
