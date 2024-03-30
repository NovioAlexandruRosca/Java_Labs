package org.example.individuals;


import org.example.destination.Destination;

/**
 * Represents a driver, who is a person traveling from a starting location to a destination.
 */
public class Driver extends Person {

    private Integer capacity;

    private Passenger passenger;

    /**
     * Constructs a driver with the specified name, age, destination, and starting location.
     *
     * @param name the name of the driver
     * @param age the age of the driver
     * @param destination the destination of the driver
     * @param startingLocation the starting location of the driver
     */
    public Driver(String name, Integer age, Destination destination, Destination startingLocation) {
        super(name, age, destination, startingLocation);
        capacity = 1;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}