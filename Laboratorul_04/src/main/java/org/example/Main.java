package org.example;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.*;
import com.github.javafaker.Faker;
import org.example.carpoolingLogic.Allocator;
import org.example.carpoolingLogic.Carpooling;
import org.example.destination.Destination;
import org.example.individuals.Driver;
import org.example.individuals.Passenger;
import org.example.individuals.Person;
import org.example.utility.Utility;

import java.util.logging.*;

/**
 * Represents the main class for the travel information application.
 */
public class Main {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Carpooling.class.getName());

    /**
     * The main method of the application. It generates random people, filters them into drivers and passengers,
     * and prints their information.
     *
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {

        List<Destination> destinations = generateRandomDestinations(800);
        generateRandomAdjacentDestinations(destinations);

        Carpooling carpooling = new Carpooling(10_000, destinations, 0.1);

        carpooling.printDrivers();
        carpooling.printPassengers();
        carpooling.printDestinations();

        carpooling.greedyAssignPassengers();


        Allocator allocate = new Allocator(carpooling);

        allocate.jGraphHopcroftKarpAllocator();
//        allocate.maxCardSet()
//                .forEach(p -> logger.log(Level.INFO, Utility.textColoring(p.getName(), Utility.ansiEscapeCodes.GREEN)));
    }

    /**
     * Generates adjacent destinations for each destination in the list.
     *
     * @param destinations the list of destinations
     */
    private static void generateRandomAdjacentDestinations(List<Destination> destinations){

        Random random = new Random();
        for (Destination destination : destinations) {

            for (Destination destination1 : destinations) {

                if(!destination1.equals(destination))
                    destination.addAdjacentDestinations(destination1);
            }
        }
    }

    /**
     * Generates a list of random destinations
     *
     * @return the list of random destinations
     */
    private static List<Destination> generateRandomDestinations(Integer numberOfDestinations){
        List<Destination> destinations = new ArrayList<>();

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < numberOfDestinations; i++) {
            String name = faker.address().city();

            Destination destination = new Destination(name);
            destinations.add(destination);

        }

        return destinations;
    }

    /**
     * Filters the list of people to extract only the drivers.
     *
     * @param listOfPeople the list of people
     * @return the list of drivers
     */
    private static List<Driver> filterDrivers(List<Person> listOfPeople){
        return listOfPeople.stream()
                .filter(p -> p instanceof Driver)
                .map(p -> (Driver) p)
                .sorted(Comparator.comparingInt(Person::getAge))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Filters the list of people to extract only the passengers.
     *
     * @param listOfPeople the list of people
     * @return the set of passengers
     */
    private static Set<Passenger> filterPassengers(List<Person> listOfPeople) {
        return listOfPeople.stream()
                .filter(p -> p instanceof Passenger)
                .map(p -> (Passenger) p)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getName))));
    }

    /**
     * Generates a list of random people (drivers and passengers).
     *
     * @return the list of random people
     */
    private static List<Person> generateRandomPeople(List<Destination> destinations, Integer numberOfPeople){
        List<Person> listOfPeople = new ArrayList<>();

        Faker faker = new Faker();

        Random random = new Random();
        for (int i = 0; i < numberOfPeople; i++) {
            String name = faker.name().fullName();
            int age = random.nextInt(50) + 20;
            Destination randomDestination = destinations.get(random.nextInt(destinations.size()));
            Destination randomStartingLocation = destinations.get(random.nextInt(destinations.size()));

            if (i < numberOfPeople / 2) {
                listOfPeople.add(new Driver(name, age, randomDestination , randomStartingLocation));
            } else {
                listOfPeople.add(new Passenger(name, age, randomDestination, randomStartingLocation));
            }
        }

        return listOfPeople;
    }
}