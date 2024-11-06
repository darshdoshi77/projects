import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class LinkedListDeque61B<T> implements Deque61B<T> {

    int size = 0;

    public class Node {
        private Node next;
        private T item;
        private Node prev;


        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }


    @Override
    public void addFirst(T x) {
        Node n = new Node(x, null, null);
        n.next = sentinel.next;
        n.prev = sentinel;
        sentinel.next = n;
        n.next.prev = n;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node f = new Node(x, null, null);
        f.prev = sentinel.prev;
        f.next = sentinel;
        sentinel.prev.next = f;
        sentinel.prev = f;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {

            returnList.add(current.item);
            current = current.next;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel && sentinel.prev == sentinel) {
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

        if (isEmpty()) {
            return null;
        }
        Node first = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return first.item;

    }

    @Override
    public T removeLast() {

        if (isEmpty()) {
            return null;
        }

        Node last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return last.item;

    }

    @Override
    public T get(int index) {

        if (index < 0 || index >= size()) {
            return null;
        }
        Node current = sentinel.next;

        for (int i = 0; i < index; i++) {

            current = current.next;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {

        if (index < 0 || index >= size()) {
            return null;
        }
        return helper(sentinel.next, index);
    }

    private T helper(Node current, int index) {

        if (current == null) {
            return null;
        }

        if (index == 0) {
            return current.item;
        } else {
            return helper(current.next, index - 1);
        }
    }


    Node sentinel;

    public LinkedListDeque61B() {

        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

    }


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



