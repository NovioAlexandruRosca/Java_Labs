package src.trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import src.attractions.Church;
import src.attractions.Concert;
import src.attractions.Statue;
import src.base.Attraction;
import src.generics.TimeInterval;
import src.interfaces.Payable;
import src.interfaces.Visitable;
import utility.Utility;
import java.util.logging.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a trip that includes various attractions and spans a duration from a starting date to an ending date.
 */
public class Trip implements Comparable<Trip>{

    /** The name of the trip */
    private String name;

    /** The starting date of the trip */
    private LocalDate startingDate;

    /** The ending date of the trip */
    private LocalDate endingDate;

    /** The list of attractions included in the trip */
    private ArrayList<Attraction> attractions;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Trip.class.getName());

    /** 
     * Constructs a trip with the specified name, starting date, ending date, and list of attractions.
     * 
     * @param name          the name of the trip
     * @param startingDate  the starting date of the trip
     * @param endingDate    the ending date of the trip
     * @param attractions   the list of attractions included in the trip
     */
    Trip(String name, LocalDate startingDate, LocalDate endingDate, ArrayList<Attraction> attractions){
        this.name = name;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.attractions = attractions;
    }

    /** 
     * Constructs a trip with the specified name, starting date, ending date
     * 
     * @param name          the name of the trip
     * @param startingDate  the starting date of the trip
     * @param endingDate    the ending date of the trip
     */
    public Trip(String name, LocalDate startingDate, LocalDate endingDate){
        this.name = name;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.attractions = new ArrayList<>();
    }

    /**
     * This method displays all the attractiosns from the trip that are visitable but not payable
     */
    public void displayVisitableNotPayable(){

        List<Attraction> attractionList = new ArrayList<>();

        for (Attraction attraction : attractions) {
            if (attraction instanceof Visitable && !(attraction instanceof Payable))
                attractionList.add(attraction);
        }

        sortByOpeningHour(attractionList);        

        attractionList.stream().forEach( e -> logger.log(Level.INFO, Utility.textColoring(e.toString(), Utility.ansiEscapeCodes.GREEN)));
    }

    /**
     * This method sorts the attractions based on their oppening hour using the Functional Interface Comparator
     * @param attractionList the list of attractions to be sorted
     */
    private void sortByOpeningHour(List<Attraction> attractionList) {
        Comparator<Attraction> comparator = new Comparator<Attraction>(){
            @Override
            public int compare(Attraction a1, Attraction a2) {

                LocalTime startTime1 = getStartTime(a1);
                LocalTime startTime2 = getStartTime(a2);

                if(startTime1 != null && startTime2 != null)
                    return startTime1.compareTo(startTime2);
                else 
                    return 0;
            }
        };
        Collections.sort(attractionList, comparator);
    }

    /**
     * this methods returns the starting hour for a given attraction
     * @param attraction the attraction of which we want to find the start time
     * @return  returns the start time for a given attractions
     */
    private LocalTime getStartTime(Attraction attraction) {
        if (attraction instanceof Church) {
            return getSmallestTime((Church) attraction);
        } else if (attraction instanceof Concert) {
            return getSmallestTime((Concert) attraction);
        } else if (attraction instanceof Statue) {
            return getSmallestTime((Statue) attraction);
        } else {
            return null;
        }
    }

    /**
     * A generic method that returns the opening hour of an attraction
     * @param a1 a generic attraction that is visitable
     * @return  returns the opening hour of an attraction
     */
    private<T extends Visitable> LocalTime getSmallestTime(T a1){
        
        LocalTime startTime1 = null;

        for (LocalDate date :  a1.getDate()) {
            TimeInterval<LocalTime> time = a1.getTimeInterval(date);
            if(startTime1 == null || time.getStartingTime().isBefore(startTime1))
                startTime1 = time.getStartingTime();
        }
        
        return startTime1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void addAttractions(Attraction attraction) {
        this.attractions.add(attraction);
    }

    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        string.append(name).append(" ").append(startingDate).append(" ").append(endingDate).append("\n");

        for (Attraction attraction : attractions) {
            if(attraction instanceof Church){
                Church church = (Church) attraction;
                string.append(attraction.toString()).append(" ").append(" ");
                for (LocalDate date : church.getDate()) {
                    string.append(date.toString()).append(" ");
                    string.append(church.getTimeInterval(date).getStartingTime().toString()).append(" ");
                    string.append(church.getTimeInterval(date).getClosingTime().toString()).append(" ");
                }
            }
            else if(attraction instanceof Concert){
                Concert concert = (Concert) attraction;
                string.append(attraction.toString()).append(" ").append(" ").append(concert.getPriceFee()).append(" ");
                for (LocalDate date : concert.getDate()) {
                    string.append(date.toString()).append(" ");
                    string.append(concert.getTimeInterval(date).getStartingTime().toString()).append(" ");
                    string.append(concert.getTimeInterval(date).getClosingTime().toString()).append(" ");
                }
            }
            else if(attraction instanceof Statue){
                Statue statue = (Statue) attraction;
                string.append(attraction.toString()).append(" ");
                for (LocalDate date : statue.getDate()) {
                    string.append(date.toString()).append(" ");
                    string.append(statue.getTimeInterval(date).getStartingTime().toString()).append(" ");
                    string.append(statue.getTimeInterval(date).getClosingTime().toString()).append(" ");
                }
            }
            string.append("\n");
        }
        return string.toString();
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int result = 5;
        return prime * result + Objects.hash(name, startingDate, endingDate, attractions);
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

        Trip trip = (Trip) obj;

        return this.name.equals(trip.name) && this.startingDate.equals(trip.startingDate)
               && this.getStartingDate().equals(trip.getStartingDate()) && this.getEndingDate().equals(trip.getEndingDate())
               && this.getAttractions().equals(trip.getAttractions());
    }

    /**
     * Compares the elements in a natural name after their name
     */
    @Override
    public int compareTo (Trip trip) {
        try{
            if (trip == null ) throw new NullPointerException();
        }catch(NullPointerException e){
            logger.log(Level.SEVERE, Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            System.exit(0);
        }
    
        return (this.getName().compareTo(trip.getName()));
    }

}