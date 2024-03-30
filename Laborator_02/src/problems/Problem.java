package src.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.*;

import src.base.Client;
import src.base.Depot;
import src.base.Vehicle;
import java.util.Random;
import utility.Utility;

/**
 * A class representing the problem of allocating clients to vehicles, 
 * it uses information about the clients, depots and vehicles
 */
public class Problem {

    /** The list of clients involved in the problem. */
    private ArrayList<Client> clients = new ArrayList<>();

    /** The list of depots involved in the problem. */
    private ArrayList<Depot> depots = new ArrayList<>();

    /** The list of vehicles involved in the problem. */
    private ArrayList<Vehicle> vehicles = new ArrayList<>();

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Problem.class.getName());

    Problem(ArrayList<Client> clients, ArrayList<Depot> depots, ArrayList<Vehicle> vehicles){
        this.clients = clients;
        this.depots = depots;
        this.vehicles = vehicles;
    }

    Problem(){}

    /**
     * Solves the problem by allocating clients to vehicles.
     */
    public void solve() {
        Solution solution = new Solution(clients, depots, vehicles);
        solution.allocateClientsToVehicles();
    }

    /**
     * Retrieves the list of vehicles involved in the problem.
     *
     * @return The list of vehicles involved in the problem.
     */
    public ArrayList<Vehicle> getVehicles() {
        ArrayList<Vehicle> newVehicles = new ArrayList<>();
        for (Depot depot : depots) {
            newVehicles.addAll(Arrays.asList(depot.getVehicles()));
        }
        return newVehicles;
    }

    /**
     * Adds a vehicle to the problem.
     *
     * @param vehicle The vehicle to add to the problem.
     */
    public void addVehicle(Vehicle vehicle) {
        if (!vehicles.contains(vehicle))
            vehicles.add(vehicle);
        else {
            String message = Utility.textColoring("You can't add the same vehicle to the list of vehicles", Utility.ansiEscapeCodes.RED);
            logger.log(Level.WARNING, message);
        }
    }

    /**
     * Adds a depot to the problem.
     *
     * @param depot The depot to add to the problem.
     */
    public void addDepot(Depot depot) {
        Vehicle[] listOfVehicles = depot.getVehicles();
        for (int i = 0; i < listOfVehicles.length; i++) {
            vehicleAlreadyInDepot(listOfVehicles[i]);
        }
        if (!depots.contains(depot))
            depots.add(depot);
        else {
            String message = Utility.textColoring("You can't add the same depot to the list of depots", Utility.ansiEscapeCodes.RED);
            logger.log(Level.WARNING, message);
        }
    }

    /**
     * Checks if a vehicle is already in a depot.
     *
     * @param vehicle The vehicle to check.
     */
    private void vehicleAlreadyInDepot(Vehicle vehicle) {
        for (Depot depot : depots) {
            Vehicle[] listOfVehicles = depot.getVehicles();
            for (int i = 0; i < listOfVehicles.length; i++)
                if (listOfVehicles[i].equals(vehicle)) {
                    String message = Utility.textColoring("You already have a depot that contains one of the vehicles from the newly added depot", Utility.ansiEscapeCodes.RED);
                    logger.log(Level.SEVERE, message);
                    System.exit(0);
                }
        }
    }

    /**
     * Adds a client to the problem.
     *
     * @param client The client to add to the problem.
     */
    public void addClient(Client client) {
        if (!clients.contains(client))
            clients.add(client);
        else {
            String message = Utility.textColoring("You can't add the same client to the list of clients", Utility.ansiEscapeCodes.RED);
            logger.log(Level.WARNING, message);
        }
    }

    /**
     * Generates transportation costs from depots to clients.
     *
     * @param depots The list of depots.
     * @param clients The list of clients.
     * @param random The random number generator.
     * @return The transportation costs from depots to clients.
     */
    protected static ArrayList<ArrayList<Integer>> generateTransportationCostsDepotToClient(ArrayList<Depot> depots, ArrayList<Client> clients, Random random) {
        ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
        for (@SuppressWarnings("unused") Depot decoy1 : depots) {
            ArrayList<Integer> column = new ArrayList<>();
            for (@SuppressWarnings("unused") Client decoy2 : clients) {
                int randomValue = random.nextInt(100);
                column.add(randomValue);
            }
            rows.add(column);
        }
        return rows;
    }

    /**
     * Generates transportation costs from client to client.
     *
     * @param clients The list of clients.
     * @param random The random number generator.
     * @return The transportation costs from client to client.
     */
    protected static ArrayList<ArrayList<Integer>> generateTransportationCostsClientToClient(ArrayList<Client> clients, Random random) {
        ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
        for (@SuppressWarnings("unused") Client decoy1 : clients) {
            ArrayList<Integer> column = new ArrayList<>();
            for (@SuppressWarnings("unused") Client decoy2 : clients) {
                int randomValue = random.nextInt(100);
                column.add(randomValue);
            }
            rows.add(column);
        }
        return rows;
    }

    /**
     * Adds a vehicle to a depot manually.
     *
     * @param depot The depot to add the vehicle to.
     * @param vehicle The vehicle to add to the depot.
     */
    public void addVehicleToDepot(Depot depot, Vehicle vehicle) {
        for (Depot depotIndex : depots) {
            if (depotIndex.equals(depot)) {
                depotIndex.addVehicle(vehicle);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (Vehicle vehicle : vehicles) {
            message.append(vehicle.toString()).append(" ");
        }
        for (Client client : clients) {
            message.append(client.toString()).append(" ");
        }
        for (Depot depot : depots) {
            message.append(depot.toString()).append(" ");
        }
        return Utility.textColoring(message.toString(), Utility.ansiEscapeCodes.GREEN);
    }
}
