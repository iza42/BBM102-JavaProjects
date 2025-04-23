# Spacecraft System

The Spacecraft System is a Java-based simulation project that models the movement and interaction of a spacecraft within a space grid. It includes functionalities like mission handling, spacecraft management, and collision detection.

## 🚀 How It Works

- **Main.java**: Entry point of the program. It initializes the SpacecraftSystem and Mission and runs the simulation.
- **Mission.java**: Contains the mission logic, including spacecraft behavior, movement, and interaction with other space objects.
- **SpacecraftSystem.java**: The core system that manages spacecraft interactions, including movement, collision detection, and grid updates.

The system allows for spacecraft movement and detection of any collisions within a grid environment, processing updates and checking for potential hazards.

## 🗂 Project Structure

SpacecraftSystem/
├── Main.java # Entry point to start the simulation  
├── Mission.java # Defines the mission and spacecraft behavior  
├── SpacecraftSystem.java # Core system for managing spacecraft and grid  
├── test/ # Folder containing input files  
├── Outputs/ # Folder containing the expected output
└── README.md # Project description and usage guide

## ▶️ How to Run

1. **Compile the Java files**:
   javac Main.java Mission.java SpacecraftSystem.java
2. **Run the file with the command line arguments**:
   java Main input.txt Test_Spacecraft 100
