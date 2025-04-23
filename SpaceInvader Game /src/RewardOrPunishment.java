import javafx.scene.image.ImageView;
/**
 * Represents a common superclass for both rewards and punishments.
 * It extends ImageView to allow for image based representation.
 * Provides basic functionality for movement.
 */
public class RewardOrPunishment extends ImageView  {
    // The type of the object (score_reward, firing_reward or punishment).
    private String type;
    // Speed of the object
    private double speed = 7;
    /**
     * This class serves as a base for both Reward and Punishment objects classes.
     *
     * @param type The type of the object(score_reward, firing_reward or punishment)).
     */
    public RewardOrPunishment( String type){
        // Set the type of the object
        this.type=type;
    }

    /**
     * Moves the object down.
     * This method can be used to move the reward or punishment object.
     */
    public void moveDown() {
        setLayoutY(getLayoutY() + speed);
    }

    /**
     * Gets the type of the object.
     *
     * @return The type of the object (score_reward, firing_reward or punishment).
     */
    public String getType() {
        return type;
    }

}
