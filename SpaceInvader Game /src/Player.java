import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the player. This class loads the player's image from a file and make
 * the player object to move leftwards and rightwards.
 * Also,specifies the object size.
 */
public class Player extends ImageView {
    // Speed of the player.
    private double player_speed = 100;
    /**
     *Loads the player image from the "assets/player.png" file and sets its sizes.
     * If the image file cannot be found, an informative message will be printed.
     */
    public Player(){
            // dynamically arranging the path for the player object
        String[] extensions = {".png", ".jpg", ".jpeg"};// The file extensions for the player object
        boolean fileFound = false;
        for (String ext : extensions){ // Searching for the correct extension
            Path pathToAssets = Paths.get("assets/player" + ext);
            File file = pathToAssets.toFile();
            if (file.exists()){
                fileFound = true;

                //load the player image from the file
                Image playerImage = new Image(file.toURI().toString());
                setImage(playerImage);
                break;
            }
        }

            if (!fileFound) {
                System.out.println("Player asset cannot be found in any format.");
                return;
            }
        // Set the player's image size
        setFitWidth(200);
        setFitHeight(200);
        setPreserveRatio(true);
    }

    /**
     * Moves the player to the left by a fixed speed, ensuring the player
     * does not go further left edge of the screen.
     */
    public void moveLeft() {
        double minX = 0;
        // Ensures that the player doesn't move further left edge
        if (getLayoutX() > minX) {
            setLayoutX(Math.max(getLayoutX() - player_speed, minX));
        }
    }

    /**
     * Moves the player to the right with a fixed speed, ensures the player
     * does not go further the right edge of the screen.
     */
    public void moveRight() {
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double maxX = screenWidth - getFitWidth();// Ensure the player stays within the screen bounds
        // Ensure the player doesn't move further the right edge
        if (getLayoutX() < maxX) {
            setLayoutX(Math.min(getLayoutX() + player_speed, maxX));
        }
    }
}
