package org.example.destination;

import java.util.HashSet;
import java.util.Set;
public class Destination implements Cloneable {


    private String name;

    private final Set<Destination> adjacentDestinations;

    public Destination(String name) {
        this.name = name;
        this.adjacentDestinations = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Destination> getAdjacentDestinations() {
        return adjacentDestinations;
    }

    public void addAdjacentDestinations(Destination adjacentDestination) {
        this.adjacentDestinations.add(adjacentDestination);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        message.append("Destination{" + "name='").append(name).append('\'').append(", adjacentDestinations=[ ");

        adjacentDestinations.forEach(destination -> message.append(destination.getName()).append(","));

        message.append("]}");

        return message.toString();
    }

    @Override
    public Destination clone() {
        try {
            return (Destination) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
