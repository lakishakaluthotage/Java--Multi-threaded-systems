# Java--Multi-threaded-systems
This repository contains the completed coursework for the Concurrent Programming module. The coursework focuses on designing and implementing multi-threaded Java programs to solve real-world concurrency problems.
The project consists of three main scenarios, each demonstrating different aspects of concurrency, synchronization, and resource management in Java.

Scenario 1: Coffee Shop Ordering System

Multiple customers place coffee orders simultaneously.
Multiple baristas prepare the coffee from a shared order queue.
Queue has limited capacity, requiring proper synchronization.
Ensures safe access to the shared resource without race conditions.

Scenario 2: Concurrent Banking System
Handles safe money transfers between multiple bank accounts.
Implements transaction safety and deadlock avoidance.
Supports fair access (first-come, first-served) to accounts.
Allows concurrent reads without blocking transactions.
Provides rollback mechanism in case of failed transfers.

Scenario 3: University Bathroom Simulation
Models limited bathroom stalls with multiple users (students & employees).
Tracks stall availability and manages waiting users.
Ensures stalls are not double-occupied.
Includes exception handling for invalid stall/user configurations.

🛠️ Technologies Used

Java (Multithreading, Concurrency utilities)

Synchronization mechanisms: Locks, Queues, Monitors

Exception Handling
