import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void testAddAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddAtIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddAtHead() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(0, 5);

        assertEquals(2, list.size());
        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void testRemove() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void testRemoveFirstAndLast() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(list.size() - 1));

        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void testContainsAndDuplicates() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(20);

        assertTrue(list.contains(10));
        assertTrue(list.contains(20));
        assertFalse(list.contains(99));
    }

    @Test
    void testOneElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(42);

        assertEquals(1, list.size());
        assertEquals(42, list.get(0));
        assertTrue(list.contains(42));
    }

    @Test
    void testInvalidIndexes() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(0));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.remove(0));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(-1, 10));

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(1));
    }
    @Test
    void testRandomDataAgainstLinkedList() {
        MyLinkedList list = new MyLinkedList();
        java.util.LinkedList<Integer> expected = new java.util.LinkedList<>();
        java.util.Random random = new java.util.Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt();

            list.add(value);
            expected.add(value);
        }

        assertEquals(expected.size(), list.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), list.get(i));
        }
    }
}