package org.example;

import org.example.TournamentLogic.OutcomeGenerator;
import org.example.TournamentLogic.Schedule;
import org.example.TournamentLogic.Tournament;
import org.example.TournamentLogic.WinningSequence;
import org.example.gameLogic.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {

    public static final int PORT = 8100;
    private static final Map<Integer, Game> games = new HashMap<>();
    private static final List<Integer> clients = new ArrayList<>();
    private final AtomicInteger gameIdCounter = new AtomicInteger(1);
    final AtomicInteger playerIdCounter = new AtomicInteger(1);

    public GameServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                new ClientThread(socket, this).start();
            }
        } catch (IOException e) {
            System.err.println("Oops... " + e);
        }
    }

    public synchronized int createGame() {
        int gameId = gameIdCounter.getAndIncrement();
        games.put(gameId, new Game());
        return gameId;
    }

    public synchronized Game getGame(int gameId) {
        return games.get(gameId);
    }

    public static void addClient(Integer clientId){
        clients.add(clientId);
    }

    public static void removeClient(Integer clientId){
        clients.remove(clientId);
    }

    public static void generateTournament() throws Exception {
        Tournament tournament = new Tournament();

        for (Integer id : clients) {
            tournament.addPlayer(id);
        }

        List<Integer> players = tournament.getPlayersId();

        List<List<Integer>> pairings = tournament.generatePairings();
        Schedule schedule = new Schedule(pairings, 5, 10);
        Map<Integer, List<List<Integer>>> generatedSchedule = schedule.createSchedule();

//        for ( List<List<Integer>> pairing : generatedSchedule.values()) {
//            int gameId = this.createGame();
//            game = server.getGame(gameId);
//            game.setPlayer1Id(pairing.get(0));
//            game.setPlayer2Id(pairing.get(1));
//            if(game.isGameEnded())
//        }

        OutcomeGenerator outcomeGenerator = new OutcomeGenerator(generatedSchedule);
        Map<String, Integer> outcomes = outcomeGenerator.generateOutcomes();
        WinningSequence winningSequence = new WinningSequence(outcomes);
        List<Integer> sequence = winningSequence.findWinningSequence(players);

        System.out.println("Game Schedule: " + generatedSchedule);
        System.out.println("Game Outcomes: " + outcomes);
        System.out.println("Winning Sequence: " + sequence);
    }

    public void closeServer() {
        System.out.println("Server is closing on client request");
        System.exit(0);
    }

    public static void main ( String [] args ) throws IOException {
        GameServer server = new GameServer();
    }
}