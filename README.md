# CPU Process Scheduler Simulation ⚙️

A Java-based simulation of an Operating System's process scheduling mechanism. This project models how a CPU manages process states, handles context switching, and executes tasks using a Round Robin scheduling algorithm.

## 🧠 Technical Overview

This simulation demonstrates key Operating System concepts by creating a virtual processor that manages multiple simulated processes. It visualizes the lifecycle of a process from "Ready" to "Running" to "Blocked" or "Finished."

### Key Concepts Implemented:
* **Process Control Block (PCB):** A `ProcessControlBlock` class tracks the specific state of each process, including its Process ID (PID), current instruction pointer, and virtual register values (R1-R4).
* **Context Switching:** The system simulates the expensive operation of saving a process's state to memory (`saveProcess`) and loading a new process's state into the CPU registers (`restoreProcess`).
* **Round Robin Scheduling:** Implements a time-slicing algorithm with a fixed **Quantum** (5 ticks). If a process doesn't finish within its time slice, it is preempted and moved to the back of the ready queue.
* **State Management:** Handles random I/O blocking events. Processes have a 15% chance of entering a `BLOCKED` state during execution and must wait in a separate queue until randomly unblocked.

## 🛠️ Technologies Used

* **Language:** Java (Core)
* **Data Structures:**
    * `LinkedList` (Queue) for the Ready Queue.
    * `ArrayList` for the Blocked List.
* **Concepts:** Object-Oriented Programming, OS Scheduling Algorithms, State Machines.

## 📂 Project Structure

* **`Main.java`**: The scheduler loop. It initializes processes, manages the Ready/Blocked queues, handles time quantums, and triggers context switches.
* **`SimProcessor.java`**: Represents the CPU hardware. It holds the current active process and contains 4 integer registers.
* **`SimProcess.java`**: Represents a program. It contains the process logic and determines if the process finishes or requests I/O (blocks).
* **`ProcessControlBlock.java`**: The data structure used to save the context (registers + instruction count) of a process when it is not running.
* **`ProcessState.java`**: Enum defining valid states: `READY`, `BLOCKED`, `FINISHED`.

## 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/your-username/CPU_Process_Scheduler.git](https://github.com/your-username/CPU_Process_Scheduler.git)
    ```
2.  **Navigate to the source folder:**
    ```bash
    cd CPU_Process_Scheduler/src
    ```
3.  **Compile the Java files:**
    ```bash
    javac *.java
    ```
4.  **Run the simulation:**
    ```bash
    java Main
    ```

## 💻 Sample Output

The simulation runs for 3000 steps, printing the state of the CPU and processes in real-time:

```text
Step: 1
Context switch: Restoring process 1 Instruction: 0 R1: 0 R2: 0 R3: 0 R4: 0
PID: 1 Process: read file Instruction Number: 0
Step: 2
PID: 1 Process: read file Instruction Number: 1
...
Step: 6
*** Quantum expired ***
Context switch: Saving process 1 Instruction: 5 R1: 45 R2: 12 R3: 88 R4: 3
Context switch: Restoring process 2 Instruction: 0 R1: 0 R2: 0 R3: 0 R4: 0
PID: 2 Process: write file Instruction Number: 0
...
Process blocked!
Context switch: Saving process 2 Instruction: 3 R1: 10 R2: 55 R3: 2 R4: 90
