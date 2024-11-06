import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;

public class MaxArrayDeque61BTest {

    // Custom comparator that compares strings by their length
    private static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    // Test for max using a custom string length comparator
    @Test
    public void testMaxWithStringLengthComparator() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<String>(new StringLengthComparator());
        mad.addFirst("");          // [""]
        mad.addFirst("2");         // ["2", ""]
        mad.addFirst("fury road"); // ["fury road", "2", ""]
        mad.addFirst("hi");        // ["hi", "fury road", "2", ""]
        assertThat(mad.max()).isEqualTo("fury road");
    }

    // Test for max with natural order (ascending) for integers
    @Test
    public void testMaxWithNaturalOrder() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        deque.addLast(5);  // [5]
        deque.addLast(3);  // [5, 3]
        deque.addLast(8);  // [5, 3, 8]
        deque.addLast(1);  // [5, 3, 8, 1]

        // Test max with the default comparator (natural order)
        assertThat(deque.max()).isEqualTo(8);
    }

    // Test max with a custom reverse-order comparator for integers
    @Test
    public void testMaxWithCustomComparator() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        deque.addLast(5);  // [5]
        deque.addLast(3);  // [5, 3]
        deque.addLast(8);  // [5, 3, 8]
        deque.addLast(1);  // [5, 3, 8, 1]

        // Test max with a custom comparator (reverse order)
        Comparator<Integer> reverseComparator = Comparator.reverseOrder();
        assertThat(deque.max(reverseComparator)).isEqualTo(1);
    }

    // Test max with string objects using natural order (alphabetical)
    @Test
    public void testMaxWithStringsNaturalOrder() {
        MaxArrayDeque61B<String> deque = new MaxArrayDeque61B<String>(Comparator.naturalOrder());

        deque.addLast("apple");  // ["apple"]
        deque.addLast("banana"); // ["apple", "banana"]
        deque.addLast("grape");  // ["apple", "banana", "grape"]
        deque.addLast("kiwi");   // ["apple", "banana", "grape", "kiwi"]

        // Test max with strings in natural order (alphabetically)
        assertThat(deque.max()).isEqualTo("kiwi");
    }

    // Test max with string objects using reverse alphabetical order
    @Test
    public void testMaxWithStringsCustomOrder() {
        MaxArrayDeque61B<String> deque = new MaxArrayDeque61B<String>(Comparator.naturalOrder());

        deque.addLast("apple");  // ["apple"]
        deque.addLast("banana"); // ["apple", "banana"]
        deque.addLast("grape");  // ["apple", "banana", "grape"]
        deque.addLast("kiwi");   // ["apple", "banana", "grape", "kiwi"]

        // Test max with a custom comparator (reverse alphabetical order)
        Comparator<String> reverseComparator = Comparator.reverseOrder();
        assertThat(deque.max(reverseComparator)).isEqualTo("apple");
    }

    // Test max on an empty deque (should return null)
    @Test
    public void testMaxWithEmptyDeque() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        // Test max with an empty deque
        assertThat(deque.max()).isNull();
    }

    // Test max on a deque with all elements equal
    @Test
    public void testMaxWithEqualElements() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        deque.addLast(5);  // [5]
        deque.addLast(5);  // [5, 5]
        deque.addLast(5);  // [5, 5, 5]
        deque.addLast(5);  // [5, 5, 5, 5]

        // Test max with equal elements (should return 5)
        assertThat(deque.max()).isEqualTo(5);
    }

    // Test max with custom comparator on deque with one element
    @Test
    public void testMaxWithOneElement() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        deque.addLast(42);  // [42]

        // Test max with only one element (should return the single element)
        assertThat(deque.max()).isEqualTo(42);
    }

    // Test adding elements at the front and checking the max with a custom comparator
    @Test
    public void testMaxWithAddFirst() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        deque.addFirst(42); // [42]
        deque.addFirst(30); // [30, 42]
        deque.addFirst(99); // [99, 30, 42]

        // Test max after adding elements at the front
        assertThat(deque.max()).isEqualTo(99);
    }

    // Test adding elements at both ends and ensuring max works
    @Test
    public void testMaxAfterAddFirstAndLast() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        deque.addFirst(30);  // [30]
        deque.addLast(50);   // [30, 50]
        deque.addFirst(100); // [100, 30, 50]
        deque.addLast(20);   // [100, 30, 50, 20]

        // Test max after adding elements at both ends
        assertThat(deque.max()).isEqualTo(100);
    }

    // Test: Fill up, empty, fill up again
    @Test
    public void testMaxAfterFillEmptyFill() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        // Fill the deque
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }
        assertThat(deque.max()).isEqualTo(9); // Max should be 9

        // Empty the deque
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }
        assertThat(deque.max()).isNull(); // Max should be null after emptying

        // Refill the deque
        for (int i = 10; i < 20; i++) {
            deque.addLast(i);
        }
        assertThat(deque.max()).isEqualTo(19); // Max should now be 19
    }

    // Test for Random add/remove/isEmpty/size tests
    @Test
    public void testMaxWithRandomOperations() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        deque.addLast(3);
        deque.addFirst(5);
        deque.addLast(2);
        deque.removeFirst(); // Removing the 5
        deque.addFirst(10);
        deque.removeLast(); // Removing the 2
        deque.addLast(15);

        assertThat(deque.max()).isEqualTo(15); // Max should be 15

        // Continue with random operations
        deque.removeFirst(); // Remove the 10
        assertThat(deque.max()).isEqualTo(15); // Max should still be 15

        deque.removeFirst(); // Remove the 15
        assertThat(deque.max()).isEqualTo(3); // Max should now be 3
    }

    // Test for Multiple MaxArrayDeque Instances
    @Test
    public void testMultipleMaxArrayDequeInstances() {
        MaxArrayDeque61B<Integer> deque1 = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        MaxArrayDeque61B<Integer> deque2 = new MaxArrayDeque61B<Integer>(Comparator.reverseOrder());

        // Add elements to the first deque
        deque1.addLast(1);
        deque1.addLast(2);
        deque1.addLast(3);
        assertThat(deque1.max()).isEqualTo(3); // Max should be 3

        // Add elements to the second deque
        deque2.addLast(1);
        deque2.addLast(2);
        deque2.addLast(3);
        assertThat(deque2.max()).isEqualTo(1); // Max should be 1 due to reverse order
    }

    // Test for Random Get Operations
    @Test
    public void testRandomGetOperations() {
        MaxArrayDeque61B<Integer> deque = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());

        deque.addFirst(5);
        deque.addLast(10);
        deque.addFirst(3);
        deque.addLast(20);

        // Get operations
        assertThat(deque.get(0)).isEqualTo(3);  // First element should be 3
        assertThat(deque.get(1)).isEqualTo(5);  // Second element should be 5
        assertThat(deque.get(3)).isEqualTo(20); // Fourth element should be 20

        deque.removeFirst(); // Remove the 3
        assertThat(deque.get(0)).isEqualTo(5);  // Now first element should be 5

        deque.removeLast();  // Remove the 20
        assertThat(deque.get(1)).isEqualTo(10); // Now second element should be 10
    }
}
