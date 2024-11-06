import org.junit.Test;

import static com.google.common.truth.Truth.assertThat; // Or use AssertJ, depending on your testing framework
import static org.junit.Assert.*;

public class ArrayEnhancementsTest {

    @Test
    public void testToString() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(0);

        // Test that toString returns the correct format
        assertThat(deque.toString()).isEqualTo("[0, 1, 2]");
    }

    @Test
    public void testEquals() {
        ArrayDeque61B<Integer> deque1 = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> deque2 = new ArrayDeque61B<>();

        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addFirst(0);

        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addFirst(0);

        // Test that two identical deques are equal
        assertThat(deque1).isEqualTo(deque2);

        // Test that two different deques are not equal
        deque2.addLast(3);
        assertThat(deque1).isNotEqualTo(deque2);
    }

    @Test
    public void testIterator() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addFirst(0);

        // Test that the iterator returns the elements in the correct order
        int[] expected = {0, 1, 2};
        int i = 0;
        for (int item : deque) {
            assertThat(item).isEqualTo(expected[i]);
            i++;
        }
    }

    // Add more tests as needed, following this structure
}
