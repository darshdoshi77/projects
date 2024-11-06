import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        this.comparator = c;
    }

    public T max() {
        return max(this.comparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        // Start with the first valid element
        T maxItem = get(0);

        // Iterate through the deque to find the max element
        for (int i = 1; i < size(); i++) {
            T currentItem = get(i);
            if (c.compare(currentItem, maxItem) > 0) {
                maxItem = currentItem;
            }
        }

        return maxItem;
    }
}
