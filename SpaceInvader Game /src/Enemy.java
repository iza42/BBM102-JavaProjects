import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents an enemy object in the game.
 * This class loads the enemy image from a file and make the enemy object to move downwards.
 * Also,specifies the object size.
 */
public class Enemy extends ImageView {
    // Speed of enemy movements
    private final double speed= 7;

    /**
     * Loads the enemy image from the "assets/enemy.png" file and sets its sizes.
     * If the image file cannot be found, an informative message will be printed.
     */
    public Enemy(){
        // dynamically arranging the path for the enemy object
        String[] extensions = {".png", ".jpg",".jpeg"}; //The file extensions for the enemy object
        boolean fileFound = false;
        for (String ext : extensions){ // Searching for the correct extension
            Path pathToAssets = Paths.get("assets/enemy" + ext);
            File file = pathToAssets.toFile();
            if (file.exists()){
                fileFound = true;
                // Load the enemy image from the file
                Image enemyImage = new Image(file.toURI().toString());
                setImage(enemyImage);
                break;
            }
        }
        if (!fileFound) {
            System.out.println("Enemy asset cannot be found in any format.");
            return;
        }

        //Set the image's sizes
        setFitWidth(100);
        setFitHeight(100);
    }
    /**
     * Moves the enemy downwards.
     * Whenever this method is called,the enemy moves down by the value of speed .
     */
    public void moveDown() {
        setLayoutY(getLayoutY() + speed);
    }

}