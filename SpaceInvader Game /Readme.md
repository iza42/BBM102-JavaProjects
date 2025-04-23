# Space Invader Game (JavaFX)

The **Space Invader Game** is a simplified version of the classic arcade game developed using the **JavaFX** framework. This game involves the player's spacecraft, which is controlled using the keyboard, while fighting against enemy spacecraft. The main objective is to avoid enemy collisions, destroy enemy ships, and collect rewards or penalties.

## 🚀 Game Overview

- **Game Mechanics**: The player controls a spacecraft that can move horizontally using arrow keys and shoot projectiles vertically with the "SPACEBAR". The enemy ships move vertically, and the player earns points by destroying them. A random drop system gives rewards or penalties, affecting the player's score or abilities.
- **Game Features**:
  - **Start Screen**: Press "ENTER" to start or "ESC" to exit.
  - **Gameplay**: Shoot enemy ships and collect rewards. The game ends when an enemy ship collides with the player's spacecraft.
  - **Game Over**: Displays the final score and options to restart ("R") or return to the main menu ("ESC").
  - **Pause**: Press "P" to pause the game at any time.

## 🕹 How the Game Works

1. **Player Movement**: The player can move the spacecraft left or right using the arrow keys.
2. **Shooting**: The player can shoot projectiles upwards using the "SPACEBAR".
3. **Enemy Ships**: Enemy ships spawn every 2 seconds and move vertically towards the bottom of the screen.
4. **Rewards and Penalties**: After an enemy is destroyed, there is a chance to drop either a reward or penalty:
   - **Penalty**: Reduces the player's score by 50 points.
   - **Reward**: Increases the player's score by 50 points or grants an enhanced shooting ability for 5 seconds.
5. **Game Over**: The game ends when an enemy ship collides with the player's spacecraft. The final screen shows the player's score and options to restart the game or exit.

## 🗂 Project Structure

```plaintext
SpaceInvader/
├── Enemy.java          # Class for enemy ship logic
├── FinalScreen.java    # Class for the final game over screen
├── GameScreen.java     # Main gameplay logic
├── Player.java         # Class for player spacecraft logic
├── Projectile.java     # Class for projectile behavior
├── Punishment.java     # Class for penalty effects
├── Reward.java         # Class for reward effects
├── RewardOrPunishment.java # Class for handling rewards and penalties
└── SpaceInvader.java   # Main entry point for the game
├── assets/
│   ├── main.png        # Title screen background image
│   └── (other assets for the game)
└── README.md           # Project description and usage guide

##▶️ How to Run
javac src/*.java
java SpaceInvader

🖥️ System Requirements
Java 8 (Oracle version)
Windows 10 or newer / macOS 12 or newer
No external libraries or scene builders are allowed. This project uses only pure Java code with JavaFX for the GUI.

## ⚙️ Controls
Arrow Keys: Move the player's spacecraft horizontally.
SPACEBAR: Shoot projectiles upward.
P: Pause the game.
ESC: Exit the game or return to the main menu.
ENTER: Start the game from the main menu.
R: Restart the game after "Game Over".




Please specify the location (e.g. src/ or src/mypackage)
where your implementation should be compiled at:
/////////// Type Below: LOCATION OF DIR ///////////
src/
///////////////////////////////////////////////////


Please specify the command that is needed
to COMPILE your implementation:
(e.g. javac Main.java)
Note that any external libraries are not allowed!
///////////// Type Below: COMPILATION //////////////
javac src/*.java
///////////////////////////////////////////////////


Please specify the command that is needed
to RUN your implementation:
(e.g. java Main)
Note that any external libraries are not allowed!
///////////////// Type Below: RUN /////////////////
java -cp src SpaceInvader
///////////////////////////////////////////////////

Please specify the location (e.g. src/ or src/mypackage)
where assets folder should be inserted:
////////// Type Below: LOCATION OF ASSETS //////////
assets/
///////////////////////////////////////////////////

Please specify extra information if you have any
////////// Type Below: EXTRA INFORMATION //////////
I did not place assets folder under the src folder. I replaced it outside
of my src folder in my local computer.
///////////////////////////////////////////////////
```
