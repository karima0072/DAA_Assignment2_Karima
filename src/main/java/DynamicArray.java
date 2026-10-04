public class DynamicArray {

    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray() {
        data = new int[10];
        size = 0;
        metrics = new Metrics();
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }
    public void add(int value) {
        if (size == data.length) {
            grow();
        }

        data[size] = value;
        size++;
    }

    private void grow() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
            metrics.steps++;
            metrics.moves++;
        }

        data = newData;
    }
    public int get(int index) {
        checkIndex(index);

        metrics.steps++;
        return data[index];
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (size == data.length) {
            grow();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            metrics.steps++;
            metrics.moves++;
        }

        data[index] = value;
        size++;
    }
    public int remove(int index) {
        checkIndex(index);

        int removedValue = data[index];
        metrics.steps++;

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            metrics.steps++;
            metrics.moves++;
        }

        size--;
        return removedValue;
    }
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            metrics.steps++;
            metrics.comparisons++;

            if (data[i] == value) {
                return true;
            }
        }

        return false;
    }
}
