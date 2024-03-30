package org.example.individuals;

import org.example.destination.Destination;

/**
 * Represents a passenger, who is a person traveling to a destination from a starting location.
 */
public class Passenger extends Person {

    private Boolean hasArrived;

    /**
     * Constructs a passenger with the specified name, age, destination, and starting location.
     *
     * @param name the name of the passenger
     * @param age the age of the passenger
     * @param destination the destination of the passenger
     * @param startingLocation the starting location of the passenger
     */
    public Passenger(String name, Integer age, Destination destination, Destination startingLocation) {
        super(name, age, destination, startingLocation);
        hasArrived = false;
    }

    public Boolean getHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(Boolean hasArrived) {
        this.hasArrived = hasArrived;
    }
}