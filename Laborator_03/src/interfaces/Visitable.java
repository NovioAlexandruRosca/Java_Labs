package src.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.*;
import src.generics.TimeInterval;

/**
 * Interface representing a visitable attraction.
 */
public interface Visitable {

    /** Logger for logging information. */
    Logger logger = Logger.getLogger(Visitable.class.getName());

    public void addTimeInterval(LocalDate date, TimeInterval<LocalTime> interval);

    public TimeInterval<LocalTime> getTimeInterval(LocalDate date);

    public default Map<LocalDate, TimeInterval<LocalTime>> getTimeTable(){
        return new HashMap<>();
    }

    public default LocalTime getStartingHour(LocalDate date){
        if(getTimeTable() == null || getTimeTable().get(date) == null){
            return null;
        }
        return getTimeTable().get(date).getStartingTime();
    }

    public Set<LocalDate> getDate();

}
