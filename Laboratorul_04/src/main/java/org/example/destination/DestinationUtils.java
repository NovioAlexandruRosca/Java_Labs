package org.example.destination;

import java.util.*;

public class DestinationUtils {

    private final Set<Destination> visited;
    private final Destination start;
    private final Destination end;

    public DestinationUtils(Destination start, Destination end) {
        this.visited = new HashSet<>();
        this.start = start;
        this.end = end;

    }

    public List<Destination> generateCourse() {

        List<Destination> course = new ArrayList<>();

        if (start.getName().equals(end.getName())) {
            course.add(start);
            return course;
        }

        Random random = new Random();

        int randomLength = random.nextInt(100);

        if (generateCourse(start, end, course, randomLength)) {
            return course;
        } else {
            return new ArrayList<>();
        }
    }

    private boolean generateCourse(Destination current, Destination end, List<Destination> course, int randomLength) {

        visited.add(current);
        if(current.equals(end)){
            course.add(0, current);
            return true;
        }

        if(current.getAdjacentDestinations().contains(end) && randomLength < 0){
            if(generateCourse(end, end, course, randomLength - 1)){
                course.add(0, current);
                return true;
            }
        }

        for (Destination nextDestination : current.getAdjacentDestinations()) {
            if(!course.contains(nextDestination) && !visited.contains(nextDestination)){
                if(generateCourse(nextDestination, end, course, randomLength - 1)){
                    course.add(0, current);
                    return true;
                }
            }
        }

        return false;
    }
}