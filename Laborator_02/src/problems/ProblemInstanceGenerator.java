package src.problems;
import java.util.Random;
import java.util.ArrayList;
import java.util.logging.*;

import src.base.Client;
import src.base.Depot;
import src.base.Vehicle;
import src.derived.Truck;
import utility.ClientType;
import utility.Utility;

/**
 * ProblemInstanceGenerator class generates large problem instances and tests it's efficency
 */
public class ProblemInstanceGenerator {

    /** variable used for random number generation */
    private static Random random = new Random();

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(ProblemInstanceGenerator.class.getName());

    /**
     * Main method to generate problem instances and measure the time taken.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        
        for (int i = 0; i < 1000; i++) {
            generateProblemInstance();
        }

        StringBuilder messages = new StringBuilder().append("Time: ").append((System.nanoTime() - startTime) / 1_000_000).append(" miliseconds");
        logger.log(Level.INFO, Utility.textColoring(messages.toString(), Utility.ansiEscapeCodes.GREEN));
    }

    /**
     * Generates a problem instance with clients, depots, and vehicles and solves it.
     */
    private static void generateProblemInstance() {
        ArrayList<Client> clients = generateClients();
        ArrayList<Depot> depots = generateDepots();
        ArrayList<Vehicle> vehicles = generateVehicles(depots);

        Problem problem = new Problem(clients, depots, vehicles);
        problem.solve();
    }

    /**
     * Generates a random time interval in the format "HH:MM".
     * @return A string array representing the time interval.
     */
    private static String[] generateTimeIntervals(){
        int startHour = random.nextInt(23);
        int startMinute = random.nextInt(60);

        int endHour = random.nextInt(23 - startHour + 1) + startHour;
        int endMinute = random.nextInt(59 - startMinute + 1) + startMinute;

        String startTime = String.format("%02d:%02d", startHour, startMinute);
        String endTime = String.format("%02d:%02d", endHour, endMinute);

        return new String[]{startTime, endTime};
    }

    /**
     * Generates a random word of length 5.
     * @return A randomly generated word.
     */
    private static String generateWord(){
        int length = 5;
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int asciiValue = random.nextInt(26) + 97;
            randomString.append((char) asciiValue);
        }

        return randomString.toString();
    }

    /**
     * Generates a list of random clients.
     * @return An ArrayList containing the generated clients.
     */
    private static ArrayList<Client> generateClients() {    
        ArrayList<Client> clients = new ArrayList<>();

        int randomNumber = random.nextInt(10);

        for (int i = 0; i < randomNumber; i++) {
            String[] timeInterval = generateTimeIntervals();
            String name = generateWord();
            Client client = new Client(ClientType.REGULAR, name, timeInterval);
            clients.add(client);
        }

        return clients;
    }

    /**
     * Generates a list of random depots.
     * @return An ArrayList containing the generated depots.
     */
    private static ArrayList<Depot> generateDepots() {
        ArrayList<Depot> depots = new ArrayList<>();

        int randomNumber = random.nextInt(5) + 1;

        for (int i = 0; i < randomNumber; i++) {
            String name = generateWord();
            Depot depot = new Depot(name, new Vehicle[]{});
            depots.add(depot);
        }

        return depots;
    }

    /**
     * Generates a list of random vehicles associated with depots.
     * @param depots An ArrayList of depots to associate vehicles with.
     * @return An ArrayList containing the generated vehicles.
     */
    private static ArrayList<Vehicle> generateVehicles(ArrayList<Depot> depots) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        int randomNumber = random.nextInt(20);

        for (int i = 0; i < randomNumber; i++) {
            int randomIndex = random.nextInt(depots.size());
            Truck truck = new Truck(depots.get(randomIndex), random.nextInt(20) + 1, random.nextInt(20) + 1);
            vehicles.add(truck);
        }

        return vehicles;
    }
}
