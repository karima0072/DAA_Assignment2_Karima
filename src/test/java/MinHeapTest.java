import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void testInsertAndPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(3, heap.size());
        assertEquals(10, heap.peekMin());
    }

    @Test
    void testExtractMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.peekMin());
        assertEquals(3, heap.size());
    }

    @Test
    void testOneElement() {
        MinHeap heap = new MinHeap();

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void testDuplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void testGrow() {
        MinHeap heap = new MinHeap();

        for (int i = 50; i >= 0; i--) {
            heap.insert(i);
        }

        assertEquals(51, heap.size());
        assertEquals(0, heap.peekMin());
    }

    @Test
    void testSortedOutput() {
        MinHeap heap = new MinHeap();

        int[] values = {40, 10, 30, 5, 20, 15, 50};

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }

    @Test
    void testEmptyPeekMin() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
    }

    @Test
    void testEmptyExtractMin() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }

    @Test
    void testRandomDataSortedOutput() {
        MinHeap heap = new MinHeap();

        java.util.Random random = new java.util.Random(42);

        for (int i = 0; i < 1000; i++) {
            heap.insert(random.nextInt());
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }
    @Test
    void testHeapPropertyAfterOperations() {
        MinHeap heap = new MinHeap();

        int[] values = {40, 10, 30, 5, 20, 15, 50, 2, 35};

        for (int value : values) {
            heap.insert(value);
            assertTrue(heap.isValidHeap());
        }

        while (heap.size() > 0) {
            heap.extractMin();
            assertTrue(heap.isValidHeap());
        }
    }
}