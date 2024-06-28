package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int PORT = 8100;
        try (
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket(serverAddress, PORT);
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))
        ) {
            while (true) {
                System.out.print("Enter a command: ");
                String request = scanner.nextLine();

                out.println(request);

                String response = in.readLine();
                System.out.println(response);

                if(request.compareToIgnoreCase("exit") == 0 || request.compareToIgnoreCase("stop") == 0){
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            System.err.println("The server was closed... " + e);
        }
    }
}