package src.trip;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import src.base.Attraction;
import utility.Utility;

import java.time.LocalDate;

/**
 * Represents a travel plan that specifies in which day the tourist will visit each attraction.
 */
public class TravelPlan {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(TravelPlan.class.getName());

    /** Map to store the visit days for each attraction. */
    private Map<Attraction, LocalDate> visitDays;

    /**
     * Constructs a new TravelPlan object.
     */
    public TravelPlan() {
        visitDays = new HashMap<>();
    }

    /**
     * Adds a visit date for the specified attraction.
     *
     * @param attraction the attraction to add visit date for
     * @param date the date of the visit
     */
    public void addDateForAttraction(Attraction attraction, LocalDate date) {
        visitDays.put(attraction, date);
    }

    /**
     * Prints the travel plan, showing which attractions will be visited on which dates.
     */
    public void printTravelPlan() {
        for (Map.Entry<Attraction, LocalDate> entry : visitDays.entrySet()) {
            StringBuilder message = new StringBuilder();
            message.append(entry.getKey().toString()).append(" ");
            message.append(entry.getValue().toString()).append(" ");
            logger.log(Level.INFO, Utility.textColoring(message.toString(), Utility.ansiEscapeCodes.GREEN));
        }
    }
}
