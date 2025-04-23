import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The main entry point for the spacecraft simulation program.
 * This class initializes spacecraft systems and missions based on input data,
 * executes the mission if all conditions are met.
 */
public class Main {
    /**
     * The main method of the program.
     * It accepts three command-line arguments: the input file path, the name of the spacecraft,
     * and the total energy level. It initializes the spacecraft systems and
     * determines whether the mission can start.
     *
     * @param args Command-line arguments:
     *                args[0]: Path to the input file
     *                 args[1]: Name of the spacecraft
     *                args[2]: Total energy level available for the spacecraft
     *
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java Main <input_file> <spacecraft_name> <total_energy_level>");
            return;
        }
        // Input file and spacecraft details
        String inputFile = args[0];
        String spacecraftName = args[1];
        double totalEnergyLevel = Double.parseDouble(args[2]);

        // Initialize spacecraft systems
        ArrayList<Object> systems = readTxt(inputFile);
        PropulsionSystem propulsionSystem = null;
        LifeSupportSystem lifeSupportSystem = null;
        NavigationSystem navigationSystem = null;
        Mission mission = null;

        // Extract the mission from the systems
        for (Object obj : systems) {
            if (obj instanceof Mission) {
                mission = (Mission) obj;
                break;
            }
        }
        // Assign the mission to each system
        for (Object obj : systems) {
            if (obj instanceof PropulsionSystem) {
                propulsionSystem = (PropulsionSystem) obj;
                propulsionSystem.setMission(mission);
            } else if (obj instanceof LifeSupportSystem) {
                lifeSupportSystem = (LifeSupportSystem) obj;
                lifeSupportSystem.setMission(mission);
            } else if (obj instanceof NavigationSystem) {
                navigationSystem = (NavigationSystem) obj;
                navigationSystem.setMission(mission);
            }
        }
        // Ensure all systems are initialized
        assert propulsionSystem != null;assert lifeSupportSystem != null;assert navigationSystem != null;

        // Calculate total energy consumption
        double wasted_energy= propulsionSystem.getEnergy_consumption()+lifeSupportSystem.getEnergy_consumption()+navigationSystem.getEnergy_consumption();

        System.out.println("All systems active");
        //  executes  mission
        if (propulsionSystem.hasEnoughFuel(navigationSystem) && lifeSupportSystem.checkCabinConditions() && (totalEnergyLevel>=wasted_energy)) {
            System.out.println("Spacecraft Status: " + spacecraftName);
            assert mission != null; // added those assert statements cuz my ide keeps giving me warnings.
            mission.start();

        } else {
            if (!propulsionSystem.hasEnoughFuel(navigationSystem)) {
                System.out.println(propulsionSystem.getProblem());
            } else if (!lifeSupportSystem.checkCabinConditions()) {
                System.out.println(lifeSupportSystem.getProblem());
            } else if (totalEnergyLevel<wasted_energy) {
                System.out.println("The total energy is not enough!");
                lifeSupportSystem.setisActive(false);
                propulsionSystem.setisActive(false);
                navigationSystem.setisActive(false);
            }
        }
        // Display system statuses
        System.out.println(propulsionSystem);
        System.out.println(lifeSupportSystem);
        System.out.println(navigationSystem);

        // Finish the mission
        assert mission != null;
        mission.finish_mission(lifeSupportSystem.getisActive() || propulsionSystem.getisActive()); //

    }

    /**
     * Reads the input file to initialize spacecraft systems and missions.
     * The input file should contain lines describing different systems or a mission.
     * Supported system types include:
     *     propulSys: Propulsion system
     *     supportSys: Life support system
     *     navSys: Navigation system
     *     exploreMission, supplyMission, satelliteMission: Mission types
     *
     * @param input The path to the input file.
     * @return A list of initialized spacecraft systems and missions.
     */
    public static ArrayList<Object> readTxt(String input) {
        ArrayList<Object> systems = new ArrayList<>();
        Mission mission;
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "propulSys" -> systems.add(new PropulsionSystem(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), null));// Mission to be assigned later.
                    case "supportSys" -> systems.add(new LifeSupportSystem(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), null));   // Mission to be assigned later.
                    case "navSys" -> systems.add(new NavigationSystem(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), null)); //Mission to be assigned later.
                    case "exploreMission", "supplyMission", "satelliteMission" -> {
                        switch (parts[0]) {
                            case "exploreMission":
                            case "supplyMission":
                            case "satelliteMission":
                                mission = new Mission(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), parts[0]);
                                systems.add(mission);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown mission type: " + parts[0]);
                        }
                    }
                }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            return systems;

        }
    }
