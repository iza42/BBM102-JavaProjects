import javafx.scene.image.Image;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a reward object in the game. It extends the RewardOrPunishment class and
 * displays a reward image(by loading the punishment image) at a given position.
 */
public class Reward extends RewardOrPunishment {

    /**
     * Loads the reward image from the "assets/reward" file and sets its position
     * based on the provided x and y coordinates.
     * If the image file cannot be found,an informative message is printed.
     *
     * @param x The X-coordinate where the reward will be displayed.
     * @param y The Y-coordinate where the reward will be displayed.
     * @param type The type of reward. There is 2 type for the reward
     */
    public Reward(double x, double y, String type) {
        super(type);

        //dynamically arrange the path for the reward image
        String[] extensions = {".png", ".jpg",".jpeg"}; // The file extensions for the reward object
        boolean fileFound = false;
        for (String ext : extensions){// Search for the correct extension
            Path pathToRewardImage = Paths.get("assets/reward" + ext);
            File file = pathToRewardImage.toFile();
            if (file.exists()){
                fileFound = true;
                // Load the reward image from the file
                Image rewardImage = new Image(file.toURI().toString());
                setImage(rewardImage);
                break;
            }
        }
            if (!fileFound) {
                System.out.println("Reward asset cannot be found in any format.");
                return;
            }

        // Set the size of the reward object
            setFitWidth(50);
            setFitHeight(50);
        // Set the position of the reward object
        setLayoutX(x);
            setLayoutY(y);
    }

}

