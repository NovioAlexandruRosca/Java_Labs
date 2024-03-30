package src.attractions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.*;
import src.base.Attraction;
import src.generics.TimeInterval;
import src.interfaces.*;
import java.util.Set;
import utility.Utility;

/**
 * Represents a statue attraction that is visitable by tourists.
 */
public class Statue extends Attraction implements Visitable, Comparable<Statue> {

    private Map<LocalDate, TimeInterval<LocalTime>> timeTable;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Statue.class.getName());

    /**
     * Constructs a statue attraction with the specified name, description, image path, open days, and open hours.
     * 
     * @param name        the name of the statue
     * @param description the description of the statue
     * @param imagePath   the image path of the statue
     * @param openDays    the list of open days for the statue
     * @param openHours   the array of open hours for the statue
     */
    public Statue(String name, String description, String imagePath){
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
    
    @Override
    public int hashCode() {
        int prime = 37;
        int result = 5;
        result = prime * result + Objects.hash(this.getName(), this.getImagePath());
        return result;
    }
    /**
     * Method to see if 2 objects are equal to eachother
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

        Statue statue = (Statue) obj;

        return this.getName().equals(statue.getName()) && this.getDescription().equals(statue.getDescription()) 
               && this.getImagePath().equals(statue.getImagePath());
    }

    /**
     * Compares the elements in a natural name after their name
     */
    @Override
    public int compareTo (Statue statue) {
        try{
            if (statue == null ) throw new NullPointerException();
        }catch(NullPointerException e){
            logger.log(Level.SEVERE, Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            System.exit(0);
        }
    
        return (this.getName().compareTo(statue.getName()));
    }

    @Override
    public Map<LocalDate, TimeInterval<LocalTime>> getTimeTable() {
        return timeTable;
    }
}
