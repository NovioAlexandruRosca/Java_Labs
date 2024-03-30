package src.base;

import java.time.LocalTime;
import java.util.regex.Pattern;
import java.util.logging.*;
import utility.ServiceException;
import utility.Utility;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Abstract class representing an attraction.
 */
public abstract class Attraction {

    /** The name of the attraction */
    private String name;

    /** The description of the attraction */
    private String description;

    /** The path for an image associated with the attraction */
    private Path imagePath;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Attraction.class.getName());

    /**
     * Constructs an Attraction object with the specified name, description, and image path.
     * 
     * @param name        the name of the attraction
     * @param description the description of the attraction
     * @param imagePath   the path to the image of the attraction
     */
    protected Attraction(String name, String description, String imagePath){
        this.name = name;
        this.description = description;

        if(isValidPath(imagePath))
            this.imagePath = Paths.get(imagePath);
        else
            System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Path getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        if(isValidPath(imagePath))
            this.imagePath = Paths.get(imagePath);
        else
            System.exit(0);
    }

    /**
     *  Validates if the given image has a correct path
     * @param pathString the image path
     * @return true if the path is absolute and normalized
     */
    public boolean isValidPath(String pathString) {
        try {
            Path path = Paths.get(pathString);
            
            return path.isAbsolute() && path.normalize().equals(path);
        } catch (Exception e) {
            String message = Utility.textColoring("Invalid path format", Utility.ansiEscapeCodes.RED);
            logger.log(Level.SEVERE, message);
            return false;
        }
    }

    /**
     * Validates if the time format is correct and if the closing time happens after the opening time
     * @param timeInterval An array of size 2 that has the opening time and the closing time
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
     * Validates the time format.
     * 
     * @param time    the time to validate
     * @param pattern the pattern for the time format
     * @return true if the time format is valid, false otherwise
     */
    private boolean validateTimeFormat(String time, String pattern) {
        return Pattern.matches(pattern, time);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append(name).append(" ").append(description).append(" ").append(imagePath.toString());
        return message.toString();
    }
}