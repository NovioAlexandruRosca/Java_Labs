package src.derived;

import utility.ServiceException;
import utility.Utility;
import java.util.Objects;
import java.util.logging.*;
import src.base.Depot;
import src.base.Vehicle;

/**
 * A class representing a Drone, which is a type of Vehicle.
 */
public final class Drone extends Vehicle {

    /** The duration of flight for the drone. */
    private Integer flightDuration;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Drone.class.getName());

    /**
     * Constructs a new Drone object with the specified depot of origin, number of clients, and flight duration.
     *
     * @param depotOfOrigin The depot of origin for the drone.
     * @param numberOfClients The number of clients the drone serves.
     * @param flightDuration The duration of flight for the drone.
     */
    public Drone(Depot depotOfOrigin, Integer numberOfClients, Integer flightDuration) {
        super(depotOfOrigin, numberOfClients);
        validateFlightDuration(flightDuration);
        this.flightDuration = flightDuration;
    }

    /**
     * Retrieves the flight duration of the drone.
     *
     * @return The flight duration of the drone.
     */
    public Integer getFlightDuration() {
        return flightDuration;
    }

    /**
     * Sets the flight duration of the drone.
     *
     * @param flightDuration The new flight duration to set.
     */
    public void setFlightDuration(Integer flightDuration) {
        validateFlightDuration(flightDuration);
        this.flightDuration = flightDuration;
    }

    /**
     * Validates the flight duration of the drone.
     *
     * @param flightDuration The flight duration to validate.
     */
    protected void validateFlightDuration(Integer flightDuration) {
        try {
            if (flightDuration <= 0)
                throw new ServiceException("You can't have a negative flight duration for the drone with an id of " + this.getId());
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

        Drone vehicleToBeCompared = (Drone) obj;

        return this.getId().equals(vehicleToBeCompared.getId()) && 
               this.getDepotOfOrigin().equals(vehicleToBeCompared.getDepotOfOrigin()) && this.flightDuration == vehicleToBeCompared.getFlightDuration();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hash(getDepotOfOrigin(), getId(), getNumberOfClients(), flightDuration);
        return hash;
    }
}
