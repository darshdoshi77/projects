import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    public static final int THRESH = 16;
    private int size;
    private T[] backing;
    private int nextFirst;
    private int nextLast;
    private int z;
    private double usage;


    public ArrayDeque61B() {
        this.size = 0;
        this.backing = (T[]) new Object[8];
        this.nextFirst = 0;
        this.nextLast = 1;
        this.z = 0;
        this.usage = (double) size / backing.length;

    }

    @Override
    public void addFirst(T x) {
        if (size == backing.length) {
            resizeUp();
        }
        backing[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, backing.length);
        size++;

    }

    @Override
    public void addLast(T x) {
        if (size == backing.length) {
            resizeUp();
        }
        backing[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, backing.length);
        size++;

    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < size(); i++) {
            list.add(get(i));
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        usage = (double) size / backing.length;

        if (backing.length >= THRESH && usage < 0.25) {
            resizeDown();
        }
        if (isEmpty()) {
            return null;
        }
        int x = Math.floorMod(nextFirst + 1, backing.length);
        T removedItem = backing[x];
        nextFirst = Math.floorMod(nextFirst + 1, backing.length);

        backing[nextFirst] = null;
        size--;


        return removedItem;

    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public T removeLast() {
        usage = (double) size() / backing.length;
        if (backing.length >= THRESH && usage < 0.25) {
            resizeDown();
        }

        if (isEmpty()) {
            return null;
        }
        int y = Math.floorMod(nextLast - 1, backing.length);
        T removedItem = backing[y];
        nextLast = Math.floorMod(nextLast - 1, backing.length);
        backing[nextLast] = null;
        size--;


        return removedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int i = nextFirst + index + 1;
        if (i >= backing.length) {
            i = Math.floorMod(i, backing.length);
        }
        T x = backing[i];
        return x;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }


    private void resizeUp() {
        T[] newBacking = (T[]) new Object[2 * backing.length];

        for (int i = 0; i < size; i++) {
            newBacking[i] = get(i);
        }

        backing = newBacking;
        nextFirst = backing.length - 1;
        nextLast = size;
    }

    private void resizeDown() {

        T[] newBacking = (T[]) new Object[backing.length / 2];


        for (int i = 0; i < size; i++) {
            newBacking[i] = get(i);
        }
        backing = newBacking;
        nextFirst = newBacking.length - 1;
        nextLast = size;

    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public T next() {
                T item = get(i);
                i++;
                return item;
            }
        };
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }


        if (!(o instanceof Deque61B<?>)) {
            return false;
        }


        Deque61B<?> otherDeque = (Deque61B<?>) o;

        if (this.size() != otherDeque.size()) {
            return false;
        }


        for (int i = 0; i < this.size(); i++) {

            if (!this.get(i).equals(otherDeque.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return toList().toString();
    }


}


