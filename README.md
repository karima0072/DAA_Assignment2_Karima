# DAA Assignment 2 – Data Structures

## Description

This project was created for Assignment 2 in the Design and Analysis of Algorithms course.

The project implements three data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

All structures store primitive `int` values. The implementations do not use `ArrayList`, `LinkedList`, or `PriorityQueue` internally.

The project also measures execution time and physical operations such as steps, moves, and comparisons.

## Project Structure

```text
src/
├── main/java/
│   ├── DynamicArray.java
│   ├── MyLinkedList.java
│   ├── MinHeap.java
│   ├── Metrics.java
│   └── Benchmark.java
│
└── test/java/
    ├── DynamicArrayTest.java
    ├── MyLinkedListTest.java
    └── MinHeapTest.java

results/
├── results.csv
└── plots/

REPORT.md
README.md
plot_results.py
pom.xml
```

## Data Structures

### DynamicArray

DynamicArray uses an `int[]` array to store elements.

It supports:

- `add(x)`
- `add(index, x)`
- `remove(index)`
- `get(index)`
- `contains(x)`

When the internal array becomes full, its capacity is doubled.

### MyLinkedList

MyLinkedList is implemented as a singly linked list.

Each node stores:

- an `int` value
- a reference to the next node

The list keeps both `head` and `tail` references.

It supports:

- `add(x)`
- `add(index, x)`
- `remove(index)`
- `get(index)`
- `contains(x)`

### MinHeap

MinHeap is implemented using an `int[]` array.

It supports:

- `insert(x)`
- `peekMin()`
- `extractMin()`

Insertion restores the heap property using bubble-up.

Extraction restores the heap property using bubble-down.

## Requirements

- Java 17
- Maven
- JUnit 5
- Python 3
- pandas
- matplotlib

## Running the Tests

The project uses JUnit 5.

Run the tests with Maven:

```bash
mvn test
```

The tests cover:

- normal operations
- empty structures
- one element
- duplicate values
- first and last indexes
- invalid indexes
- randomized correctness
- heap property
- sorted MinHeap output

## Running the Benchmark

Run the `Benchmark` class.

The benchmark tests the following input sizes:

```text
100
1,000
10,000
100,000
```

The same random seed is used for reproducibility:

The same random seed is used for reproducibility: `new Random(42)`.

Each benchmark case uses one warm-up run followed by five measured runs.

The median execution time of the five measured runs is saved.

Benchmark results are written to:

```text
results/results.csv
```

The CSV contains the following columns:

```text
workload,variant,structure,n,time_ms,steps,moves,comparisons
```

## Benchmark Workloads

### W1 – Random Access

DynamicArray and MyLinkedList are filled with `n` values.

Then 10,000 random `get(index)` operations are performed.

### W2 – Search

DynamicArray and MyLinkedList perform 1,000 `contains(x)` queries.

Half of the searched values are present in the structure and half are absent.

### W3 – Insert and Remove

DynamicArray and MyLinkedList are tested using two variants.

**Head:**

- 1,000 insertions at index 0
- 1,000 removals at index 0

**Middle:**

- 1,000 insertions near the middle
- 1,000 removals near the middle

### W4 – Priority Processing

The benchmark inserts `n` values into MinHeap.

Then all elements are removed using `extractMin()`.

The benchmark checks that the extracted values are returned in non-decreasing order.

## Metrics

The structures count physical operations during execution.

**Steps**

- one read of an array cell
- or one move to the next linked-list node

**Moves**

- one shifted array element
- or one link update in MyLinkedList

**Comparisons**

- one comparison between element values

The counters are updated inside the data structure operations.

## Generating Plots

The benchmark charts are generated from `results/results.csv`.

Run:

```bash
python plot_results.py
```

The script uses pandas and matplotlib.

Generated PNG files are saved in:

```text
results/plots/
```

The plots show execution time and operation counts for the benchmark workloads.

## Git Workflow

The project uses the following branches:

```text
main
feature/array
feature/list
feature/heap
feature/metrics
```

Development was divided into small commits for implementation, testing, benchmarking, and documentation.

The final working version is available on the `main` branch.

The final release is marked with the tag:

```text
v1.0
```

## Report

The complete analysis is available in:

```text
REPORT.md
```

The report contains:

- complexity analysis
- best, average, and worst cases
- auxiliary space analysis
- two loop invariant proofs
- benchmark plots
- performance discussion
- cache locality and pointer chasing analysis