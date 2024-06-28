package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoadTestApp {

    public static void main(String[] args) {
        int maxRequests = 2000;
        int increment = 100;
        int numRequests = 0;
        String serviceUrl = "http://localhost:9090/api/books";

        List<Thread> threads = new ArrayList<>();
        AtomicBoolean errorOccurred = new AtomicBoolean(false);

        try {
            while (numRequests <= maxRequests && !errorOccurred.get()) {
                System.out.println("Attempting to send " + numRequests + " requests...");

                for (int i = 0; i < numRequests; i++) {
                    Thread thread = new Thread(() -> {
                        try {
                            URL url = new URL(serviceUrl);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");

                            int responseCode = connection.getResponseCode();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();

                            connection.disconnect();
                        } catch (ConnectException e) {
                            System.out.println("Connection refused: " + e.getMessage());
                            errorOccurred.set(true);
                            throw new RuntimeException("Connection refused", e);
                        } catch (BindException e) {
                            System.out.println("Address already in use: " + e.getMessage());
                            errorOccurred.set(true);
                            throw new RuntimeException("Address already in use", e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            errorOccurred.set(true);
                            throw new RuntimeException("An error occurred", e);
                        }
                    });
                    threads.add(thread);
                    thread.start();
                }

                Thread.sleep(500);

                numRequests += increment;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Reached maximum requests: " + maxRequests);
        } catch (RuntimeException e) {
            if (!errorOccurred.get()) {
                System.out.println("An exception occurred: " + e.getMessage());
            }
            threads.clear();
        }

        System.out.println("Maximum number of requests: " + numRequests);
        System.exit(0);
    }
}
