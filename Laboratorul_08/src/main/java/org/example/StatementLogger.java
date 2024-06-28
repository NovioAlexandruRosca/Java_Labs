package org.example;

import java.util.logging.*;

public class StatementLogger {
    private static final Logger LOGGER = Logger.getLogger(StatementLogger.class.getName());

    public StatementLogger() {
    }

    public static void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("repositoryResults.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new SimpleFormatter());

            LOGGER.addHandler(fileHandler);
            LOGGER.addHandler(consoleHandler);

            LOGGER.setLevel(Level.INFO);
            LOGGER.setUseParentHandlers(false);
        } catch (Exception e) {
            System.err.println("Logger couldn't be set up: " + e.getMessage());
        }

    }

    public static void logCritical(Exception e) {
        LOGGER.severe("Exception: " + e.getMessage());
    }

    public static void logInfo(String message) {
        LOGGER.log(Level.INFO, "JPQL statement: " + message);
    }
}
