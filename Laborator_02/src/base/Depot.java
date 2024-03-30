// Formated + Comments + Javadoc
package src.base;

import java.util.Objects;
import utility.ServiceException;
import utility.Utility;
import java.util.logging.*;

/**
 * Represents a depot that contains vehicles
 */
public class Depot {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Depot.class.getName());

    /** value used for counting the number of objects of type Depot created */
    private static int idNumber = -1;

    /** Name of the Depot */ 
    private String name;

    /** An id to differentiate the objects created */
    private Integer id;

    /** List of vehicles that are part of the given depot */
    private Vehicle[] vehicles;

    /**
     * Constructs a Depot object with the given name and vehicles.
     * 
     * @param name     the name of the depot
     * @param vehicles an array of vehicles belonging to the depot
     */
    public Depot(String name, Vehicle[] vehicles) {
        idNumber++;
        this.id = idNumber;
        this.name = name;
        this.vehicles = vehicles;
    }

    /**
     * Retrieves the name of the depot.
     * 
     * @return the name of the depot
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the depot.
     * 
     * @param name the name of the depot
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the ID of the depot.
     * 
     * @return the ID of the depot
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retrieves the vehicles belonging to the depot.
     * 
     * @return an array of vehicles belonging to the depot
     */
    public Vehicle[] getVehicles() {
        return vehicles;
    }

    /**
     * Sets the vehicles belonging to the depot.
     * 
     * @param vehicles an array of vehicles belonging to the depot
     */
    public void setVehicles(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Adds a vehicle to the depot.
     * 
     * @param vehicle the vehicle to add to the depot
     */
    public void addVehicle(Vehicle vehicle) {
        try {
            // We check to see if the vehicle already exists in the depot
            for (int i = 0; i < vehicles.length; i++)
                if (vehicles[i].equals(vehicle)) {
                    throw new ServiceException("You can't add the same vehicle in the same depot");
                }

        } catch (ServiceException e) {
            String message = Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
            System.exit(0);
        }

        // Make space for the newly added vehicle
        Vehicle[] newVehicles = new Vehicle[this.vehicles.length + 1];
        System.arraycopy(this.vehicles, 0, newVehicles, 0, this.vehicles.length);
        // Add the new vehicle to the list & transfer the newly created array to the original one
        newVehicles[this.vehicles.length] = vehicle;
        this.vehicles = newVehicles;
    }

    /**
     * Returns a string representation of the depot.
     * 
     * @return string representation of the depot
     */
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder().append(name).append(" ").append(id.toString()).append(" ");
        for (int i = 0; i < vehicles.length; i++) {
            message.append(vehicles[i].getId()).append(" ");
        }
        return Utility.textColoring(message.toString(), Utility.ansiEscapeCodes.GREEN);
    }

    /**
     * Checks if this depot is equal to another object.
     * 
     * @param obj the object to compare
     * @return true if the depots are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;

        Depot depotToBeCompared = (Depot) obj;

        return this.name.equals(depotToBeCompared.name) && this.id.equals(depotToBeCompared.id)
                && Objects.equals(vehicles, depotToBeCompared.vehicles);
    }

    /**
     * Returns the hash code value for the depot.
     * 
     * @return the hash code value for the depot
     */
    @Override
    public int hashCode() {
        int hash = 2;
        hash = 31 * hash + Objects.hash(id, vehicles, name);
        return hash;
    }
}