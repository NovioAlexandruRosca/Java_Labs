package src.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.*;
import src.base.Client;
import src.base.Depot;
import src.base.Vehicle;
import utility.Utility;
import java.time.LocalTime;

/**
 * Solution class represents the solution for allocating clients to vehicles.
 * It contains 2 methods for allocating clients to vehicles and 
 * other helper functions to complete that task
 */
public class Solution {
    /** A list of all the clients that are needed in the solution */
    private ArrayList<Client> clients;

    /** A list of all the depots that are needed in the solution */
    private ArrayList<Depot> depots;

    /** A list of all the vehicles that are needed in the solution */
    private ArrayList<Vehicle> vehicles;

    /** An arraylist of all the costs from all the depots to all the clients */
    private ArrayList<ArrayList<Integer>> transportationCostDepotToClient;

    /** An arraylist of all the costs from all the clients to all other clients */
    private ArrayList<ArrayList<Integer>> transportationCostClientToClient;

    /** A matrix of all costs combined */
    private int[][] costMatrix;

    /** Used for generating random values using the methodes defined in the Problem class*/
    private Random random = new Random();

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Solution.class.getName());

    public Solution(ArrayList<Client> clients, ArrayList<Depot> depots, ArrayList<Vehicle> vehicles) {
        this.clients = clients;
        this.depots = depots;
        this.vehicles = vehicles;
    }

    /**
     * Generates the transportation cost graph for clients and depots.
     * Calculates transportation costs between depots and clients.
     * Populates the cost matrix for the graph.
     */
    private void generateGraphCosts(){
        transportationCostClientToClient = Problem.generateTransportationCostsClientToClient(clients, random);
        transportationCostDepotToClient = Problem.generateTransportationCostsDepotToClient(depots, clients, random);

        costMatrix = new int[depots.size() + clients.size()][depots.size() + clients.size()];

        for(int i = 0 ; i < depots.size() ; i++){
            
            for(int j = 0 ; j < depots.size() ; j++){
                costMatrix[i][j] = 0;
            }
            for(int j = depots.size() ; j < clients.size() + depots.size() ; j++){
                costMatrix[i][j] = transportationCostDepotToClient.get(i).get(j - depots.size());
            }
        }

        for(int i = depots.size() ; i < depots.size() + clients.size() ; i++){
            for(int j = 0 ; j < depots.size() ; j++){
                costMatrix[i][j] = costMatrix[j][i];
            }
            for(int j = depots.size() ; j < clients.size() + depots.size() ; j++){
                costMatrix[i][j] = transportationCostClientToClient.get(i - depots.size()).get(j - depots.size());
            }
        }

    }


    /**
     * Allocates clients to vehicles using 2 different alogorithms
     */
    public void allocateClientsToVehicles() {
        sortClientsAfterTimeIntervals();

        generateGraphCosts();

        ArrayList<ArrayList<Client>> vehicleTours = new ArrayList<>();
        for (@SuppressWarnings("unused") Vehicle vehicle : vehicles) {
            vehicleTours.add(new ArrayList<>());
        }  

        sortedClientVehicleAllocation(vehicleTours);
        // dijkstraClientAllocator(vehicleTours);
        displayTours(vehicleTours);

    }

    /**
     * Allocates clients to vehicles using the sorted client vehicle allocation method.
     * Clients are allocated to vehicles based on minimum additional cost.
     * It represents the dynamical way of solving the problem
     *
     * @param vehicleTours An ArrayList of ArrayLists representing vehicle tours.
     */
    private void sortedClientVehicleAllocation(ArrayList<ArrayList<Client>> vehicleTours){
        for (Client client : clients) {
            int minCost = Integer.MAX_VALUE;
            Vehicle selectedVehicle = null;

            for (Vehicle vehicle : vehicles) {
                if(vehicle.getNumberOfClients() > 0){
                    int cost = calculateAdditionalCost(vehicle, client);
                    if (cost < minCost) {
                        minCost = cost;
                        selectedVehicle = vehicle;
                    }
                }
            }

            if(selectedVehicle != null){
                addToVehicleTour(vehicleTours, selectedVehicle, client);
                updateVehicleData(selectedVehicle, client);
                updateClientData(client);
            }
        }
    }

    /**
     * Adds a client to the tour of a selected vehicle.
     *
     * @param vehicleTours   An ArrayList of ArrayLists representing vehicle tours.
     * @param selectedVehicle The selected vehicle to add the client to.
     * @param client          The client to be added to the vehicle tour.
     */
    private void addToVehicleTour(ArrayList<ArrayList<Client>> vehicleTours, Vehicle selectedVehicle, Client client){
        vehicleTours.get(vehicles.indexOf(selectedVehicle)).add(client);
    }

    /**
     * It marks that a specific vehicle has stopped at a specific client, that it's not longer in it's depot
     * and that it can move to the number of clients it was able to move before - 1
     * @param selectedVehicle the selected vehicle
     * @param client  the client that was visited
     */
    private void updateVehicleData(Vehicle selectedVehicle, Client client){
        selectedVehicle.setNumberOfClients(selectedVehicle.getNumberOfClients() - 1);
        selectedVehicle.setClientStop(client);
        selectedVehicle.setInDepot(false);
    }

    /**
     * It marks the client as being visited
     * @param client  the client that was visited
     */
    private void updateClientData(Client client){
        client.setVisited(true);
    }

    private void displayTours(ArrayList<ArrayList<Client>> vehicleTours){
        int i = -1;
        for (ArrayList<Client> arrayList : vehicleTours) {
            i++;
            Vehicle vehicle = vehicles.get(i);
            StringBuilder message = new StringBuilder().append("Tour for Vehicle ").append(vehicle.getId()).append(": ");
            for (Client client : arrayList) {
                message.append(client).append(" ");
            }
            logger.log(Level.INFO, Utility.textColoring(message.toString(), Utility.ansiEscapeCodes.GREEN));
        }
    }

    /**
     * Sorts the clients based on the time interval they are opened at
     * It uses a comparator, to test if the time at which a clients starts happens before or after
     * another clients starts it's work
     */
    private void sortClientsAfterTimeIntervals() {
        Comparator<Client> comparator = new Comparator<Client>(){
            @Override
            public int compare(Client c1, Client c2) {

                LocalTime time1 = LocalTime.parse(c1.getTimeInterval()[0]);
                LocalTime time2 = LocalTime.parse(c2.getTimeInterval()[0]);

                return time1.compareTo(time2);
            }
        };
        Collections.sort(clients, comparator);
    }

    /**
     * Calculates the cost of moving from a client/depot to another client
     * @param vehicle  the vehicle that is going to move
     * @param client  the client at which it wants to move
     * @return The cost of moving from a client/depot to another client
     */
    private int calculateAdditionalCost(Vehicle vehicle, Client client) {
        
        int costOfTransportation;
        int indexOfClient = clients.indexOf(client);
        int indexOfDepot = depots.indexOf(vehicle.getDepotOfOrigin());

        if(vehicle.isInDepot()){
            costOfTransportation = transportationCostDepotToClient.get(indexOfDepot).get(indexOfClient);
        }
        else{
            costOfTransportation = transportationCostClientToClient.get(indexOfClient).get(indexOfClient);
        }

        return costOfTransportation;
    }

    /**
     * Returns a value represeting the index at which the minimum value of an unvisted vertice is
     * @param distance a list of distances between nodes
     * @param visitedNode a list of all the nodes visted by the dijkstra algorightm
     * @return A value represeting the index at which the minimum value of an unvisted vertice is
     */
    private int minimumDistance(int[] distance, boolean[] visitedNode) {
        int minIndex = -1;
        int min = Integer.MAX_VALUE;

        for (int v = 0; v < distance.length; v++)
            if (!visitedNode[v] && distance[v] <= min) {
                min = distance[v];
                minIndex = v;
            }

        visitedNode[minIndex] = true;
        return minIndex;
    }

    /**
     * Returns a list with maximum values representing that the vertices havent been visited and for a
     * given depot mark the distance as being 0
     * @param sizeOfGrid the size of the graph  
     * @param indexOfDepot the index of the given depot
     * @return  a list with maximum values represeting that the vertices havent been visited
     */
    private int[] initializeDistances(int sizeOfGrid, int indexOfDepot){
        int[] distances = new int[sizeOfGrid];

        for (int i = 0 ; i < sizeOfGrid ; i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        distances[indexOfDepot] = 0;

        return distances;
    }

    /**
     * Retruns a list of false values that indicates that no vertices have been visited
     * @param sizeOfGrid  the size of the graph
     * @return  a list of false values indicating that no vertices have been visited
     */
    private boolean[] initialiseVisitedNodes(int sizeOfGrid){
        boolean[] visitedNode = new boolean[sizeOfGrid];

        for (int i = 0 ; i < sizeOfGrid ; i++) {
            visitedNode[i] = false;
        }

        return visitedNode;
    }

    /**
     * Implements the Dijkstra algorithm to find the minimum distance from a depot to all clients.
     *
     * @param indexOfDepot The index of the depot for which Dijkstra's algorithm is applied.
     * @return An array containing the minimum distances from the depot to all clients.
     */
    private int[] dijkstraAlgorithm(int indexOfDepot) {
        int sizeOfGrid = clients.size() + depots.size();
        int[] distances = initializeDistances(sizeOfGrid, indexOfDepot);
        boolean[] visitedNode = initialiseVisitedNodes(sizeOfGrid);

        for (int count = 0; count < sizeOfGrid - 1; count++) {
            int u = minimumDistance(distances, visitedNode);

            for (int v = 0; v < sizeOfGrid; v++) {
                if (!visitedNode[v] && costMatrix[u][v] != 0 && distances[u] != Integer.MAX_VALUE 
                && distances[u] + costMatrix[u][v] < distances[v]) {
                    distances[v] = distances[u] + costMatrix[u][v];
                }
            }
        }

        return distances;
    }

    /**
     * Updates the working hours of a vehicle after visiting a client.
     *
     * @param closestClient The closest client visited by the vehicle.
     * @return A string representing the updated working hours of the vehicle.
     */
    private String updateWorkingHours(Client closestClient){
        return closestClient.getTimeInterval()[1];
    }

    /**
     * Updates the data after visiting a client by adding it to the vehicle tour and updating 
     * related information.
     *
     * @param vehicleTours   An ArrayList of ArrayLists representing vehicle tours.
     * @param selectedVehicle The selected vehicle that visits the closest client.
     * @param closestClient  The closest client visited by the vehicle.
     * @return A string representing the updated working hours after visiting the client.
     */
    private String updateData(ArrayList<ArrayList<Client>> vehicleTours, Vehicle selectedVehicle, Client closestClient){
        addToVehicleTour(vehicleTours, selectedVehicle, closestClient);
        updateVehicleData(selectedVehicle, closestClient);
        updateClientData(closestClient);
        return updateWorkingHours(closestClient);
    }

    /**
     * Allocates clients to vehicles using the Dijkstra algorithm.
     * Vehicles start from depots and visit clients based on minimum distance.
     *
     * @param vehicleTours An ArrayList of ArrayLists representing vehicle tours.
     */
    private void dijkstraClientAllocator(ArrayList<ArrayList<Client>> vehicleTours) {

        for (Depot depot : depots){ 
            String workingVehicleTime;
            int indexOfDepot = depots.indexOf(depot);
            int[] distancesFromVehicle = dijkstraAlgorithm(indexOfDepot);

            for (Vehicle selectedVehicle : depot.getVehicles()) {
                workingVehicleTime = "08:00";

                while (true) {
                    Client closestClient = null;
                    int minCost = Integer.MAX_VALUE;

                    for (Client client : clients) {
                        if (!client.isVisited() && selectedVehicle.getNumberOfClients() > 0 && client.canBeVisited(workingVehicleTime)) {
                            int costToClient = distancesFromVehicle[clients.indexOf(client)];

                            if (costToClient < minCost) {
                                minCost = costToClient;
                                closestClient = client;
                            }
                        }
                    }

                    if (minCost != Integer.MAX_VALUE && closestClient != null && selectedVehicle.getNumberOfClients() > 0) {
                        workingVehicleTime = updateData(vehicleTours, selectedVehicle, closestClient);
                    } else
                        break;
                }
            }
        }
    }
}
