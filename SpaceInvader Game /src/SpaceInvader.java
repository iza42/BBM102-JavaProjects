import javafx.application.Application;
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
 * This is the starting screen of the game.
 * It displays the game title and provides options to either start
 * the game or exit the application based on the player's input(taken from keyboard).
 */
public class SpaceInvader extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Create labels
        Label startGame = new Label("PRESS ENTER TO PLAY");
        Label exitGame = new Label("PRESS ESC TO EXIT");
        // Set fonts and text colors for the labels
        startGame.setFont(new Font(50));
        startGame.setTextFill(Color.NAVY);
        exitGame.setFont(new Font(50));
        exitGame.setTextFill(Color.NAVY);

        // Load and set background image for the start screen
        try {
            // dynamically arranging the path for the background
            Path pathToAssets;
            String[] extensions = {".png", ".jpg",".jpeg"}; //The file extensions for the background image
            boolean fileFound = false;

            ImageView backgroundView = null; //I put here null cuz if I do not ,I get error downwards
            for (String ext : extensions) { //Searching for the correct extension
                pathToAssets = Paths.get("assets/main" + ext);
                File file = pathToAssets.toFile();
                if (file.exists()) {
                    fileFound = true;

                    // Load the background image
                    Image background = new Image(file.toURI().toString());
                    backgroundView = new ImageView(background);

                    // Set application icon
                    Image icon = new Image(pathToAssets.toUri().toString());
                    stage.getIcons().add(icon);
                    break;
                }
            }

            if (!fileFound) {
                System.out.println("Background asset cannot be found in any format.");
            }
            double screenWidth = Screen.getPrimary().getBounds().getWidth();
            double screenHeight = Screen.getPrimary().getBounds().getHeight();

            backgroundView.setFitWidth(screenWidth);
            backgroundView.setFitHeight(screenHeight);

            // Create a vertical layout for labels
            VBox textOrganizer = new VBox(30);// 30 pixel gap
            textOrganizer.getChildren().addAll(startGame, exitGame);

            // Create a StackPane to hold both the background and the text layout
            StackPane root = new StackPane();
            root.getChildren().addAll(backgroundView, textOrganizer);
            textOrganizer.setAlignment(javafx.geometry.Pos.CENTER);

            // Create a Scene and arrange  keyboard handling
            Scene scene = new Scene(root, screenWidth, screenHeight);
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    new GameScreen(stage); // Goes to the main page where game will occur
                } else if (event.getCode() == KeyCode.ESCAPE) {
                    stage.close(); // closes the application
                }
            });


            stage.setTitle("SPACE INVADER START SCREEN");
            stage.setScene(scene);
            stage.setFullScreen(false); // for avoid full-screen mode
            stage.show();
        }catch (Exception e){
            //handling any potential exceptions
            System.out.println("Error in StartScreen");
            e.printStackTrace();
        }
    }
}
