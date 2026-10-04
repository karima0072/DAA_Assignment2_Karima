public class MinHeap {

    private int[] heap;
    private int size;
    private final Metrics metrics;

    public MinHeap() {
        heap = new int[10];
        size = 0;
        metrics = new Metrics();
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void insert(int value) {
        if (size == heap.length) {
            grow();
        }

        heap[size] = value;
        int index = size;
        size++;

        bubbleUp(index);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        metrics.steps++;
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap[0];
        metrics.steps++;

        heap[0] = heap[size - 1];
        metrics.steps++;
        metrics.moves++;

        size--;

        if (size > 0) {
            bubbleDown(0);
        }

        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.comparisons++;
            metrics.steps += 2;

            if (heap[parent] <= heap[index]) {
                break;
            }

            int temp = heap[parent];
            heap[parent] = heap[index];
            heap[index] = temp;

            metrics.steps += 2;
            metrics.moves += 2;

            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.steps += 2;
                metrics.comparisons++;

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.steps += 2;
                metrics.comparisons++;

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            int temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;

            metrics.steps += 2;
            metrics.moves += 2;

            index = smallest;
        }
    }

    private void grow() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];
            metrics.steps++;
            metrics.moves++;
        }

        heap = newHeap;
    }
    boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && heap[i] > heap[left]) {
                return false;
            }

            if (right < size && heap[i] > heap[right]) {
                return false;
            }
        }

        return true;
    }
}