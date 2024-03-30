package src.base;

import utility.ServiceException;
import utility.Utility;
import java.util.regex.Pattern;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.*;
import utility.ClientType;

/**
 * Represents a client that can be visited by vehicles.
 */
public class Client {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    /** The type of the client. */
    private ClientType type;

    /** The name of the client. */
    private String name;

    /** The time interval for visiting the client. */
    private String[] timeInterval;

    /** Indicates whether the client has been visited or not. */
    private boolean visited;

    /**
     * Constructs a client with the specified type, name, and time intervals(given by an array containg the values).
     * 
     * @param type         the type of the client
     * @param name         the name of the client
     * @param timeInterval the time interval for visiting the client
     */
    public Client(ClientType type, String name, String[] timeInterval) {
        this.type = type;
        this.name = name;
        this.visited = false;
        validateTimeArgument(timeInterval);

        this.timeInterval = timeInterval;
    }

    /**
     * Constructs a client with the specified type, name, and time intervals(given by a start and an end value).
     * 
     * @param type            the type of the client
     * @param name            the name of the client
     * @param startOfInterval the start time of the visiting interval
     * @param endOfInterval   the end time of the visiting interval
     */
    public Client(ClientType type, String name, String startOfInterval, String endOfInterval) {
        this.type = type;
        this.name = name;

        String[] newTimeInterval = new String[] { startOfInterval, endOfInterval };

        validateTimeArgument(newTimeInterval);

        this.timeInterval = newTimeInterval;
    }

    /**
     * Validates the time interval for visiting the client.
     * 
     * @param timeInterval the time interval for visiting the client
     */
    protected void validateTimeArgument(String[] timeInterval) {
        try {
            if (timeInterval.length != 2)
                throw new ServiceException("The interval for visiting a client consists of 2 values exactly");

            String pattern = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

            if (!validateTimeFormat(timeInterval[0], pattern))
                throw new ServiceException("The starting value for the visiting interval [" + timeInterval[0]
                        + "] is incorrectly given!");
            if (!validateTimeFormat(timeInterval[1], pattern))
                throw new ServiceException("The finishing value for the visiting interval [" + timeInterval[1]
                        + "] is incorrectly given!");

            if(compareTimeIntervals(timeInterval[0], timeInterval[1]))
                throw new ServiceException("The timestamps are wrong, "
                       + "you can't have the starting time happen before the closing time");

        } catch (ServiceException e) {
            String message = Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
            System.exit(0);
        }
    }

    private boolean compareTimeIntervals(String startingTime, String finishingTime) {
        
        LocalTime time1 = LocalTime.parse(startingTime);
        LocalTime time2 = LocalTime.parse(finishingTime);

        return time1.isAfter(time2);
    }

    /**
     * Returns if a given client can be visited at a specific time
     * @param time The time the vehicle wants to visit the client
     * @return Returns if the client can be bisited at that specific time
     */
    public boolean canBeVisited(String time) {
        
        LocalTime vehicleTime = LocalTime.parse(time);
        LocalTime time1 = LocalTime.parse(timeInterval[0]);
        LocalTime time2 = LocalTime.parse(timeInterval[1]);

        return vehicleTime.isAfter(time1) && vehicleTime.isBefore(time2);
    }

    /**
     * Validates the time format.
     * 
     * @param time    the time to validate
     * @param pattern the pattern for the time format
     * @return true if the time format is valid, false otherwise
     */
    protected boolean validateTimeFormat(String time, String pattern) {
        return Pattern.matches(pattern, time);
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String[] timeInterval) {
        validateTimeArgument(timeInterval);
        this.timeInterval = timeInterval;
    }

    public void setTimeInterval(String startOfInterval, String endOfInterval) {
        String[] newTimeInterval = new String[] { startOfInterval, endOfInterval };
        validateTimeArgument(newTimeInterval);
        this.timeInterval = newTimeInterval;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Returns a string representation of the client.
     * 
     * @return a string representation of the client
     */
    @Override
    public String toString() {
        String message = type.toString() + " " + name + " " + Arrays.toString(timeInterval);
        return Utility.textColoring(message, Utility.ansiEscapeCodes.GREEN);
    }

    /**
     * Checks if this client is equal to another object.
     * 
     * @param obj the object to compare
     * @return true if the clients are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (this.getClass() != obj.getClass())
        // (obj instanceof this.getClass())
            return false;

        Client clientToBeCompared = (Client) obj;

        return this.type == clientToBeCompared.type && this.name.equals(clientToBeCompared.name)
                && Objects.equals(timeInterval, clientToBeCompared.timeInterval);
    }

    /**
     * Returns the hash code value for the client.
     * 
     * @return the hash code value for the client
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hash(type, name, timeInterval); 
        return hash;
    }

}