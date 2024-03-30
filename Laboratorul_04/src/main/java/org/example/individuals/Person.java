package org.example.individuals;

import org.example.carpoolingLogic.Carpooling;
import org.example.destination.Destination;
import org.example.destination.DestinationUtils;
import org.example.utility.Utility;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Represents a person with a unique identifier, name, destination, and age that can be either a driver or a passenger
 */
public abstract class Person {

    /** The class identifier for generating unique IDs. */
    private static Integer classId = 0;

    /** The unique identifier of the person. */
    private final Integer id;

    /** The name of the person. */
    private final String name;

    /** The destination of the person. */
    private final Destination destination;

    /** The starting location of the driver. */
    private final Destination startingLocation;

    private final List<Destination> course;

    /** The age of the person. */
    private Integer age;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(Person.class.getName());

    /**
     * Constructs a person with the specified name, destination, and age.
     *
     * @param name the name of the person
     * @param destination the destination of the person
     * @param age the age of the person
     */
    public Person(String name, Integer age, Destination destination, Destination startingLocation) {
        this.id = classId++;
        this.name = name;
        this.age = age;
        this.destination = destination;
        this.startingLocation = startingLocation;

        DestinationUtils generateCourse = new DestinationUtils(startingLocation.clone(), destination);

        this.course = new ArrayList<>(generateCourse.generateCourse());
        Collections.reverse(this.course);

    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Destination getDestination() {
        return destination;
    }

    public Destination getStartingLocation() {
        return startingLocation;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Destination> getCourse() {
        return course;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        message.append("Person{" + "id=")
                .append(id).append(", name='").append(name).append('\'')
                .append(", starting location='").append(destination.getName()).append('\'')
                .append(", destination=' ").append(startingLocation.getName()).append('\'').append(", course='");

        course.forEach(c -> message.append(c.getName()).append(','));

        message.append('\'' + ", age=").append(age).append('}');

        return message.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(destination, person.destination) && Objects.equals(startingLocation, person.startingLocation) && Objects.equals(course, person.course) && Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, destination, startingLocation, course, age);
    }
}