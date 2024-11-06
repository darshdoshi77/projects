import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class LinkedListEnhancements {

    @Test
    public void iteratorTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addFirst(0);

        int[] expected = {0, 1, 2};
        int i = 0;
        for (int item : lld1) {
            assertThat(item).isEqualTo(expected[i]);
            i++;
        }
    }

    @Test
    public void toStringTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addFirst(0);

        // Ensure that toString gives the expected output
        assertThat(lld1.toString()).isEqualTo("[0, 1, 2]");
    }

    @Test
    public void equalsTest() {
        Deque61B<Integer> deque1 = new LinkedListDeque61B<>();
        Deque61B<Integer> deque2 = new LinkedListDeque61B<>();

        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addFirst(0);

        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addFirst(0);

        // Check if two identical deques are equal
        assertThat(deque1).isEqualTo(deque2);

        // Add a different element to one and check inequality
        deque2.addLast(3);
        assertThat(deque1).isNotEqualTo(deque2);
    }
}
