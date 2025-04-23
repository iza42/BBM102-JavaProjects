/**
 * Represents a generic spacecraft system with energy consumption and activation status.
 * Subclasses define specific types of spacecraft systems.
 */
public class SpacecraftSystem {
    private final double energy_consumption; // Energy consumption of the system
    private static boolean isActive = true; // Indicates whether the system is active
    private String problem; // Problem description, if any
    private Mission mission; // Associated mission

    /**
     * Constructs a spacecraft system with specified energy consumption and mission.
     *
     * @param energy_consumption The energy consumption of the system.
     * @param mission The given mission in the input file.
     */
    public SpacecraftSystem(double energy_consumption, Mission mission){
       this.energy_consumption=energy_consumption;
       this.mission=mission;
   }

    // Getter and setter methods
    /**
     * Returns the energy consumption of the system.
     *
     * @return The energy consumption.
     */
    public double getEnergy_consumption() {
        return energy_consumption;
    }

    /**
     * Returns the given mission in the file.
     *
     * @return The mission object linked to this system for getting the mission data.
     */
    public Mission getMission() {
        return mission;
    }
    /**
     * Sets the given mission for the system.
     *
     * @param mission The mission to link to this system.
     */
    public void setMission(Mission mission) {
        this.mission = mission; 
    }

    /**
     * Returns whether the system is active.
     *
     * @return True if the system is active, false otherwise.
     */
    public boolean getisActive() {
        return isActive;
    }
    /**
     * Sets the active status of the system.
     *
     * @param isActive True to activate the system, false to deactivate.
     */
    public void setisActive(boolean isActive){
       SpacecraftSystem.isActive =isActive;
    }
    /**
     * Returns the current problem description.
     *
     * @return The problem description as a string.
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Sets the problem description for the system.
     *
     * @param problem The problem description to set.
     */
    public void setProblem(String problem) {
        this.problem ="Cannot perform spaceflight,"+ problem;
    }
}
/**
 * A spacecraft system responsible for propulsion, managing fuel levels,
 * and ensuring enough fuel for the mission.
 */
class PropulsionSystem extends SpacecraftSystem{
    private double fuel_level; // Current fuel level
    private final double fuel_per_km; // Fuel consumption per kilometer

    /**
     * Constructs a propulsion system with specified parameters.
     * Gives a message about activating the system.
     *
     * @param energy_consumption The energy consumption of the propulsion system.
     * @param fuel_level The initial fuel level.
     * @param fuel_per_km The fuel consumption per kilometer.
     * @param mission The associated mission.
     */
    public  PropulsionSystem(double energy_consumption, double fuel_level, double fuel_per_km, Mission mission){
        super(energy_consumption,mission);
        this.fuel_level=fuel_level;
        this.fuel_per_km=fuel_per_km;
        System.out.println("Propulsion System is now active.");
        System.out.println("Propulsion system is ready for launch.");
    }
    /**
     * Checks if there is enough fuel for the mission and adjusts the fuel level if there is enough fuel.
     *
     * @param navigationSystem The navigation system to calculate required distance.
     * @return True if there is enough fuel, false otherwise.If there is no enough fuel also sets the problem according to the issue here.Make the isActive false.
     */
    public boolean  hasEnoughFuel(NavigationSystem navigationSystem) {
        double necessary_fuel = navigationSystem.getcalculatedDistance() * fuel_per_km;
        if (fuel_level >= necessary_fuel) { // if the fuel level is enough for propelling the spacecraft.
            fuel_level -= necessary_fuel;
            return true;
        } else {
            setisActive(false);
            setProblem(" fuel level is not enough!");
            return false;
        }
    }


    /**
     * Returns a detailed string representation of the system, including its
     * operational status and resource levels.
     *
     * @return A formatted string describing the system state.
     */
    @Override
    public String toString() {
        String message;
        if(getisActive()) { // ==true
             message = "Propulsion System Status: Operating at " + Math.round( fuel_level * 10) / 10.0+ "% fuel\nFuel Level: "+ fuel_level;
            return message;
        }
        message= "Propulsion System is now standby.";
        return message;
    }


}
/**
 * A spacecraft system responsible for managing life support, including oxygen
 * levels and temperature control.
 */
class LifeSupportSystem extends SpacecraftSystem{
    private final double temperature; // Cabin temperature
    private double oxygen_level; // Current oxygen level
    private final double oxygen_coefficient; // Oxygen consumption coefficient


    /**
     * Constructs a life support system with specified parameters.
     * Gives a message about activating the system.
     *
     * @param energy_consumption The energy consumption of the system.
     * @param temperature The cabin temperature.
     * @param oxygen_level The initial oxygen level.
     * @param oxygen_coefficient The coefficient for oxygen consumption.
     * @param mission The associated mission.
     */
    public LifeSupportSystem(double energy_consumption, double temperature, double oxygen_level, double oxygen_coefficient, Mission mission){
        super(energy_consumption, mission);
        this.temperature=temperature;
        this.oxygen_level=oxygen_level;
        this.oxygen_coefficient =oxygen_coefficient;

        System.out.println("Life Support System is now active.");
        System.out.println("Life support system activated and stabilizing environment.");
    }

    /**
     * Checks the cabin conditions, including temperature and oxygen levels.
     *
     * @return True if conditions are within acceptable limits, false otherwise. If false also sets the problem description and make the isActive false.
     */
    public boolean checkCabinConditions() {
        double tempDifference = Math.abs(getMission().getExpected_temperature() - this.temperature);
        if(tempDifference>100){ // I added this extra check because I felt like I should
            setisActive(false);
            setProblem(" temperature discrepancy is too much!");
            return false;
        }
        double requiredOxygen = tempDifference * getMission().getMission_duration() * this.oxygen_coefficient;
        if (this.oxygen_level >= requiredOxygen) {
            oxygen_level-=requiredOxygen;
            return true;
        }else{
            setisActive(false);
            setProblem(" oxygen level is not enough!");
            return  false;
        }
    }
    /**
     * Returns a detailed string representation of the system, including its
     * operational status and resource levels.
     *
     * @return A formatted string describing the system state.
     */
    @Override
    public String toString() {
        String message;
        if(getisActive()) { // ==true
            message = "Life Support System Status: Oxygen Level: " + Math.round(oxygen_level * 10) / 10.0+ "%, Temperature: "+getMission().getExpected_temperature()+"°C";
            return message;
        }
        message= "Life Support System is now standby.";
        return message;
    }
}
/**
 * A spacecraft system responsible for navigation, calculating distances
 * between the current and target locations.
 */
class NavigationSystem extends SpacecraftSystem{

    private final double start_latitude; // Starting latitude
    private final double start_longitude; // Starting longitude

    /**
     * Constructs a navigation system with specified parameters.
     * Gives a message about activating the system.
     *
     * @param energy_consumption The energy consumption of the system.
     * @param start_latitude The starting latitude.
     * @param start_longitude The starting longitude.
     * @param mission The associated mission.
     */
    public  NavigationSystem (double energy_consumption, double start_latitude, double start_longitude, Mission mission){
        super (energy_consumption, mission);
        this.start_latitude=start_latitude;
        this.start_longitude=start_longitude;
        System.out.println("Navigation System is now active.");
        System.out.println("Navigation system activated and stabilizing environment.");
    }

    /**
     * Returns a detailed string representation of the system, including its
     * operational status and resource levels.
     *
     * @return A formatted string describing the system state.
     */
    @Override
    public String toString() {
        String message;
        if(getisActive()){
            message= "Navigation System Status: Active\nCurrent Latitude: "+  Math.round(start_latitude * 10) / 10.0+ " Current Longitude: "+ Math.round(start_longitude * 10) / 10.0;
            return message;
        }
        message="Navigation System is now standby.";
        return message;
    }

    /**
     * Calculates the distance between the current and target locations using
     * the Haversine formula.
     *
     * @return The calculated distance in kilometers.
     */
    public double getcalculatedDistance() {
        return Haversine(start_latitude, start_longitude, getMission().getTarget_latitude(), getMission().getTarget_longitude());
    }

    /**
     * Applies the Haversine formula to calculate the distance between two points.
     *
     * @param currentLatitude Current latitude .
     * @param currentLongitude Current longitude .
     * @param targetLatitude Target latitude .
     * @param targetLongitude Target longitude .
     * @return The distance between the two points in kilometers.
     */
    public double Haversine(double currentLatitude, double
            currentLongitude, double targetLatitude, double
                                           targetLongitude){
        final int R = 6371; // Radius of the Earth in kilometers
// Convert latitude and longitude from degrees to radians
        double latDistance = Math.toRadians(targetLatitude -
                currentLatitude);
        double lonDistance = Math.toRadians(targetLongitude -
                currentLongitude);
// Apply Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance
                / 2) +
                Math.cos(Math.toRadians(currentLatitude)) * Math.
                        cos(Math.toRadians(targetLatitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(
                        lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }

}


