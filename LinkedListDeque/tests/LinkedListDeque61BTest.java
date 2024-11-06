import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import deque.*;

import java.util.List;

/**
 * Performs some basic linked list tests.
 */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
     *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    /** This test performs interspersed addFirst and addLast calls. */
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test

    public void toListTestBasic() {

        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        List<Integer> list = lld1.toList();
        assertThat(list).isEmpty();
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(-1);
        lld1.addFirst(2);
        assertThat(lld1.toList()).containsExactly(2, -1, 1, 0).inOrder();


    }

    @Test
    public void isEmptyTestBasic() {

        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addFirst(0);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();

    }

    @Test
    public void sizeTestBasic() {

        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.size()).isEqualTo(0);
        lld1.addFirst(0);
        assertThat(lld1.size()).isEqualTo(1);
        lld1.addFirst(3);
        lld1.addFirst(4);
        assertThat(lld1.size()).isEqualTo(3);
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);


    }

    @Test

    public void getTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        lld1.addLast(5);
        assertThat(lld1.get(0)).isEqualTo(0);
        assertThat(lld1.get(4)).isEqualTo(4);
        assertThat(lld1.get(-1)).isEqualTo(null);
        assertThat(lld1.get(12435)).isEqualTo(null);
        assertThat(lld1.get(6)).isEqualTo(null);
    }

    @Test

    public void getRecursiveTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.addLast(4);
        lld1.addLast(5);
        assertThat(lld1.getRecursive(0)).isEqualTo(0);
        assertThat(lld1.getRecursive(4)).isEqualTo(4);
        assertThat(lld1.getRecursive(-1)).isEqualTo(null);
        assertThat(lld1.getRecursive(12435)).isEqualTo(null);
        assertThat(lld1.getRecursive(6)).isEqualTo(null);
    }

    @Test
    public void removeFirstTestBasic() {

        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(0);
        lld1.addLast(4);
        lld1.addLast(7);
        lld1.addLast(4);
        lld1.addLast(23);
        lld1.addLast(5);
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly(4, 23, 5).inOrder();
        lld1.removeFirst();
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.removeFirst()).isNull();

    }

    @Test
    public void removeLastTestBasic() {

        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(0);
        lld1.addLast(4);
        lld1.addLast(7);
        lld1.addLast(4);
        lld1.addLast(23);
        lld1.addLast(5);
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly(0, 4, 7).inOrder();
        lld1.removeLast();
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).isEmpty();
        assertThat(lld1.removeLast()).isNull();

    }

    @Test
    public void addLastAfterRemoveTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(1);
        lld1.removeLast();
        assertThat(lld1.toList()).isEmpty();

        lld1.addLast(2);
        assertThat(lld1.toList()).containsExactly(2).inOrder();
    }

}