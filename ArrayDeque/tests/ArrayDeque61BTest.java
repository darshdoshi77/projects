import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();
        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    // Basic addFirst test with rigorous check after each operation
    @Test
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addFirst("back"); // ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();
        lld1.addFirst("middle"); // ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
        lld1.addFirst("front"); // ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    // Basic addLast test with rigorous check after each operation
    @Test
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addLast("front"); // ["front"]
        assertThat(lld1.toList()).containsExactly("front").inOrder();
        lld1.addLast("middle"); // ["front", "middle"]
        assertThat(lld1.toList()).containsExactly("front", "middle").inOrder();
        lld1.addLast("back"); // ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    // Combined addFirst and addLast test with verification after each operation
    @Test
    public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addLast(0);   // [0]
        assertThat(lld1.toList()).containsExactly(0).inOrder();
        lld1.addLast(1);   // [0, 1]
        assertThat(lld1.toList()).containsExactly(0, 1).inOrder();
        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.toList()).containsExactly(-1, 0, 1).inOrder();
        lld1.addLast(2);   // [-1, 0, 1, 2]
        assertThat(lld1.toList()).containsExactly(-1, 0, 1, 2).inOrder();
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Rigorous test for toList method for both empty and non-empty cases
    @Test
    public void toListTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.toList()).isEmpty();  // Check for empty deque
        lld1.addFirst(0);
        lld1.addFirst(1);
        lld1.addFirst(-1);
        lld1.addFirst(2);
        assertThat(lld1.toList()).containsExactly(2, -1, 1, 0).inOrder();  // Check for non-empty deque
    }

    // Rigorous test for isEmpty, testing the transitions between empty and non-empty states
    @Test
    public void isEmptyTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();  // Empty at the start
        lld1.addFirst(0);
        assertThat(lld1.isEmpty()).isFalse();  // Not empty after adding element
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();  // Empty after removing the only element
    }

    // Rigorous test for size to check size updates after multiple operations
    @Test
    public void sizeTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        assertThat(lld1.size()).isEqualTo(0); // Size should be 0
        lld1.addFirst(0);
        assertThat(lld1.size()).isEqualTo(1); // Size should be 1
        lld1.addFirst(3);
        assertThat(lld1.size()).isEqualTo(2); // Size should be 2
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(1); // Size should be 1 after removal
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0); // Back to 0 after removing all elements
    }

    // Rigorous test for get to ensure it handles out-of-bounds cases
    @Test
    public void getTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(0);
        lld1.addLast(1);
        assertThat(lld1.get(0)).isEqualTo(0);  // Test valid get
        assertThat(lld1.get(1)).isEqualTo(1);  // Test valid get
        assertThat(lld1.get(-1)).isEqualTo(null);  // Test out-of-bounds negative index
        assertThat(lld1.get(2)).isEqualTo(null);  // Test out-of-bounds positive index
    }

    // Rigorous test for removeFirst, with transitions to empty deque
    @Test
    public void removeFirstTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(0);
        lld1.addLast(1);
        lld1.removeFirst(); // Remove first element
        assertThat(lld1.toList()).containsExactly(1).inOrder();
        lld1.removeFirst(); // Remove remaining element
        assertThat(lld1.isEmpty()).isTrue();  // Check if empty after all removals
    }

    // Rigorous test for removeLast, with transitions to empty deque
    @Test
    public void removeLastTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        lld1.addFirst(0);
        lld1.addLast(1);
        lld1.removeLast();  // Remove last element
        assertThat(lld1.toList()).containsExactly(0).inOrder();
        lld1.removeLast();  // Remove remaining element
        assertThat(lld1.isEmpty()).isTrue();  // Check if empty after all removals
    }

    // Resize up test - Verify behavior after resizing and data integrity
    @Test
    public void resizeUpTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            deque.addLast(i); // Fill up initial capacity
        }
        deque.addLast(8); // Trigger resize
        // Verifying that the behavior remains correct after resizing
        assertThat(deque.size()).isEqualTo(9);
        assertThat(deque.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8).inOrder();  // Ensure all elements are intact
    }

    // Resize down test - Verify behavior after resizing down and data integrity
    @Test
    public void resizeDownTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            deque.addLast(i); // Fill up the array and trigger resize up
        }
        for (int i = 0; i < 16; i++) {
            deque.removeLast(); // Remove elements and trigger resize down
        }
        // Verifying that the behavior remains correct after resizing down
        assertThat(deque.size()).isEqualTo(4);
        assertThat(deque.toList()).containsExactly(0, 1, 2, 3).inOrder();
    }

    // Test adding after removing to empty
    @Test
    public void addFirstAfterRemoveToEmptyTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();  // Ensure it's empty
        deque.addFirst(2);
        assertThat(deque.toList()).containsExactly(2).inOrder();  // Ensure adding after removing to empty works
    }

    // Test addLast after removing to empty
    @Test
    public void addLastAfterRemoveToEmptyTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();  // Ensure it's empty
        deque.addLast(2);
        assertThat(deque.toList()).containsExactly(2).inOrder();  // Ensure adding after removing to empty works
    }

    @Test
    public void removeFirstTriggerResizeTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            deque.addLast(i); // Fill up the array and trigger resize up
        }
        for (int i = 0; i < 17; i++) {
            deque.removeFirst(); // Removing should eventually trigger resize down
        }
        assertThat(deque.size()).isEqualTo(3); // Ensure correct size
        assertThat(deque.toList()).containsExactly(17, 18, 19).inOrder(); // Ensure elements are intact after resize down
    }

    @Test
    public void removeLastTriggerResizeTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            deque.addFirst(i); // Fill up the array and trigger resize up
        }
        for (int i = 0; i < 17; i++) {
            deque.removeLast(); // Removing should eventually trigger resize down
        }
        assertThat(deque.size()).isEqualTo(3); // Ensure correct size
        assertThat(deque.toList()).containsExactly(19, 18, 17).inOrder(); // Ensure elements are intact after resize down
    }

    @Test
    public void removeFirstToEmptyThenAddTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst(); // Should be empty now
        assertThat(deque.isEmpty()).isTrue(); // Ensure it's empty
        deque.addLast(4); // Add an element again
        assertThat(deque.toList()).containsExactly(4).inOrder(); // Ensure deque is functioning after emptying
    }

    @Test
    public void removeAndResizeTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 100; i++) {
            deque.addLast(i); // Fill up and trigger multiple resizes
        }
        for (int i = 0; i < 90; i++) {
            deque.removeFirst(); // Removing should eventually trigger resize down
        }
        assertThat(deque.size()).isEqualTo(10); // Ensure size is correct after resizing down
        assertThat(deque.toList()).containsExactly(90, 91, 92, 93, 94, 95, 96, 97, 98, 99).inOrder(); // Ensure elements remain correct
    }

    @Test
    public void alternatingRemoveFirstAndLastTest() {
        Deque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.removeFirst(); // Removes 1
        deque.removeLast();  // Removes 4
        deque.removeFirst(); // Removes 2
        deque.removeLast();  // Removes 3
        assertThat(deque.isEmpty()).isTrue(); // Should be empty
        deque.addLast(5); // Add another element
        assertThat(deque.toList()).containsExactly(5).inOrder(); // Ensure deque is functioning
    }

}
