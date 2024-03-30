package src.derived;

import utility.ServiceException;
import utility.Utility;
import java.util.Objects;
import java.util.logging.*;
import src.base.Depot;
import src.base.Vehicle;

/**
 * A class representing a Truck, which is a type of Vehicle.
 */
public final class Truck extends Vehicle {

    /** The capacity of the truck. */
    private Integer capacity;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Truck.class.getName());

    /**
     * Constructs a new Truck object with the specified depot of origin, number of clients, and capacity.
     *
     * @param depotOfOrigin The depot of origin for the truck.
     * @param numberOfClients The number of clients the truck serves.
     * @param capacity The capacity of the truck.
     */
    public Truck(Depot depotOfOrigin, Integer numberOfClients, Integer capacity) {
        super(depotOfOrigin, numberOfClients);
        validateCapacity(capacity);
        this.capacity = capacity;
    }

    /**
     * Retrieves the capacity of the truck.
     *
     * @return The capacity of the truck.
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the truck.
     *
     * @param capacity The new capacity to set.
     */
    public void setCapacity(Integer capacity) {
        validateCapacity(capacity);
        this.capacity = capacity;
    }

    /**
     * Validates the capacity of the truck.
     *
     * @param capacity The capacity to validate.
     */
    protected void validateCapacity(Integer capacity) {
        try {
            if (capacity <= 0)
                throw new ServiceException("You can't have a negative capacity for the truck with an id of " + this.getId());
        } catch (ServiceException e) {
            String message = Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
            System.exit(0);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Truck vehicleToBeCompared = (Truck) obj;

        return this.getId().equals(vehicleToBeCompared.getId()) && 
            this.getDepotOfOrigin().equals(vehicleToBeCompared.getDepotOfOrigin()) && this.capacity == vehicleToBeCompared.getCapacity();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hash(getDepotOfOrigin(), getId(), getNumberOfClients(), capacity);
        return hash;
    }
}
