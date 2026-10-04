public class MyLinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    private final Metrics metrics;

    public MyLinkedList() {
        head = null;
        tail = null;
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
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
            metrics.moves += 2;
        } else {
            tail.next = newNode;
            tail = newNode;
            metrics.moves += 2;
        }

        size++;
    }

    public void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == size) {
            add(value);
            return;
        }

        Node newNode = new Node(value);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
            metrics.moves += 2;
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.steps++;
            }

            newNode.next = current.next;
            current.next = newNode;
            metrics.moves += 2;
        }

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removedValue;

        if (index == 0) {
            removedValue = head.value;
            head = head.next;
            metrics.moves++;

            if (size == 1) {
                tail = null;
                metrics.moves++;
            }
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.steps++;
            }

            Node removedNode = current.next;
            removedValue = removedNode.value;

            current.next = removedNode.next;
            metrics.moves++;

            if (removedNode == tail) {
                tail = current;
                metrics.moves++;
            }
        }

        size--;
        return removedValue;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.steps++;
        }

        return current.value;
    }

    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            metrics.comparisons++;

            if (current.value == value) {
                return true;
            }

            current = current.next;
            metrics.steps++;
        }

        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}