/**
 * Represents a mission with target coordinates, duration, temperature,
 * and operational task type. Includes methods to start and complete the mission.
 */
public class Mission {
    private final double target_latitude; // Latitude of the mission's target location
    private final double target_longitude; // Longitude of the mission's target location
    private final double expected_temperature; // Expected temperature during the mission
    private double mission_duration; // Duration of the mission in months
    protected String operational_task; // The type of task for the mission (explore, satellite, supply)

    /**
     * Constructs a new Mission with the specified parameters.
     *
     * @param target_latitude     The latitude of the mission's target location.
     * @param target_longitude    The longitude of the mission's target location.
     * @param mission_duration    The duration of the mission in days.
     * @param expected_temperature The expected temperature during the mission.
     * @param operational_task    The type of task for the mission.
     */
    public Mission(double target_latitude, double target_longitude,  double mission_duration,double expected_temperature, String operational_task) {
        this.target_latitude = target_latitude;
        this.target_longitude = target_longitude;
        this.expected_temperature = expected_temperature;
        this.operational_task = operational_task;
        setMission_duration(mission_duration);
    }

    /**
     * Starts the mission and displays a message indicating the mission has begun.
     * The message is based on the type of operational task.
     */
    public void start(){
        if(operational_task.equals("exploreMission")){
            System.out.println("Explore mission is started");
        } else if (operational_task.equals("satelliteMission")) {
            System.out.println("Satellite mission is started");
        }
        else {
            System.out.println("Supply mission is started");
        }
    }

    // Getter methods for accessing mission properties
    /**
     * @return The latitude of the mission's target location.
     */
    double getTarget_latitude() {
        return target_latitude;
    }

    /**
     * @return The longitude of the mission's target location.
     */
    double getTarget_longitude() {
        return target_longitude;
    }

    /**
     * @return The duration of the mission in months.
     */
    double getMission_duration() {
        return mission_duration;
    }

    /**
     * @return The expected temperature during the mission.
     */
    public double getExpected_temperature() {
        return expected_temperature;

    }

    /**
     * Sets the duration of the mission based on the type of operational task.
     * Ensures the duration falls within valid ranges for the given task type.
     *
     * @param mission_duration The new duration of the mission in days.
     */
    public void setMission_duration(double mission_duration) {
        switch (this.operational_task) {
            case "exploreMission" -> {
                if (mission_duration >= 3 && mission_duration <= 5) {
                    this.mission_duration = mission_duration;
                }
            }
            case "satelliteMission" -> {
                if (mission_duration >= 1 && mission_duration <= 2) {
                    this.mission_duration = mission_duration;
                }
            }
            case "supplyMission" -> {
                if (mission_duration <= 1) {
                    this.mission_duration = mission_duration;
                }
            }
        }
    }

    /**
     * Completes the mission and prints a result message according to the sufficient level of the fuel and the oxygen
     * with another word based on the activeness.
     *
     * @param isActive Indicates whether the mission systems were active(active means that there are sufficient level of energy, fuel and oxygen).
     */
    public void finish_mission (boolean isActive){
        switch (operational_task) {
            case "exploreMission" -> {
                if (isActive) {
                    System.out.println("Explore rescue mission is ended.");
                } else {
                    System.out.println("Explore mission start failed.");
                }
            }
            case "satelliteMission" -> {
                if (isActive) {
                    System.out.println("Satellite rescue mission is ended.");
                } else {
                    System.out.println("Satellite mission start failed.");
                }
            }
            case "supplyMission" -> {
                if (isActive) {
                    System.out.println("Supply rescue mission is ended.");

                } else {
                    System.out.println("Supply mission start failed.");

                }
            }
        }

    }
}
