import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100,
            1_000,
            10_000,
            100_000
    };
    private static final int RUNS = 5;
    private static final int SEED = 42;

    public static void main(String[] args) throws IOException {

        new File("results/plots").mkdirs();

        try (FileWriter writer = new FileWriter("results/results.csv")) {

            writer.write(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons\n"
            );

            for (int n : SIZES) {
                System.out.println("Running n = " + n);

                runW1(writer, n);
                runW2(writer, n);
                runW3(writer, n, true);
                runW3(writer, n, false);
                runW4(writer, n);
            }
        }

        System.out.println("Benchmark finished.");
        System.out.println("Results saved to results/results.csv");
    }
    private static void runW1(FileWriter writer, int n) throws IOException {
        int[] values = generateData(n);
        int[] indexes = new int[10_000];
        Random random = new Random(SEED);
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = random.nextInt(n);
        }

        benchmarkW1DynamicArray(writer, n, values, indexes);
        benchmarkW1LinkedList(writer, n, values, indexes);
    }

    private static void benchmarkW1DynamicArray(
            FileWriter writer,
            int n,
            int[] values,
            int[] indexes
    ) throws IOException {

        double[] times = new double[RUNS];
        DynamicArray warmup = createDynamicArray(values);
        warmup.getMetrics().reset();

        for (int index : indexes) {
            warmup.get(index);
        }

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            DynamicArray array = createDynamicArray(values);
            array.getMetrics().reset();

            long start = System.nanoTime();

            for (int index : indexes) {
                array.get(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = array.getMetrics().steps;
                moves = array.getMetrics().moves;
                comparisons = array.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W1", "-", "DynamicArray",
                n, median(times), steps, moves, comparisons
        );
    }

    private static void benchmarkW1LinkedList(
            FileWriter writer,
            int n,
            int[] values,
            int[] indexes
    ) throws IOException {

        double[] times = new double[RUNS];

        MyLinkedList warmup = createLinkedList(values);
        warmup.getMetrics().reset();

        for (int index : indexes) {
            warmup.get(index);
        }

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            MyLinkedList list = createLinkedList(values);
            list.getMetrics().reset();

            long start = System.nanoTime();

            for (int index : indexes) {
                list.get(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = list.getMetrics().steps;
                moves = list.getMetrics().moves;
                comparisons = list.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W1", "-", "MyLinkedList",
                n, median(times), steps, moves, comparisons
        );
    }
    private static void runW2(FileWriter writer, int n) throws IOException {

        int[] values = generateData(n);
        int[] queries = createSearchQueries(values);

        benchmarkW2DynamicArray(writer, n, values, queries);
        benchmarkW2LinkedList(writer, n, values, queries);
    }

    private static void benchmarkW2DynamicArray(
            FileWriter writer,
            int n,
            int[] values,
            int[] queries
    ) throws IOException {

        double[] times = new double[RUNS];

        DynamicArray warmup = createDynamicArray(values);
        warmup.getMetrics().reset();

        for (int query : queries) {
            warmup.contains(query);
        }

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            DynamicArray array = createDynamicArray(values);
            array.getMetrics().reset();

            long start = System.nanoTime();

            for (int query : queries) {
                array.contains(query);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = array.getMetrics().steps;
                moves = array.getMetrics().moves;
                comparisons = array.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W2", "-", "DynamicArray",
                n, median(times), steps, moves, comparisons
        );
    }

    private static void benchmarkW2LinkedList(
            FileWriter writer,
            int n,
            int[] values,
            int[] queries
    ) throws IOException {

        double[] times = new double[RUNS];

        MyLinkedList warmup = createLinkedList(values);
        warmup.getMetrics().reset();

        for (int query : queries) {
            warmup.contains(query);
        }

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            MyLinkedList list = createLinkedList(values);
            list.getMetrics().reset();

            long start = System.nanoTime();

            for (int query : queries) {
                list.contains(query);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = list.getMetrics().steps;
                moves = list.getMetrics().moves;
                comparisons = list.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W2", "-", "MyLinkedList",
                n, median(times), steps, moves, comparisons
        );
    }
    private static void runW3(
            FileWriter writer,
            int n,
            boolean head
    ) throws IOException {

        int[] values = generateData(n);
        String variant = head ? "head" : "middle";

        benchmarkW3DynamicArray(writer, n, values, variant, head);
        benchmarkW3LinkedList(writer, n, values, variant, head);
    }

    private static void benchmarkW3DynamicArray(
            FileWriter writer,
            int n,
            int[] values,
            String variant,
            boolean head
    ) throws IOException {

        double[] times = new double[RUNS];

        DynamicArray warmup = createDynamicArray(values);
        warmup.getMetrics().reset();
        performW3DynamicArray(warmup, head);

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            DynamicArray array = createDynamicArray(values);
            array.getMetrics().reset();

            long start = System.nanoTime();

            performW3DynamicArray(array, head);

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = array.getMetrics().steps;
                moves = array.getMetrics().moves;
                comparisons = array.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W3", variant, "DynamicArray",
                n, median(times), steps, moves, comparisons
        );
    }

    private static void performW3DynamicArray(
            DynamicArray array,
            boolean head
    ) {

        for (int i = 0; i < 1_000; i++) {
            int index = head ? 0 : array.size() / 2;
            array.add(index, i);
        }

        for (int i = 0; i < 1_000; i++) {
            int index = head ? 0 : array.size() / 2;
            array.remove(index);
        }
    }

    private static void benchmarkW3LinkedList(
            FileWriter writer,
            int n,
            int[] values,
            String variant,
            boolean head
    ) throws IOException {

        double[] times = new double[RUNS];

        MyLinkedList warmup = createLinkedList(values);
        warmup.getMetrics().reset();
        performW3LinkedList(warmup, head);

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            MyLinkedList list = createLinkedList(values);
            list.getMetrics().reset();

            long start = System.nanoTime();

            performW3LinkedList(list, head);

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = list.getMetrics().steps;
                moves = list.getMetrics().moves;
                comparisons = list.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W3", variant, "MyLinkedList",
                n, median(times), steps, moves, comparisons
        );
    }

    private static void performW3LinkedList(
            MyLinkedList list,
            boolean head
    ) {

        for (int i = 0; i < 1_000; i++) {
            int index = head ? 0 : list.size() / 2;
            list.add(index, i);
        }

        for (int i = 0; i < 1_000; i++) {
            int index = head ? 0 : list.size() / 2;
            list.remove(index);
        }
    }
    private static void runW4(FileWriter writer, int n) throws IOException {

        int[] values = generateData(n);
        double[] times = new double[RUNS];
        MinHeap warmup = new MinHeap();

        for (int value : values) {
            warmup.insert(value);
        }

        warmup.getMetrics().reset();
        extractAndCheck(warmup);

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {

            MinHeap heap = new MinHeap();

            for (int value : values) {
                heap.insert(value);
            }

            heap.getMetrics().reset();

            long start = System.nanoTime();

            extractAndCheck(heap);

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            if (run == 0) {
                steps = heap.getMetrics().steps;
                moves = heap.getMetrics().moves;
                comparisons = heap.getMetrics().comparisons;
            }
        }

        writeResult(
                writer, "W4", "-", "MinHeap",
                n, median(times), steps, moves, comparisons
        );
    }

    private static void extractAndCheck(MinHeap heap) {

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            if (current < previous) {
                throw new IllegalStateException(
                        "Heap output is not sorted"
                );
            }

            previous = current;
        }
    }

    private static int[] generateData(int n) {

        Random random = new Random(SEED);
        int[] values = new int[n];

        for (int i = 0; i < n; i++) {
            values[i] = random.nextInt();
        }

        return values;
    }

    private static int[] createSearchQueries(int[] values) {

        int[] queries = new int[1_000];

        for (int i = 0; i < 500; i++) {
            queries[i] = values[i % values.length];
        }

        for (int i = 500; i < 1_000; i++) {
            queries[i] = findMissingValue(values, i);
        }

        return queries;
    }

    private static int findMissingValue(int[] values, int seed) {

        int candidate = Integer.MAX_VALUE - seed;

        while (containsValue(values, candidate)) {
            candidate--;
        }

        return candidate;
    }

    private static boolean containsValue(int[] values, int value) {

        for (int current : values) {
            if (current == value) {
                return true;
            }
        }

        return false;
    }

    private static DynamicArray createDynamicArray(int[] values) {

        DynamicArray array = new DynamicArray();

        for (int value : values) {
            array.add(value);
        }

        return array;
    }

    private static MyLinkedList createLinkedList(int[] values) {

        MyLinkedList list = new MyLinkedList();

        for (int value : values) {
            list.add(value);
        }

        return list;
    }

    private static double median(double[] values) {

        double[] copy = values.clone();
        Arrays.sort(copy);

        return copy[copy.length / 2];
    }

    private static void writeResult(
            FileWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            double time,
            long steps,
            long moves,
            long comparisons
    ) throws IOException {

        writer.write(
                workload + "," +
                        variant + "," +
                        structure + "," +
                        n + "," +
                        String.format(java.util.Locale.US, "%.6f", time) + "," +
                        steps + "," +
                        moves + "," +
                        comparisons + "\n"
        );

        writer.flush();
    }
}