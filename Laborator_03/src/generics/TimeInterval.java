package src.generics;

import java.time.LocalTime;

public final class TimeInterval<T extends LocalTime> {
    private T startingTime;
    private T closingTime;

    public TimeInterval(T startTime, T endTime) {
        this.startingTime = startTime;
        this.closingTime = endTime;
    }

    public T getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(T startTime) {
        this.startingTime = startTime;
    }

    public T getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(T endTime) {
        this.closingTime = endTime;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        message.append("TimeInterval{").append("startingTime= ").append(startingTime).append(", closingTime= ").append(closingTime).append("}");

        return message.toString();
    }
}

   