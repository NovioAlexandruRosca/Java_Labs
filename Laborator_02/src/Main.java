package src;

import java.util.logging.*;

import src.base.Client;
import src.base.Depot;
import src.base.Vehicle;
import src.derived.Drone;
import src.derived.Truck;
import src.problems.ProblemInstanceGenerator;
import utility.ClientType;

public class Main {
    
    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){

        // String[] timeInterval = new String[]{"12:00", "18:50"};

        // Client client1 = new Client(ClientType.PREMIUM, "Emag", timeInterval);
        // Client client2 = new Client(ClientType.REGULAR, "Flanco", "08:00", "23:00");
        // Client client3 = new Client(ClientType.PREMIUM, "Alte", "11:20", "20:10");

        // // logger.log(Level.INFO, client3.toString());

        // client3.setName("Altex");

        // // logger.log(Level.INFO, client1.toString());
        // // logger.log(Level.INFO, client2.toString());
        // // logger.log(Level.INFO, client3.toString());

        // Depot depot1 = new Depot("Depot1", new Vehicle[]{});
        // Depot depot2 = new Depot("Depot2", new Vehicle[]{});

        // Vehicle vehicle1 = new Truck(depot1, 2, 5);
        // Vehicle vehicle2 = new Truck(depot1, 1, 4);
        // Vehicle vehicle3 = new Drone(depot2, 3, 3);

        // // logger.log(Level.INFO, depot1.toString());
        // // logger.log(Level.INFO, depot2.toString());

        // // logger.log(Level.INFO, vehicle1.toString());
        // // logger.log(Level.INFO, vehicle2.toString());
        // // logger.log(Level.INFO, vehicle3.toString());

        // Problem problem1 = new Problem();

        // problem1.addDepot(depot1);
        // problem1.addDepot(depot2);
        // problem1.addVehicle(vehicle1);
        // problem1.addVehicle(vehicle2);
        // problem1.addVehicle(vehicle3);
        // problem1.addClient(client1);
        // problem1.addClient(client2);
        // problem1.addClient(client3);

        // problem1.solve();

        ProblemInstanceGenerator.main(args);
    }
}
