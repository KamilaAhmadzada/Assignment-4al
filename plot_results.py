
import csv
from pathlib import Path
import matplotlib.pyplot as plt

results = Path("results")
plots = Path("plots")
plots.mkdir(exist_ok=True)

with open(results / "dp_results.csv", newline="") as f:
    dp = list(csv.DictReader(f))

sizes = [int(row["Size"]) for row in dp]
lcs = [float(row["LCS"]) for row in dp]
edit = [float(row["EditDistance"]) for row in dp]

plt.figure(figsize=(9, 5))
plt.plot(sizes, lcs, marker="o", label="LCS")
plt.plot(sizes, edit, marker="s", label="Edit Distance")
plt.xlabel("Input Length")
plt.ylabel("Average Runtime (ms)")
plt.title("Dynamic Programming Performance")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(plots / "dp_performance.png", dpi=200)
plt.close()

with open(results / "matching_results.csv", newline="") as f:
    matching = list(csv.DictReader(f))

matching = [row for row in matching if row["Case"] == "Present"]

sizes = [int(row["Size"]) for row in matching]
naive = [float(row["Naive"]) for row in matching]
kmp = [float(row["KMP"]) for row in matching]
rk = [float(row["RabinKarp"]) for row in matching]

plt.figure(figsize=(9, 5))
plt.plot(sizes, naive, marker="o", label="Naive")
plt.plot(sizes, kmp, marker="s", label="KMP")
plt.plot(sizes, rk, marker="^", label="Rabin-Karp")
plt.xlabel("Text Length")
plt.ylabel("Average Runtime (ms)")
plt.title("String Matching Performance (Pattern Present)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(plots / "matching_performance.png", dpi=200)
plt.close()

print("Plots created successfully.")
