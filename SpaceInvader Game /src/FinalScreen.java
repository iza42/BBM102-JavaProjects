import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This screen shows the text as a "GAME OVER!" and  the player's overall score and
 * can restart game by pressing the "R" and can return to the starting screen which is SpaceInvader class
 * by pressing ESC.
 */
public class FinalScreen {
    /**
     * @param score The overall score of the player.
     * @param stage The current Stage.
     */
        public FinalScreen(int score, Stage stage){
            // Create labels for displaying game over message and score and restart instruction
            Label gameOver= new Label("GAME OVER!");
            Label playerScore = new Label("PLAYER'S FINAL SCORE: " + score);
            Label restartGame = new Label("PRESS R TO RESTART THE GAME");
            // Set fonts and text colors for the labels
            gameOver.setFont(new Font(100));
            gameOver.setTextFill(Color.NAVY);
            restartGame.setFont(new Font(30));
            restartGame.setTextFill(Color.NAVY);
            playerScore.setFont(new Font(30));
            playerScore.setTextFill(Color.NAVY);
            try{
                // dynamically arranging the path for the background
                Path pathToAssets ;
                String[] extensions = {".png", ".jpg",".jpeg"};
                boolean fileFound = false;

                ImageView backgroundView = null;
                for (String ext : extensions){ // for finding the correct extension
                    pathToAssets = Paths.get("assets/main" + ext);
                    File file = pathToAssets.toFile();
                    if (file.exists()){
                        fileFound = true;

                        //Load the background image for final screen
                        Image background = new Image(file.toURI().toString());
                        backgroundView = new ImageView(background);

                        //Set the game icon
                        Image icon = new Image(file.toURI().toString());
                        stage.getIcons().add(icon);
                        break;
                    }
                }

                if (!fileFound) {
                    System.out.println("Background asset cannot be found in any format.");
                    return;
                }
                // Get screen width and height to fit the background image
                double screenWidth = Screen.getPrimary().getBounds().getWidth();
                double screenHeight = Screen.getPrimary().getBounds().getHeight();
                backgroundView.setFitWidth(screenWidth);
                backgroundView.setFitHeight(screenHeight);
                // Create a VBox layout to organize the text vertically
                VBox textOrganizer= new VBox(30);// 30pixel gap between text elements
                textOrganizer.setAlignment(javafx.geometry.Pos.CENTER);
                textOrganizer.getChildren().addAll(gameOver, restartGame,playerScore);

                // Create a StackPane to hold the background and the text layout
                StackPane root = new StackPane();
                root.getChildren().addAll(backgroundView, textOrganizer);
                // Create a Scene and arrange  the key event handling
                Scene scene = new Scene(root, screenWidth, screenHeight);
                scene.setOnKeyPressed(event -> {
                    // Restarting the game
                    if (event.getCode() == KeyCode.R) {
                        new GameScreen(stage);
                    }else if (event.getCode() == KeyCode.ESCAPE) { //Returning to the start screen
                        new SpaceInvader().start(stage);
                    }
                });

                stage.setScene(scene);
                stage.setFullScreen(false); //  for avoiding full screen
                stage.show();
            }catch (Exception e){
                //handling potential exceptions
                System.out.println("Error occurred in FinalScreen");
                e.printStackTrace();
            }

        }
}
