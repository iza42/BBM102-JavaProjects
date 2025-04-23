import javafx.scene.image.Image;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a punishment object in the game.
 * Extends the RewardOrPunishment class and displays a punishment image
 * (by loading the punishment image)at a given position.
 */
public class Punishment extends RewardOrPunishment{
    /**
     * Loads the punishment image from the "assets/punishment.png" file and sets
     * its position based on the given x and y coordinates.
     * If the image file cannot be found, an informative message will be printed.
     *
     * @param x The X-coordinate where the punishment will be displayed.
     * @param y The Y-coordinate where the punishment will be displayed.
     * @param type The type of punishment.There is only 1 type for punishment
     */
    public Punishment(double x, double y, String type) {
        // Pass the punishment type to the parent class
        super(type);
        //dynamically arrange the path for the punishment image
        String[] extensions = {".png", ".jpg",".jpeg"}; // The file extensions for the punishment image
        boolean fileFound = false;

        for (String ext : extensions){ // Search for the correct extension
            Path pathToPunishmentImage = Paths.get("assets/punishment" + ext);
            File file = pathToPunishmentImage.toFile();
            if (file.exists()){
                fileFound = true;
                // Load the punishment image from the file
                Image projectileImage = new Image(file.toURI().toString());
                setImage(projectileImage);
                break;
            }
        }

            if (!fileFound) {
                System.out.println("Punishment image cannot be found in any format.");
                return;
            }

        // Set the size of the punishment object
            setFitWidth(50);
            setFitHeight(50);

        // Set the position of the punishment object
        setLayoutX(x);
        setLayoutY(y);

    }


}
