
# 📊 Trading Order Analyzer CLI

A command-line Java application that calculates the **maximum number of simultaneously active trading orders** using a sweep line algorithm. Built with a clean architecture using Java 21, Maven, and [picocli](https://picocli.info/).

---

## 📦 Project Structure

```
order-analyzer/
├── application/          # Business logic
├── cli/                  # CLI interface (Picocli command)
├── domain/               # Domain models (Order, Event, EventType)
├── infrastructure/       # Input handling (file/args)
├── Main.java             # Entry point
├── pom.xml               # Maven build file
└── README.md             # You are here
```

---

## 🚀 How to Build

Make sure you have **Java 24** and **Maven** installed.

```bash
mvn clean package
```

This will generate a JAR with dependencies in the `target/` folder.

---

## 🏃 How to Run

### 🔹 Option 1: Run using CSV file

```
java -jar target/analyzer.jar --file path/to/orders.csv
```

### 🔹 Option 2: Run using inline entries

```
java -jar target/analyzer.jar \
  --entry t1,10,20 \
  --entry t1,18,22 \
  --entry t2,15,25 \
  --entry t4,30,40
```

---

## ⚙️ CLI Options

| Option           | Description                                      |
|------------------|--------------------------------------------------|
| `-f`, `--file`   | Path to a CSV file with orders (e.g. `t1,10,20`) |
| `-e`, `--entry`  | Add one or more inline orders via the CLI        |
| `--help`         | Show usage help                                  |
| `--version`      | Show version info                                |

---

## ✅ Example Input

```
t1,10,20
t1,18,22
t2,15,25
t4,30,40
```

Output:
```
✅ Max simultaneously active orders: 3 🧵
```

---

## 🧪 Run Tests

```bash
mvn test
```

Includes:
- Unit tests for parsing and computation logic
- CLI command tests using picocli’s API

---

## 🧊 Native Image (Optional)

You can build a **native binary** with GraalVM:

```bash
native-image -cp target/order-analyzer-1.0-jar-with-dependencies.jar Main
./main --entry t1,10,20 --entry t2,15,25
```

---

## 📜 License

MIT (or your preferred license)
