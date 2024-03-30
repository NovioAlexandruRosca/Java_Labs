package org.example.carpoolingLogic;

import com.github.javafaker.Faker;
import org.example.destination.Destination;
import org.example.individuals.Driver;
import org.example.individuals.Passenger;
import org.example.individuals.Person;
import org.example.utility.Utility;
import java.util.*;
import java.util.stream.Collectors;
import java.util.logging.*;

/**
 * Represents the logic for managing the allocation of passengers to drivers
 * It also has the logic for generating random drivers and passengers
 */
public class Carpooling {

    /**
     * Logger for logging information.
     */
    private static final Logger logger = Logger.getLogger(Carpooling.class.getName());

    /** Set of drivers participating in carpooling */
    private final Set<Driver> drivers;

    /** Set of passengers participating in carpooling */
    private final Set<Passenger> passengers;

    /** List of destinations available for carpooling */
    private final List<Destination> destinations;

    /** Number of associations between drivers and passengers */
    private int numberOfAssociations;

    /**
     * Constructs a Carpooling object with the specified number of people, destinations, and probability of connection
     *
     * @param numberOfPeople the total number of people involved (drivers and passengers)
     * @param destinations the list of destinations available for the carpooling
     * @param probabilityOfConnection the probability of a passenger being connected to a destination with a driver
     */
    public Carpooling(Integer numberOfPeople, List<Destination> destinations, Double probabilityOfConnection) {
        this.destinations = destinations;

        this.drivers = generateRandomDrivers(destinations, numberOfPeople / 2);
        this.passengers = generateRandomPassengers(destinations, numberOfPeople / 2, probabilityOfConnection);
        numberOfAssociations = 0;
    }

    /**
     * Retrieves the set of destinations passed by all drivers
     *
     * @return the set of destinations passed by drivers
     */
    public Set<Destination> destinationsPassedByDrivers() {
        return drivers.stream()
                .flatMap(driver -> driver.getCourse().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Groups people (both drivers and passengers) by their respective destinations
     *
     * @return a map where destinations are keys and associated people are values
     */
    public Map<Destination, List<Person>> peopleByDestination() {
        List<Person> allPeople = new ArrayList<>();
        allPeople.addAll(drivers);
        allPeople.addAll(passengers);

        return allPeople.stream()
                .collect(Collectors.groupingBy(Person::getDestination));
    }

    /**
     * Generates a set of random drivers
     *
     * @param destinations the list of destinations
     * @param numberOfPeople the number of passengers to generate
     * @return the set of generated random drivers
     */
    private Set<Driver> generateRandomDrivers(List<Destination> destinations, Integer numberOfPeople){
        Set<Driver> listOfDrivers = new HashSet<>();

        Faker faker = new Faker();
        Random random = new Random();

        for (int i = 0; i < numberOfPeople; i++) {

            String name = faker.name().fullName();
            int age = random.nextInt(50) + 20;
            Destination randomDestination = destinations.get(random.nextInt(destinations.size()));
            Destination randomStartingLocation = destinations.get(random.nextInt(destinations.size()));

            listOfDrivers.add(new Driver(name, age, randomDestination , randomStartingLocation));

        }

        return listOfDrivers;
    }

    /**
     * Generates a set of random passengers based on the provided destinations, number of people, and probability of connection
     *
     * @param destinations the list of destinations available for the carpooling
     * @param numberOfPeople the number of passengers to generate
     * @param probabilityOfConnection the probability of a passenger being connected to a destination with a driver
     * @return the set of generated random passengers
     */
    private Set<Passenger> generateRandomPassengers(List<Destination> destinations, Integer numberOfPeople, Double probabilityOfConnection){
        Set<Passenger> listOfDrivers = new HashSet<>();

        Faker faker = new Faker();
        Random random = new Random();

        List<Destination> destinationsWithoutDrivers = destinations.stream()
                                                                   .filter(p -> !destinationsPassedByDrivers().contains(p))
                                                                   .toList();

        List<Destination> destinationsWithDrivers = destinations.stream().toList();

        for (int i = 0; i < numberOfPeople; i++) {

            String name = faker.name().fullName();
            int age = random.nextInt(50) + 20;

            Destination randomStartingLocation = destinations.get(random.nextInt(destinations.size()));
            Destination randomDestination;

            int randomValue = random.nextInt(10);
            if(probabilityOfConnection * 10 == randomValue){
                randomDestination = destinationsWithDrivers.get(random.nextInt(destinationsWithDrivers.size()));
            }
            else{
                randomDestination = destinationsWithoutDrivers.get(random.nextInt(destinationsWithoutDrivers.size()));
            }

            listOfDrivers.add(new Passenger(name, age, randomDestination , randomStartingLocation));

        }

        return listOfDrivers;
    }

    /**
     * Assigns passengers to drivers greedily based on their destinations and the course of the driver
     * it firstly sorts the passengers and drivers by destination
     */
    public void greedyAssignPassengers(){

        Comparator<Destination> destinationComparator = Comparator.comparing(Destination::getName);

        List<Driver> sortedDriver = new ArrayList<>(drivers);
        sortedDriver.sort(Comparator.comparing(Driver::getDestination, destinationComparator));
        List<Passenger> sortedPassengers = new ArrayList<>(passengers);
        sortedPassengers.sort(Comparator.comparing(Passenger::getDestination, destinationComparator));

        for (var driver : sortedDriver) {
            List<Destination> driverStops = new ArrayList<>(driver.getCourse());

            for (var stop: driver.getCourse()) {

                if(driver.getCapacity() == 0 && stop.getName().equals(driver.getPassenger().getDestination().getName())){
                    driver.setCapacity(1);

                    StringBuilder messageToBePrinted = new StringBuilder().append("The passenger ").append(driver.getPassenger().getName()).append(" was transported by ").append(driver.getName());

                    logger.log(Level.INFO, Utility.textColoring(messageToBePrinted.toString(), Utility.ansiEscapeCodes.GREEN));

                    driver.setPassenger(null);
                }
                driverStops.remove(stop);
                for (var passenger: sortedPassengers) {
                    if(passenger.getStartingLocation().getName().equals(stop.getName())
                            && driverStops.contains(passenger.getDestination())
                            && driver.getCapacity() > 0 && !passenger.getHasArrived()){
                            driver.setCapacity(0);
                            driver.setPassenger(passenger);
                            passenger.setHasArrived(true);
                            this.numberOfAssociations++;

                    }
                }
            }
        }
    }

    public void printDrivers(){
        logger.log(Level.INFO, Utility.textColoring("\nDrivers: ", Utility.ansiEscapeCodes.GREEN));
        drivers.forEach(d -> logger.log(Level.INFO, Utility.textColoring(d.toString(), Utility.ansiEscapeCodes.GREEN)));
    }

    public void printPassengers(){
        logger.log(Level.INFO, Utility.textColoring("\nPassengers: ", Utility.ansiEscapeCodes.GREEN));
        passengers.forEach(p -> logger.log(Level.INFO, Utility.textColoring(p.toString(), Utility.ansiEscapeCodes.GREEN)));
    }

    public void printDestinations(){
        logger.log(Level.INFO, Utility.textColoring("\nDestinations: ", Utility.ansiEscapeCodes.GREEN));
        destinations.forEach(destination -> logger.log(Level.INFO, Utility.textColoring(destination.toString(), Utility.ansiEscapeCodes.GREEN)));
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public int getNumberOfAssociations() {
        return numberOfAssociations;
    }
}
