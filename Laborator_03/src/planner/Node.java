package src.planner;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import src.base.Attraction;

/**
 * A class representing the node of a graph where the main value is an attraction that has
 * a set of connections(edges) a specific color and a list of dates at which it happens
 */
public class Node {
    
    /** The attraction associated with the node. */
    private Attraction attraction;

    /** The list of connections to other nodes. */
    private List<Node> connections;

    /** The color assigned to the node. */
    private LocalDate color;

    /** The set of dates associated with the node. */
    private Set<LocalDate> dates;

    /**
     * Constructor for the node class that populates the attraction type and the dates at which it takes place
     * @param attraction  the attraction(main value of the node)
     * @param dates the dates at which it takes place
     */
    public Node(Attraction attraction, Set<LocalDate> dates) {
        this.attraction = attraction;
        this.dates = dates;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public LocalDate getColor() {
        return color;
    }

    public void setColor(LocalDate color) {
        this.color = color;
    }

    public Set<LocalDate> getDates() {
        return dates;
    }

    public void setDates(Set<LocalDate> dates) {
        this.dates = dates;
    }

    public List<Node> getConnections() {
        return connections;
    }

    public void setConnections(List<Node> connections) {
        this.connections = connections;
    }

    public int getDegree(){
        return connections.size();
    }

    
}
