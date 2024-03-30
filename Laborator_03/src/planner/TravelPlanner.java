package src.planner;

import java.util.List;
import java.util.Random;
import java.util.Set;

import src.attractions.Church;
import src.attractions.Concert;
import src.attractions.Statue;
import utility.Utility;
import java.util.logging.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents a Travel Planner that computes a plan(not the optimal one) for visiting attractions during a trip.
 */
public class TravelPlanner {

    /** The list of nodes representing attractions. */
    private List<Node> nodes;

    /** The starting date of the trip. */
    private LocalDate startingDate;

    /** The ending date of the trip. */
    private LocalDate endingDate;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(TravelPlanner.class.getName());
    
    /**
     * Constructs a TravelPlanner with the specified starting date and ending date of the trip.
     * 
     * @param startingDate the starting date of the trip
     * @param endingDate the ending date of the trip
     */
    public TravelPlanner(LocalDate startingDate, LocalDate endingDate){
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.nodes = new ArrayList<>();
    }

    /**
     * Constructs a TravelPlanner with an empty list of nodes.
     */
    public TravelPlanner() {
        this.nodes = new ArrayList<>();
    }

    /**
     * Computes the best plan for visiting attractions during the trip.
     */
    public void computePlan(){
        addNodesConnections();
        greedyColoring();

        for (Node node : nodes) {
            if(node.getColor() != null)
                logger.log(Level.INFO, Utility.textColoring(node.getAttraction().getName() + node.getColor().toString(), Utility.ansiEscapeCodes.GREEN));
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    /**
     * Creates connections between nodes based on their attraction type and the data at which they are taking place
     */
    private void addNodesConnections(){
        for (Node node : nodes) {
            List<Node> nodeConnections = new ArrayList<>();

            for (Node otherNode : nodes) {

                if (otherNode == node) {
                    continue;
                }

                if (((node.getAttraction() instanceof Church && otherNode.getAttraction() instanceof Church) || (node.getAttraction() instanceof Concert && otherNode.getAttraction() instanceof Concert) || (node.getAttraction() instanceof Statue && otherNode.getAttraction() instanceof Statue)) && haveSameDate(node.getDates() , otherNode.getDates())){
                    nodeConnections.add(otherNode);
                }
            }

            node.setConnections(nodeConnections);
        }
    }

    /**
     * Checks if two nodes have common dates.
     * 
     * @param nodeDates the set of dates for the first node
     * @param otherNodeDates the set of dates for the second node
     * @return true if the nodes have common dates, otherwise false
     */
    private boolean haveSameDate(Set<LocalDate> nodeDates, Set<LocalDate> otherNodeDates) {
        for (LocalDate localDate : nodeDates) {
            if(otherNodeDates.contains(localDate))
                return true;
        }

        return false;
    }

    /**
     * Colors the nodes using a greedy algorithm.
     */
    private void greedyColoring() {
        for (Node node : nodes) {
            LocalDate colorDate = startingDate;

            List<Node> connectedNodes = node.getConnections();

            while(colorDate.isBefore(endingDate)){

                while (isDateUsed(connectedNodes, colorDate)) {
                    colorDate = colorDate.plusDays(1);
                }
                if(node.getDates().contains(colorDate)){
                    
                    node.setColor(colorDate);
                    break;
                }
                colorDate = colorDate.plusDays(1);
            }
        }
    }
    
    /**
     * Checks if a date is already used by connected nodes.
     * 
     * @param connectedNodes the list of connected nodes
     * @param date the date to be checked
     * @return true if the date is used by connected nodes, otherwise false
     */
    private boolean isDateUsed(List<Node> connectedNodes, LocalDate date) {
        for (Node connectedNode : connectedNodes) {
            if (connectedNode.getColor() == null)
                return false;
            if (connectedNode.getColor().equals(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sorts nodes after the number of connections and colors them accordingly.(warshall powell)
     */
    private void sortedAfterNumberOfConnections(){
        Collections.sort(nodes, Comparator.comparingInt(Node::getDegree));

        Set<Node> uncoloredNodes = nodes.stream().collect(Collectors.toSet());

        for (LocalDate colorDate = startingDate ; colorDate.isBefore(endingDate); colorDate = colorDate.plusDays(1)) {
            for (Node node : uncoloredNodes) {
                boolean colorIsUsed = false;
                if(node.getDates().contains(colorDate)){
                    for (Node node2 : node.getConnections()) {
                        if(node2.getColor().isEqual(colorDate)){
                            colorIsUsed = true;
                            break;
                        }
                    }
                    if(colorIsUsed)
                        break;
                    else{
                        node.setColor(colorDate);
                        uncoloredNodes.remove(node);
                    }
                }
            }
        }
    }

    /**
     * Sorts nodes after the number of connections and colors them with random values from the trip duration
     */
    private void randomAllocator(){

        Collections.sort(nodes, Comparator.comparingInt(Node::getDegree));

        for (Node node : nodes) {
            LocalDate randomDate = generateRandomDate(startingDate, endingDate);
            for (Node node2 : node.getConnections()) {
                if(!node2.getColor().isEqual(randomDate)){
                    node.setColor(randomDate);
                }
            }
        }

    }

    /**
     *  Generates a random date between the startDate and endDate
     * @param startDate the startDate of the Trip
     * @param endDate the endDate of the Trip
     * @return a random date between the startDate and endDate
     */
    private static LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        Random random = new Random();

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay));

        return LocalDate.ofEpochDay(randomEpochDay);
    }

    /**
     * Deletes all the nodes from the trip planner
     */
    public void clearNodes() {
        nodes.clear();
    }
}
