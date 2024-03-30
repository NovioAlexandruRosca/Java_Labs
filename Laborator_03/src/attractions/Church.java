package src.attractions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import src.base.Attraction;
import src.generics.TimeInterval;
import src.interfaces.*;
import utility.Utility;
import java.util.logging.*;

/**
 * Represents a church attraction that is visitable by tourist.
 */
public class Church extends Attraction implements Visitable, Comparable<Church>{

    private Map<LocalDate, TimeInterval<LocalTime>> timeTable;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Church.class.getName());

    /**
     * Constructs a church attraction with the specified name, description, image path, open days, and open hours.
     * 
     * @param name        the name of the church
     * @param description the description of the church
     * @param imagePath   the image path of the church
     * @param openDays    the list of open days for the church
     * @param openHours   the array of open hours for the church
     */
    public Church(String name, String description, String imagePath) {
        super(name, description, imagePath);
        this.timeTable = new HashMap<>();
    }

    public void addTimeInterval(LocalDate date, TimeInterval<LocalTime> interval) {
        timeTable.put(date, interval);
    }

    public TimeInterval<LocalTime> getTimeInterval(LocalDate date) {
        return timeTable.get(date);
    }

        public Set<LocalDate> getDate(){
        return timeTable.keySet();
    }
    
    public int hashCode() {
        int hash = 51;
        return hash * 3 + Objects.hash(this.getName());
    }

    /**
     * Method to see if 2 objects are equal to eachother
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Church church = (Church) obj;
        return Objects.equals(this.getName(), church.getName()) && Objects.equals(this.getDescription(), church.getDescription()) &&
        Objects.equals(this.getImagePath(), church.getImagePath());
    }

    /**
     * Compares the elements in a natural name after their name
     */
    @Override
    public int compareTo (Church church) {
        try{
            if (church == null ) throw new NullPointerException();
        }catch(NullPointerException e){
            logger.log(Level.SEVERE, Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            System.exit(0);
        }
        return (this.getName().compareTo(church.getName()));
    }

    @Override
    public Map<LocalDate, TimeInterval<LocalTime>> getTimeTable() {
        return timeTable;
    }
    
}
