package org.example.carpoolingLogic;

import org.example.destination.Destination;
import org.example.individuals.Driver;
import org.example.individuals.Passenger;
import org.example.individuals.Person;
import org.example.utility.Utility;
import org.jgrapht.Graph;
import org.jgrapht.alg.matching.HopcroftKarpMaximumCardinalityBipartiteMatching;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Allocates passengers to drivers using the Hopcroft algorithm and also returns a set of people who represent a maximum cardinality set
 */
public class Allocator {

    /**
     * Represents the bipartite graph for allocating passengers to drivers
     */
    private final Graph<Person, DefaultEdge> bipartiteGraph;

    /**
     * The carpooling instance associated with the allocator
     * */
    private final Carpooling carpooling;

    /**
     * Logger for logging information
     * */
    private static final Logger logger = Logger.getLogger(Allocator.class.getName());

    /**
     * Constructs an Allocator object with the given data about drivers, passengers and destinations from the carpooling problem
     *
     * @param problem the Carpooling problem instance to be allocated
     */
    public Allocator(Carpooling problem) {
        this.carpooling = problem;
        this.bipartiteGraph = new SimpleGraph<>(DefaultEdge.class);
        toBipartiteGraph();
    }

    /**
     * Performs allocation using the JGraphT Hopcroft-Karp algorithm and prints the results after comparing them with the greedy algorithm
     * It uses a simple bipartite graph which has connections between it's vertices such as if a passgener is on a driver's way it can be picked up
     */
    public void jGraphHopcroftKarpAllocator() {

        StringBuilder messageToBeDisplayed = new StringBuilder();

        HopcroftKarpMaximumCardinalityBipartiteMatching<Person, DefaultEdge> hopcroftKarpMatching =
                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(this.bipartiteGraph,
                        new HashSet<>(carpooling.getDrivers()), new HashSet<>(carpooling.getPassengers()));

        int numberOfConnectionsHopcroft = hopcroftKarpMatching.getMatching().getEdges().size();

        carpooling.greedyAssignPassengers();
        int numberOfConnectionsGreedy = carpooling.getNumberOfAssociations();

        messageToBeDisplayed.append("Number of passengers assigned to drivers with Hopcroft: ").append(numberOfConnectionsHopcroft);
        printAndRemove(messageToBeDisplayed);
        messageToBeDisplayed.append("Number of passengers assigned to drivers with the Greedy Algorithm: ").append(numberOfConnectionsGreedy);
        printAndRemove(messageToBeDisplayed);

        printResults(messageToBeDisplayed, numberOfConnectionsHopcroft, numberOfConnectionsGreedy);
    }

    private void printAndRemove(StringBuilder messageToBeDisplayed){
        logger.log(Level.INFO, Utility.textColoring(messageToBeDisplayed.toString(), Utility.ansiEscapeCodes.GREEN));
        messageToBeDisplayed.delete(0, messageToBeDisplayed.length());
    }

    private void printResults(StringBuilder messageToBeDisplayed, int numberOfConnectionsHopcroft, int numberOfConnectionsGreedy){
        messageToBeDisplayed.append("Compare the results of the two Algorithms\n");

        if (numberOfConnectionsHopcroft < numberOfConnectionsGreedy) {
            messageToBeDisplayed.append("The Greedy algorithm has found a larger number of matchings");
        } else if (numberOfConnectionsHopcroft > numberOfConnectionsGreedy) {
            messageToBeDisplayed.append("The Hopcroft algorithm has found a larger number of matchings");
        } else {
            messageToBeDisplayed.append("We've got the same results for both of the algorithms we have ran");
        }

        logger.log(Level.INFO, Utility.textColoring(messageToBeDisplayed.toString(), Utility.ansiEscapeCodes.GREEN));
    }

    /**
     * Converts the carpooling problem into a bipartite graph which is used for allocating the passengers
     */
    private void toBipartiteGraph() {
        Set<Driver> drivers = new HashSet<>(carpooling.getDrivers());
        Set<Passenger> passengers = carpooling.getPassengers();

        drivers.stream()
                .map(p -> (Person) p)
                .forEach(bipartiteGraph::addVertex);

        passengers.stream()
                .map(p -> (Person) p)
                .forEach(bipartiteGraph::addVertex);

        for (Driver driver : drivers) {
            for (Passenger passenger : passengers) {
                if (driver.getCourse().contains(passenger.getDestination())) {
                    bipartiteGraph.addEdge((Person) driver,(Person) passenger);
                }
            }
        }
    }

    /**
     * It returns a maximum cardinality set of persons(both drivers and passengers)
     *
     * @return the set of unique people involved in the maximum cardinality allocation
     */
    public Set<Person> maxCardSet() {
        Set<Person> uniquePeople = new HashSet<>();
        Set<Destination> visitedDestinations = new HashSet<>();
        Set<Person> listOfPeople = Stream.concat(carpooling.getPassengers().stream(), carpooling.getDrivers().stream()).collect(Collectors.toSet());

        for (Person person : listOfPeople) {
            if (!visitedDestinations.contains(person.getDestination())) {
                uniquePeople.add(person);
                visitedDestinations.add(person.getDestination());
            }
        }

        return uniquePeople;
    }
}



