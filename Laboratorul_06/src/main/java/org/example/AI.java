package org.example;

import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * an artifical inteligcence that plays the game for you
 */
public class AI {

    /**
     * finds the best move available
     * @param sticks  a list of all the sticks
     * @param activeNode the latest node chosen
     * @param nodes a list of all the nodes
     * @return the best node out there
     */
    public Optional<Point> findBestMove(Set<Stick> sticks, Point activeNode, Set<Point> nodes) {
        Set<Stick> connectedSticks = connectedStick(sticks, activeNode);
        Set<Point> localNodes = new HashSet<>(nodes);
        Set<Point> availableNodes = visitableNodes(connectedSticks, activeNode, localNodes);

        Optional<Point> bestMove = Optional.empty();
        int bestScore = Integer.MIN_VALUE;

        for (Point node : availableNodes) {

            localNodes.remove(node);

            if (isWinningMove(sticks, node, activeNode)){
                bestMove = Optional.of(node);
                break;
            }

            int score = 1000;
            score = evaluateMove(sticks, node, localNodes, false, score);

            localNodes.add(node);

            if (score > bestScore) {
                bestMove = Optional.of(node);
                bestScore = score;
            }
        }

        System.out.println("Final Score: " + bestScore);

        if(bestMove.isEmpty()){
            for (Stick stick : connectedSticks) {
                Point otherNode = stick.getStartingPoint().equals(activeNode) ?
                        stick.getFinishingPoint() : stick.getStartingPoint();
                if (nodes.contains(otherNode)) {
                    nodes.remove(otherNode);
                    return Optional.of(otherNode);
                }
            }
        }

        return bestMove;
    }

    private Set<Stick> connectedStick(Set<Stick> sticks, Point activeNode){
        return sticks.stream()
                     .filter(stick -> stick.getStartingPoint().equals(activeNode) || stick.getFinishingPoint().equals(activeNode))
                     .collect(Collectors.toSet());
    }

    private Set<Point> visitableNodes(Set<Stick> connectedSticks, Point activeNode, Set<Point> nodes){
        Set<Point> availableNodes = new HashSet<>();
        for (Stick stick : connectedSticks) {
            Point possibleNode = stick.getStartingPoint().equals(activeNode) ?
                    stick.getFinishingPoint() : stick.getStartingPoint();
            if (nodes.contains(possibleNode)) {
                availableNodes.add(possibleNode);
            }
        }
        return availableNodes;
    }

    private int evaluateMove(Set<Stick> sticks, Point activeNode, Set<Point> nodes, boolean isAIsTurn, int score) {

        score -= 10;
        Set<Point> localNodes = new HashSet<>(nodes);
        Set<Stick> connectedSticks = connectedStick(sticks, activeNode);
        Set<Point> availableNodes = visitableNodes(connectedSticks, activeNode, localNodes);

        int localMax = 0;

        for (Point node : availableNodes) {
            localNodes.remove(node);

            if (isWinningMove(sticks, node, activeNode)){
                if(!isAIsTurn)
                    return 0;
                else
                    return score;
            }
            int localScore = evaluateMove(sticks, node, localNodes, !isAIsTurn, score);

            if(localScore > localMax)
                localMax = localScore;

            localNodes.add(node);
        }

        return localMax;
    }

    private boolean isWinningMove(Set<Stick> allSticks, Point node, Point activeNode) {
        Set<Stick> possibleSticks = allSticks.stream()
                .filter(stick -> stick.getStartingPoint().equals(node) && !stick.getFinishingPoint().equals(activeNode)
                        || stick.getFinishingPoint().equals(node) && !stick.getStartingPoint().equals(activeNode) )
                .collect(Collectors.toSet());

        return possibleSticks.isEmpty();
    }
}

//public class AI {
//
//    public Optional<Point> findBestMove(Set<Stick> sticks, Set<Stick> connectedSticks, Point activeNode, Set<Point> nodes) {
//
//        Set<Point> availableNodes = new HashSet<>();
//
//        for (Stick stick : connectedSticks) {
//            Point possibleNode = stick.getStartingPoint().equals(activeNode) ?
//                    stick.getFinishingPoint() : stick.getStartingPoint();
//            if (nodes.contains(possibleNode)) {
//                availableNodes.add(possibleNode);
//            }
//        }
//
//        for (Point node : availableNodes) {
//            if (isWinningMove(sticks, node, connectedSticks, activeNode)) {
//                return Optional.of(node);
//            }
//        }
//
//        for (Stick stick : connectedSticks) {
//            Point otherNode = stick.getStartingPoint().equals(activeNode) ?
//                    stick.getFinishingPoint() : stick.getStartingPoint();
//            if (nodes.contains(otherNode)) {
//                nodes.remove(otherNode);
//                return Optional.of(otherNode);
//            }
//        }
//        return Optional.empty();
//    }
//
//    private boolean isWinningMove(Set<Stick> allSticks, Point node, Set<Stick> sticks, Point activeNode) {
//        Set<Stick> possibleSticks = allSticks.stream()
//                .filter(stick -> stick.getStartingPoint().equals(node) && !stick.getFinishingPoint().equals(activeNode)
//                        || stick.getFinishingPoint().equals(node) && !stick.getStartingPoint().equals(activeNode) )
//                .collect(Collectors.toSet());
//
//        return possibleSticks.isEmpty();
//    }
//}