**  Reverse Path Forwarding (RPF) Visualizer in Java**

This project is a Java-based simulation of the **Reverse Path Forwarding (RPF)** multicast routing algorithm. It provides an interactive visual tool that allows users to build a custom network graph and simulate how multicast packets are forwarded efficiently while avoiding loops.

---

   🚀 Features

- Add nodes and edges dynamically.
- Assign custom costs (weights) to edges.
- Choose a source node and simulate multicast routing.
- Visual display of nodes, edges, and packet flow.
- Highlight dropped vs. accepted packets during RPF simulation.
- GUI built with **JavaFX** for clean visualization.

---



---

   🛠 Technologies Used

| Category          | Tool                      |
|------------------|---------------------------|
| Programming Lang | Java                      |
| GUI Framework    | JavaFX                    |
| IDE              | IntelliJ IDEA Community   |
| Recording        | OBS Studio / Loom         |
| Documentation    | Google Docs / MS Word     |

---

   📁 Project Structure
RoutingAlgorithmProject/
├── Source_Code/   Java source files
├── Project_Report.pdf   Full documentation (6–7 pages)
├── Demo_Video.mp4   Screen recording of simulation
├── GitHub_Link.txt   This repository URL
└── Executable/   Compiled .class files or .jar


---

   🧠 Algorithm Description

**Reverse Path Forwarding (RPF)** is a technique used in multicast routing where each node accepts a packet **only if it arrives via the shortest path from the source**. This helps prevent loops and unnecessary duplication of multicast data.

**Steps:**
1. Use Dijkstra’s algorithm to find the shortest path from the source to all nodes.
2. For each incoming packet at a node, check if it arrived via the shortest reverse path.
3. If yes → forward; if no → drop.

---

   📋 How to Run

1. Clone or download the repository.
2. Open the project in **IntelliJ IDEA**.
3. Run `Main.java` (or the main launcher file).
4. Use the GUI to add nodes, edges, and simulate RPF.

---


---

   📄 License

This project was developed as part of a **Computer Networks course assignment**. Not intended for commercial use.

---

   🔗 References

- Computer Networking: A Top-Down Approach (Kurose & Ross)
- https://en.wikipedia.org/wiki/Reverse-path_forwarding
- JavaFX Documentation: https://openjfx.io/




