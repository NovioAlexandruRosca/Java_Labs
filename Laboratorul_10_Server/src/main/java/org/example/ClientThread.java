package org.example;

import org.example.gameLogic.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;

class ClientThread extends Thread {
    private final Socket socket;
    private final GameServer server;
    private final int playerId;
    Game game;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
        this.playerId = server.playerIdCounter.getAndIncrement();
        game = null;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void run() {

        GameServer.addClient(playerId);

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            while (true) {
                String request = in.readLine();
                if (request == null) {
                    break;
                }

                String[] parts = request.split(" ");
                String command = parts[0];

                String response;
                switch (command.toLowerCase()) {
                    case "create":
                        int gameId = server.createGame();
                        game = server.getGame(gameId);
                        game.setPlayer1Id(playerId);
                        response = "Game created with ID: " + gameId;
                        break;
                    case "join":
                        if (parts.length < 2) {
                            response = "Invalid join command. Usage: join <gameId>";
                            break;
                        }
                        gameId = Integer.parseInt(parts[1]);
                        game = server.getGame(gameId);
                        if (game == null) {
                            response = "Game ID not found.";
                        } else {
                            response = "Joined game with ID: " + gameId;
                            if(game.getPlayer2Id() == 0)
                                game.setPlayer2Id(playerId);
                            else
                                response = "Someone has already joined the game";
                        }
                        break;
                    case "attack":

                        game.decrementTimer(playerId);

                        if (parts.length < 3) {
                            response = "Invalid move command. Usage: move <gameId> <x> <y>";
                            break;
                        }
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        if(x >= 10 || y >= 10 || x < 0 || y < 0) {
                            response = "Invalid move command. Usage: move <gameId> <x> <y> [0-9]";
                            break;
                        }
                        if (game == null) {
                            response = "You haven't connected to a game";
                        } else {
                            response = game.makeMove(playerId % 2 == 0 ? 2 : 1, x, y);
                        }

                        break;
                    case "place":
                        if (parts.length < 4) {
                            response = "Invalid place command. Usage: place <x> <y> <length> <horizontal>";
                            break;
                        }
                        int xCord = Integer.parseInt(parts[1]);
                        int yCord = Integer.parseInt(parts[2]);
                        int length = Integer.parseInt(parts[3]);
                        boolean horizontal = Boolean.parseBoolean(parts[4]);

                        if (game == null) {
                            response = "You haven't connected to a game";
                        } else {
                            response = game.placeShip(playerId, xCord, yCord, length, horizontal);
                        }
                        break;
                    case "exit":
                        response = "Disconnected.";
                        game.endGame();
                        game.stopTimer1();
                        game.stopTimer2();
                        break;
                    case "tournament":
                        GameServer.generateTournament();
                        response = "Tournament started.";
                        break;
                    case "stop":
                        response = "Server stopped";
                        out.println(response);
                        server.closeServer();
                        break;
                    default:
                        response = "Unknown command: " + command;
                        break;
                }


                System.out.println("Client " + playerId + " " + response);
                out.println(playerId + " " + response);
                if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("stop")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } catch (Exception e) {
            System.out.println("Cant create tournament with given data");
        } finally {
            try {
                socket.close();
                GameServer.removeClient(playerId);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}