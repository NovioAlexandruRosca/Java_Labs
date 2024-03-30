package src.attractions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import utility.Utility;
import src.base.Attraction;
import src.generics.TimeInterval;
import src.interfaces.*;
import java.util.logging.*;

/**
 * Represents a concert tourists can take part of
 */
public class Concert extends Attraction implements Visitable, Payable, Comparable<Concert>{

    private Map<LocalDate, TimeInterval<LocalTime>> timeTable;

    /** The fee you have to pay to participate at the concert */
    private int entryFee;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Concert.class.getName());

    /**
     * Constructs a concert attraction with the specified name, description, image path, open days, open hours, and entry fee.
     * 
     * @param name        the name of the concert
     * @param description the description of the concert
     * @param imagePath   the image path of the concert
     * @param openDays    the list of open days for the concert
     * @param openHours   the array of open hours for the concert
     * @param entryFee    the entry fee to participate in the concert
     */
    public Concert(String name, String description, String imagePath, int entryFee) {
        super(name, description, imagePath);
        this.entryFee = entryFee;
        this.timeTable = new HashMap<>();
    }

    @Override
    public void setPriceFee(int entryFee) {
        this.entryFee = entryFee;
    }

    @Override
    public int getPriceFee() {
        return entryFee;
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
        return hash * 3 + Objects.hash(this.getName(), this.getPriceFee());
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

        Concert concert = (Concert) obj;

        return this.getName().equals(concert.getName()) && this.entryFee == concert.entryFee
               && this.getDescription() == concert.getDescription() && this.getImagePath() == concert.getImagePath();
    }

    /**
     * Compares the elements in a natural name after their name
     */
    @Override
    public int compareTo (Concert concert) {
        try{
            if (concert == null ) throw new NullPointerException();
        
        }catch(NullPointerException e){
            logger.log(Level.SEVERE, Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            System.exit(0);
        }
    
        return (this.getName().compareTo(concert.getName()));
    }

    @Override
    public Map<LocalDate, TimeInterval<LocalTime>> getTimeTable() {
        return timeTable;
    }
    
}
