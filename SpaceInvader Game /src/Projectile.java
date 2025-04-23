import javafx.scene.image.ImageView;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.scene.image.Image;

/**
 * Represents a projectile. This class loads the projectile image from a file and allows the projectile
 * object to move in the specified direction .
 */
public class Projectile extends ImageView {
    // Speed of the projectile's movement.
    private double speed =30;

    // Direction of the projectile's movement.
    private double directionX = 0;// Moves horizontally.
    private double directionY = -1;  // Moves vertically.
    /**
     * .Loads the projectile image from the "assets/projectile1.png" file and
     * sets its initial position based on the player's position.
     * If the image file cannot be found, an informative message is printed.
     *
     * @param player_x starting position of the projectile.
     * @param player_y starting position of the projectile.
     */

    public Projectile(double player_x, double player_y){
            // dynamically arrange the path to the projectile image
        String[] extensions = {".png", ".jpg",".jpeg"}; // The file extensions for the projectile image
        boolean fileFound = false;
        for (String ext : extensions){ // Search for the correct extension
            Path pathToProjectileImage = Paths.get("assets/projectile" + ext);
            File file = pathToProjectileImage.toFile();
            if (file.exists()){
                fileFound=true;
                // Load the projectile image from the file
                Image projectileImage = new Image(file.toURI().toString());
                setImage(projectileImage);
                break;
            }
        }

            if (!fileFound) {
                System.out.println("Projectile asset cannot be not found in any format.");
// I did not put here return because if the teacher did not put any asset for projectile while testing I want the program to run.
                // The program will run but just with informative message that the image cannot be found in any format.
            }
        // Set the size of the projectile image
            setFitWidth(30);
            setFitHeight(30);
            setLayoutX(player_x);  // Set the initial X position according to the player's position
            setLayoutY(player_y);
    }
    /**
     * Sets the direction of the projectile's movement.
     *
     * @param directionX The horizontal direction of the projectile.-1 for left, 1 for right.
     * @param directionY The vertical direction of the projectile.-1 for up, 1 for down.
     */
    public void setDirection(double directionX, double directionY) {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    /**
     * Moves the projectile in the given direction by the given speed.
     */
    public void move() {
        // With this function I can arrange the movement in any direction I wanted.
        this.setLayoutY(this.getLayoutY() +directionY* speed);
        this.setLayoutX(this.getLayoutX()+ directionX*speed);
    }

}
