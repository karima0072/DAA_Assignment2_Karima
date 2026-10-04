import pandas as pd
import matplotlib.pyplot as plt
import os

data = pd.read_csv("results/results.csv")

output_folder = "results/plots"

if not os.path.exists(output_folder):
    os.makedirs(output_folder)


def plot_time(workload, variant=None):
    temp = data[data["workload"] == workload]

    if variant is not None:
        temp = temp[temp["variant"] == variant]

    plt.figure(figsize=(8, 5))

    for structure in temp["structure"].unique():
        part = temp[temp["structure"] == structure]

        plt.plot(
            part["n"],
            part["time_ms"],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel("Time (ms)")

    if variant is None:
        plt.title(workload + " - Time vs n")
        filename = workload.lower() + "_time.png"
    else:
        plt.title(workload + " " + variant + " - Time vs n")
        filename = workload.lower() + "_" + variant + "_time.png"

    plt.legend()
    plt.grid()
    plt.tight_layout()
    plt.savefig(output_folder + "/" + filename)
    plt.close()


def plot_operations(workload, metric, variant=None):
    temp = data[data["workload"] == workload]

    if variant is not None:
        temp = temp[temp["variant"] == variant]

    plt.figure(figsize=(8, 5))

    for structure in temp["structure"].unique():
        part = temp[temp["structure"] == structure]

        plt.plot(
            part["n"],
            part[metric],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel(metric.capitalize())

    if variant is None:
        plt.title(workload + " - " + metric.capitalize() + " vs n")
        filename = workload.lower() + "_" + metric + ".png"
    else:
        plt.title(
            workload + " " + variant +
            " - " + metric.capitalize() + " vs n"
        )
        filename = (
                workload.lower() + "_" +
                variant + "_" +
                metric + ".png"
        )

    plt.legend()
    plt.grid()
    plt.tight_layout()
    plt.savefig(output_folder + "/" + filename)
    plt.close()


# W1 - Random Access
plot_time("W1")
plot_operations("W1", "steps")
plot_operations("W1", "moves")
plot_operations("W1", "comparisons")

# W2 - Search
plot_time("W2")
plot_operations("W2", "steps")
plot_operations("W2", "moves")
plot_operations("W2", "comparisons")

# W3 - Head
plot_time("W3", "head")
plot_operations("W3", "steps", "head")
plot_operations("W3", "moves", "head")
plot_operations("W3", "comparisons", "head")

# W3 - Middle
plot_time("W3", "middle")
plot_operations("W3", "steps", "middle")
plot_operations("W3", "moves", "middle")
plot_operations("W3", "comparisons", "middle")

# W4 - Priority Processing
plot_time("W4")
plot_operations("W4", "steps")
plot_operations("W4", "moves")
plot_operations("W4", "comparisons")

print("Plots created successfully.")