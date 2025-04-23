# MazeSolver

MazeSolver is a simple Java console application that navigates through a maze using a deterministic left-hand rule strategy. The goal is to reach the 'X' tile from a given starting point.

## 🧩 How It Works

- Maze is read from a `.txt` file.
- Starting coordinates (x, y) and file path are passed as command-line arguments.
- The algorithm tries to move east first, if not possible, it turns left (north → west → south → east) until it finds a valid direction.
- If all directions are blocked, it backtracks.
- It stops either when it reaches `'X'` or after 1000 steps.

## 🗂 Project Structure

MazeSolver/
├── MazeSolver.java #Main Java file containing maze-solving logic
├── test/ # Folder containing example maze files
│ ├── test1.txt
│ └── test2.txt
└── README.md # Project description and usage guide

## ▶️ How to Run

javac MazeSolver.java # Compile the java file
#Run the program with arguments:
java MazeSolver <start_x> <start_y> test/test1.txt
#<start_x>: Starting row index (e.g., 1)

<start_y>: Starting column index (e.g., 1)

test/test1.txt: Path to the maze file (example provided in the test/ folder)

# 📝 Example:

java MazeSolver 1 1 test/test1.txt
