// Formated + Comments + Javadoc
package src.base;

import java.util.Objects;
import utility.Utility;
import java.util.logging.*;

/**
 * Represents a vehicle that can visit clients.
 */
public abstract class Vehicle {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Vehicle.class.getName());

    /** id given to each object to differentiate them */
    private static int idNumber = -1;

    /** the depot in which the vehicle stationates */
    private Depot depotOfOrigin;

    /** the id unique to every object */
    private Integer id;

    /** number of clients the vehicle can visit */
    private Integer numberOfClients;

    /** boolean value to tell if a vehicle is in it s depot of origin */
    private boolean inDepot;

    /** if the vehicle isnt in the depot it's stationated at a specific client */
    private Client clientStop;

    /**
     * Constructs a Vehicle object with the given depot of origin and number of
     * clients it can visit.
     * 
     * @param depotOfOrigin   the depot from which the vehicle originates
     * @param numberOfClients the number of clients the vehicle can visit
     */
    protected Vehicle(Depot depotOfOrigin, Integer numberOfClients) {
        idNumber++;
        this.id = idNumber;
        this.depotOfOrigin = depotOfOrigin;
        this.inDepot = true;
        this.clientStop = null;

        if (numberOfClients < 0) {
            String message = Utility.textColoring("The vehicle needs to be able to visit none or more clients",
                    Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
            System.exit(0);
        }

        this.numberOfClients = numberOfClients;
        depotOfOrigin.addVehicle(this);
    }

    /**
     * Retrives if the vehicle is in it's depot
     * 
     * @return if the vehicle is in it's depot
     */
    public boolean isInDepot() {
        return inDepot;
    }

    /**
     * Sets the status of the vehicle in the depot.
     * 
     * @param inDepot true if the vehicle is in the depot, false otherwise
     */
    public void setInDepot(boolean inDepot) {
        this.inDepot = inDepot;
    }

    /**
     * Retrives the client where the vehicle is stopped at
     * 
     * @return  the client where the vehicle is stopped at
     */
    public Client getClientStop() {
        return clientStop;
    }

    /**
     * Sets the client where the vehicle has stopped.
     * 
     * @param clientStop the client where the vehicle has stopped
     */
    public void setClientStop(Client clientStop) {
        this.clientStop = clientStop;
    }

    /**
     * Retrives the depot where the vehicle originates from
     * 
     * @return  the depot where the vehicle originates from
     */
    public Depot getDepotOfOrigin() {
        return depotOfOrigin;
    }

    /**
     * Sets the depot from which the vehicle originates.
     * 
     * @param depotOfOrigin the depot from which the vehicle originates
     */
    public void setDepotOfOrigin(Depot depotOfOrigin) {
        this.depotOfOrigin = depotOfOrigin;
    }

    /**
     * Retrives the number of clients the vehicle can visit
     * 
     * @return  the number of clients the vehicle can visit
     */
    public Integer getNumberOfClients() {
        return numberOfClients;
    }

    /**
     * Sets the number of clients the vehicle can visit.
     * 
     * @param numberOfClients the number of clients the vehicle can visit
     */
    public void setNumberOfClients(Integer numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    /**
     * Retrives the id of the vehicle
     * 
     * @return  the id of the vehicle
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the vehicle.
     * 
     * @param id the ID of the vehicle
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the vehicle.
     * 
     * @return a string representation of the vehicle
     */

    @Override
    public String toString() {
        String message = id.toString() + " " + depotOfOrigin.getName() + " " + numberOfClients.toString() + " "
                + inDepot;
        return Utility.textColoring(message, Utility.ansiEscapeCodes.GREEN);
    }

    /**
     * Checks if this vehicle is equal to another object.
     * 
     * @param obj the object to compare
     * @return true if the vehicles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Vehicle vehicleToBeCompared = (Vehicle) obj;

        return this.id.equals(vehicleToBeCompared.id) && this.depotOfOrigin.equals(vehicleToBeCompared.depotOfOrigin);
    }

    /**
     * Returns the hash code value for the vehicle.
     * 
     * @return the hash code value for the vehicle
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hash(depotOfOrigin, numberOfClients, id);
        return hash;
    }

}
