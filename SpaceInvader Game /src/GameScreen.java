import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents the game screen in the Space game.
 * This class applies the game logic.Including player movement, enemy spawning, firing projectiles collision detection and score updates.
 * It also manages the pause and resume functionality and displays rewards and punishments on the screen.
 */
public class GameScreen  {
    private Timeline collisionCheck;  // Timeline used to check collisions periodically
    private List<Text> messages = new ArrayList<>();// List to hold temporary messages displayed on the screen for managing the simultaneous messages.

    private Stage stage;
    private boolean isThereReward; // Used for if there is no any reward or punishment in the screen no need to call the moveRewards func.Used for that check.
    private Player player;
    private Pane root; // The root pane containing all game elements
    private double screenWidth = Screen.getPrimary().getBounds().getWidth();
    private double screenHeight = Screen.getPrimary().getBounds().getHeight();

    private List<Timeline> enemiesTimelines = new ArrayList<>(); // List of timelines for enemy movement for pausing the enemies from any function

    private boolean isPaused = false; // For making the game resume, when we pressed the P second time
    Timeline enemySpawner; // Timeline for spawning enemies periodically, declared here because need to reach from other functions

    private List<RewardOrPunishment> rewardOrPunishments = new ArrayList<>();// List of rewards and punishments for keeping track of the reward and punishments

    private List<Projectile> projectiles = new ArrayList<>(); // List of projectiles fired by the player
    private int score = 0;
    private Text scoreText = new Text("SCORE: 0");

    private List<Enemy> enemies = new ArrayList<>(); // List of enemies in the game


    /**
     * Initializes the game screen.
     * Sets up the player ,enemies,score,projectiles and game controls.
     * @param stage The stage where the game will be displayed
     */
    public GameScreen(Stage stage){
        root = new Pane();
        scoreText.setFill(Color.NAVY);
        scoreText.setFont(new Font(60));
        this.stage=stage;

        root.setStyle("-fx-background-color: lightblue;");

        // I will create one player object for game screen from the player class I wrote
        player = new Player();
        player.setLayoutX(screenWidth / 2);
        player.setLayoutY(screenHeight - 200);
        root.getChildren().add(player);

       // Adding score to the root
        scoreText.setFont(new Font(20));
        scoreText.setLayoutX(10);
        scoreText.setLayoutY(30);
        root.getChildren().add(scoreText);

        // creating the enemy object and making its movements inside  the loop
         enemySpawner = new Timeline(new KeyFrame(Duration.seconds(2), e -> createEnemy())); // calls createEnemy() method in every 2 seconds
        enemySpawner.setCycleCount(Timeline.INDEFINITE); // loop is indefinite, enemies created throughout the game till the game ends or user press P
        enemySpawner.play();

        // Projectile movement
        Timeline projectileMovement = new Timeline(new KeyFrame(Duration.seconds(0.05), e -> moveProjectiles()));
        projectileMovement.setCycleCount(Timeline.INDEFINITE);
        projectileMovement.play();

        collisionCheck= new Timeline(new KeyFrame(Duration.seconds(0.05), e->check_game_ends()));
        collisionCheck.setCycleCount(Timeline.INDEFINITE);
        collisionCheck.play();

        // Create a Scene and set the key event handling
        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) { // if user press ESC, game goes to the start screen
                new SpaceInvader().start(stage);
            } else if (event.getCode() == KeyCode.LEFT) {
                if(isPaused) return;
                player.moveLeft();
            } else if (event.getCode() == KeyCode.RIGHT) {
                if(isPaused) return;
                player.moveRight();
            }else if (event.getCode() == KeyCode.P) { //stop the game when press P, then resume the game when press P secondly
                if (!isPaused) {
                    pauseGame();
                    isPaused = true;
                }else{
                    resumeGame();
                    isPaused=false;
                }
            }else if (event.getCode() == KeyCode.SPACE) { // fire projectile when press spacebar
                fireProjectile();
            }

        });
        stage.setTitle("SPACE INVADER GAME");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Creates a new enemy and adds it to the game screen at a random position.
     * The enemy will move down periodically until it leaves the screen or collides with the player.
     */
    private void createEnemy() {
        if (isPaused) return; // If the game is paused, do not create a new enemy
        Enemy enemy = new Enemy();
        double cm5= 189; // Approximately 189 pixels, enemies are positioned with 5 cm intervals.
        double enemyX = Math.random() * (screenWidth - enemy.getFitWidth());  // Ensures the enemy stays within screen boundaries
        enemyX = Math.round(enemyX / cm5) * cm5;// positions the enemy with 5 cm intervals
        enemy.setLayoutX(enemyX);
        enemy.setLayoutY(0); // Positions the enemy at the top of the screen, they fall from there
        root.getChildren().add(enemy); // Adds the enemy to the game scene

        Timeline enemyMovement = new Timeline(new KeyFrame(Duration.seconds(0.03), e -> enemy.moveDown())); // Calls moveDown() every 30ms that moves the enemy downwards
        enemyMovement.setCycleCount(Timeline.INDEFINITE);
        enemyMovement.play();

        enemiesTimelines.add(enemyMovement); // Adds the enemy's movement to a list for tracking them.
        enemies.add(enemy);
    }
    /**
     * Pauses the game by making enemy spawning and movement.
     */
    private void pauseGame() {
        enemySpawner.pause(); // stop the creation of new enemies
        for (Timeline enemyMovement : enemiesTimelines) {//stop the movements of current enemies
            enemyMovement.pause();
        }
    }

    /**
     * Resumes the game by restarting enemy spawning and movement.
     */
    private void resumeGame() {
        enemySpawner.play(); //// start the creation of new enemies
        for (Timeline enemyMovement : enemiesTimelines) {  // start the movements of current enemies
            enemyMovement.play();
        }
    }

    /**
     * Fires a projectile from the player's current position.
     * The projectile will move upwards and may or may not collide with enemies.
     */
    private void fireProjectile() {
        if(isPaused) return; // if the user press the P, user cannot fire any more projectiles
        // to fire the bullet from the middle of the player.since the bullet is 20 pixels, we wrote -10.
        Projectile projectile = new Projectile(player.getLayoutX() + player.getFitWidth() / 2 - 10, player.getLayoutY());
        root.getChildren().add(projectile);
        projectiles.add(projectile); //we added it to the list to be able to control the bullet
    }

    /**
     * Moves all projectiles upwards, checking for collisions with enemies.
     * Removes projectiles and enemies if they collide and update scores according to that.
     */
    private void moveProjectiles() {
        if(isPaused) return; // if the user press the P, cannot move any projectile anymore
        List<Projectile> projectilesToRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            projectile.move(); // Make the projectile movement upwards

            // Collision control
            for (Enemy enemy : enemies) {
                if (projectile.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    // There was a collision, delete the enemy and the bullet
                    root.getChildren().remove(projectile);
                    root.getChildren().remove(enemy);
                    projectilesToRemove.add(projectile);
                    enemies.remove(enemy);

                    // Update the score
                    score += 100;
                    scoreText.setText("Score: " + score);

                    // Take the position of the enemy for making reward or the punishment object
                    double enemyX = enemy.getLayoutX();
                    double enemyY = enemy.getLayoutY();

                    // Randomly reward or penalty determination
                    double randomChance = Math.random();
                    if (randomChance <= 0.6) { // the enemy has a 60% chance of dropping a reward or punishment object
                        isThereReward =true;
                        if (Math.random() <= 0.4) { // the enemy has a 40% chance of dropping the reward object
                            if(Math.random() <= 0.2){ //the enemy has a 20% chance of dropping the score reward
                                createReward(enemyX, enemyY, "score_reward");
                            }else{ //the enemy has a 20% chance of dropping the enhanced firing ability reward
                                createReward(enemyX, enemyY, "firing_reward");
                            }

                        } else { //the enemy has a 20% chance of dropping the punishment
                            createPunishment(enemyX, enemyY, "punishment");
                        }
                    }
                    break;
                }
            }

            // Remove the projects that collide and go off-screen
            if (projectile.getLayoutY() < 0) {
                root.getChildren().remove(projectile);
                projectilesToRemove.add(projectile);
            }
        }
        projectiles.removeAll(projectilesToRemove);
        if(rewardOrPunishments.isEmpty()){
            isThereReward =false;
        }
        if(isThereReward){ // if there is no reward or punishment in the screen just do not call the function for it
            moveRewards();
        }
    }
    /**
     * Moves all rewards and punishments downwards, checking for collisions with the player.
     * If the player catches a reward or punishment, there will be consequences depending on which object is caught.
     */
    private void moveRewards(){
        if(isPaused) return; // If the user press the P, the rewards cannot move anymore.
        List<RewardOrPunishment> thingsToRemove = new ArrayList<>();
        for (RewardOrPunishment rewardOrPunishment: rewardOrPunishments){
            rewardOrPunishment.moveDown(); //move the reward downwards.

            if( rewardOrPunishment.getBoundsInParent().intersects(player.getBoundsInParent())){ // If the player catches the reward or punishment object
                onCatch(rewardOrPunishment);
                thingsToRemove.add(rewardOrPunishment);
            }
            if(rewardOrPunishment.getLayoutY()> root.getHeight()){ // If the reward or punishment go off-screen
                thingsToRemove.add(rewardOrPunishment);
            }
        }
        rewardOrPunishments.removeAll(thingsToRemove);
    }

    /**
     * Checks if the player has collided with any enemies.
     * If a collision occurs, the game is ended and the final screen is shown.
     */
    private void check_game_ends(){
        // Margin for more precise collision detection
        double margin = 5;

        for (Enemy enemy: enemies){
            Bounds playerBounds = player.getBoundsInParent();
            Bounds enemyBounds = enemy.getBoundsInParent();
            // I did that here because in the game, even if there is still some gap it goes into the collision condition. Still not perfect, but I think it is better than before.
            // Create new rectangles with margins
            Rectangle playerRect = new Rectangle(playerBounds.getMinX() + margin,playerBounds.getMinY() + margin,
                    playerBounds.getWidth() - 7 * margin,playerBounds.getHeight() - 7* margin);
            Rectangle enemyRect = new Rectangle(enemyBounds.getMinX() + margin,enemyBounds.getMinY() + margin,
                    enemyBounds.getWidth() - 7* margin,enemyBounds.getHeight() - 7* margin);
            // Check for collision
            if(playerRect.intersects(enemyRect.getBoundsInParent())){
                collisionCheck.stop(); // Stop collision check because the game ended
                pauseGame(); // Pause the game
                new FinalScreen(score, stage); // Shows final screen with the final score
               return;
            }
        }
    }

    /**
     * Creates a reward at a given position with a given type.
     * Rewards can provide score increase or enhance on firing abilities.
     * @param x The x-coordinate where the reward will be placed
     * @param y The y-coordinate where the reward will be placed
     * @param type The type of reward ("score_reward" or "firing_reward")
     */
    private void createReward(double x, double y, String type){
        RewardOrPunishment reward = new Reward(x, y, type);
        rewardOrPunishments.add(reward); // We are adding both the "reward" and "punishment" type to this list
        root.getChildren().add(reward);

    }
    /**
     * Creates a punishment at a given position with a given type.
     * Punishments decrease the score.
     * @param x The x-coordinate where the punishment will be placed
     * @param y The y-coordinate where the punishment will be placed
     * @param type The type of punishment ("punishment")
     */
    private void createPunishment(double x, double y, String type){
RewardOrPunishment punishment = new Punishment(x,y,type);
rewardOrPunishments.add(punishment);
root.getChildren().add(punishment);
    }
    /**
     * Displays a temporary message on the screen for a specified duration.
     * The message will be displayed in the top left corner of the screen below of the score text.
     * @param message The message to display
     * @param color The color of the message text(green for reward, red for punishment)
     */
    private void showTemporaryMessage(String message, String color) {
        Text tempMessage = new Text(message); // Create a new message
        tempMessage.setFont(new Font(20));
        tempMessage.setFill(Color.web(color)); // We take the color of the message as a parameter


        double yOffset = 0;
        for (Text existingMessage : messages) {
            // Adjust the yOffset based on the height of existing messages
            yOffset += existingMessage.getLayoutBounds().getHeight() + 5; // height of the message +gap
        }

        // Set the position of the new message
        tempMessage.setLayoutX(scoreText.getLayoutX());
        tempMessage.setLayoutY(scoreText.getLayoutY() + 30 + yOffset);

        root.getChildren().add(tempMessage);
        messages.add(tempMessage);   // Add to the message list for keeping track of them

        // Set up a timer to remove the message after the determined duration
        Timeline removeMessage;
        if (message.equals("Enhanced firing capabilities")) {
            // Remove after 5 seconds for this message
            removeMessage = new Timeline(new KeyFrame(Duration.seconds(5), e -> {
                root.getChildren().remove(tempMessage);
                messages.remove(tempMessage); // Remove from the message list
                updateMessagesLayout();  // Reposition the remaining messages
            }));
        } else {
            // Remove after 2 seconds for other messages
            removeMessage = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                root.getChildren().remove(tempMessage);
                messages.remove(tempMessage);
                updateMessagesLayout();
            }));
        }
        removeMessage.setCycleCount(1);
        removeMessage.play();
    }

    /**
     * Updates the positions of all displayed messages to ensure they don't overlap.
     */
    private void updateMessagesLayout() {
        double yOffset = 0;
        for (Text message : messages) {
            //Calculate the position of the message again
            message.setLayoutY(scoreText.getLayoutY() + 30 + yOffset);
            yOffset += message.getLayoutBounds().getHeight() + 5;  // height of the message +gap
        }
    }


    /**
     * Applies the consequences of catching a reward or punishment object.
     * The player may increase their score, enhance firing capabilities or decrease their score.
     * @param reward_or_punishment The reward or punishment caught by the player
     */
    private void onCatch(RewardOrPunishment reward_or_punishment){
        if(reward_or_punishment.getType().equals("score_reward")){
            score += 50;
            scoreText.setText("Score: " + score);
            showTemporaryMessage("Score Increased", "green");
        }
        else if(reward_or_punishment.getType().equals("firing_reward")){
            enableEnhancedFiring(5);
            showTemporaryMessage("Enhanced firing capabilities", "green");
        }
        else if(reward_or_punishment.getType().equals("punishment")){
            score -= 50;
            scoreText.setText("Score: " + score);
            showTemporaryMessage("Punishment", "red");
        }
        root.getChildren().remove(reward_or_punishment);
    }

    /**
     * Enables enhanced firing capabilities for the player for a specified duration.
     * During this time, the player can fire multiple projectiles at once.
     * @param seconds The duration of the enhanced firing ability
     */
    private void enableEnhancedFiring(int seconds) {
        Timeline enhancedFiringTimeline = new Timeline(new KeyFrame(Duration.seconds(0.18), e -> {  // Fires every 0.18 seconds
            // Create projectiles for diagonal fire directions
            fireProjectile(); // upwards
            fireProjectileDiagonalLeft();
            fireProjectileDiagonalRight();
        }));
        enhancedFiringTimeline.setCycleCount((int)(seconds / 0.18)); // runs for the specified time (every 0.18 seconds)
        enhancedFiringTimeline.play();
    }
    /**
     * Fires a projectile in the left diagonal direction from the player's position.
     */
    private void fireProjectileDiagonalLeft() {
        if(isPaused) return;
        Projectile projectile = new Projectile(player.getLayoutX() + player.getFitWidth() / 2 - 10, player.getLayoutY());
        projectile.setDirection(-0.7, -1); // Left diagonal direction
        root.getChildren().add(projectile);
        projectiles.add(projectile);
    }
    /**
     * Fires a projectile in the right diagonal direction from the player's position.
     */
    private void fireProjectileDiagonalRight() {
        if(isPaused) return;
        Projectile projectile = new Projectile(player.getLayoutX() + player.getFitWidth() / 2 - 10, player.getLayoutY());
        projectile.setDirection(0.7, -1); // Right diagonal direction
        root.getChildren().add(projectile);
        projectiles.add(projectile);


    }


}

