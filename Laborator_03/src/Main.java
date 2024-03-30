package src;

import java.util.logging.*;
import src.attractions.Church;
import src.attractions.Concert;
import src.attractions.Statue;
import src.generics.TimeInterval;
import src.planner.Node;
import src.planner.TravelPlanner;
import src.trip.TravelPlan;
import src.trip.Trip;
import utility.Utility;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class to run the implementation of all the other classes in the object
 */
public class Main {

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        LocalDate tripStartDate = LocalDate.of(2024, 10, 10);
        LocalDate tripEndingDate = LocalDate.of(2024, 12, 10);
       
        Statue statue = new Statue("Alexandru Ioan Cuza", "Description1", "C:\\");
        Church church = new Church("Mircea The Old One", "Description2", "C:\\");
        Concert concert = new Concert("O-zone", "Description3", "C:\\", 150);
        Statue statue1 = new Statue("Alex", "Description4", "C:\\");

        statue1.addTimeInterval(LocalDate.of(2024, 10, 14),
                new TimeInterval<>(LocalTime.of(9, 0), LocalTime.of(17, 0)));

        statue.addTimeInterval(LocalDate.of(2024, 10, 14),
                new TimeInterval<>(LocalTime.of(9, 0), LocalTime.of(17, 0)));
           
        statue.addTimeInterval(LocalDate.of(2024, 11, 12),
                new TimeInterval<>(LocalTime.of(5, 0), LocalTime.of(17, 0)));  

        statue.addTimeInterval(LocalDate.of(2024, 11, 15),
                new TimeInterval<>(LocalTime.of(6, 0), LocalTime.of(17, 0)));                

        church.addTimeInterval(LocalDate.of(2024, 11, 15),
                new TimeInterval<>(LocalTime.of(8, 0), LocalTime.of(18, 0)));

        concert.addTimeInterval(LocalDate.of(2024, 11, 16),
                new TimeInterval<>(LocalTime.of(11, 50), LocalTime.of(19, 40)));

        Trip trip = new Trip("Paris Trip", tripStartDate, tripEndingDate);
        
        trip.addAttractions(concert);
        trip.addAttractions(statue);
        trip.addAttractions(church);
        trip.addAttractions(statue1);

        // for (Attraction attraction : trip.getAttractions()) {
        //     if(attraction instanceof Church){
        //         Church church = (Church) attraction;
        //         logger.log(Level.INFO, Utility.textColoring(church.toString(), Utility.ansiEscapeCodes.GREEN));
        //     }else if(attraction instanceof Concert){
        //         Concert concert = (Concert) attraction;
        //         logger.log(Level.INFO, Utility.textColoring(concert.toString(), Utility.ansiEscapeCodes.GREEN));
        //     }else if(attraction instanceof Statue){
        //         Statue statue = (Statue) attraction;
        //         logger.log(Level.INFO, Utility.textColoring(statue.toString(), Utility.ansiEscapeCodes.GREEN));
        //     }
        // }

        logger.log(Level.INFO, Utility.textColoring(trip.toString(), Utility.ansiEscapeCodes.GREEN));
        
        trip.displayVisitableNotPayable();
                
        if(concert.getStartingHour(LocalDate.of(2024, 11, 16)) == null){
                logger.log(Level.SEVERE, "The attraction doesnt happen at that specific date");
                System.exit(0);
        }else 
                logger.info(concert.getStartingHour(LocalDate.of(2024, 11, 16)).toString());

        // TravelPlan travelPlan = new TravelPlan();

        // travelPlan.addDateForAttraction(concert, tripEndingDate);
        // travelPlan.addDateForAttraction(church, tripEndingDate);
        // travelPlan.addDateForAttraction(statue, tripEndingDate);

        // travelPlan.printTravelPlan();

        Node node1 = new Node(concert, concert.getDate());
        Node node2 = new Node(statue, statue.getDate());
        Node node3 = new Node(church, church.getDate());
        Node node4 = new Node(statue1, statue1.getDate());

        TravelPlanner travelPlanner = new TravelPlanner(trip.getStartingDate(), trip.getEndingDate());

        travelPlanner.addNode(node4);
        travelPlanner.addNode(node1);
        travelPlanner.addNode(node2);
        travelPlanner.addNode(node3);

        travelPlanner.computePlan();


    }
}
