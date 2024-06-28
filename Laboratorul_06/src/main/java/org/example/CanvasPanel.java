package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The CanvasPanel class represents the panel where the game grid and sticks are drawn together with the lines that make the
 * map of the game
 */
class CanvasPanel extends JPanel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int gridSizeX;
    private int gridSizeY;
    private final Set<Stick> sticks;

    private final Set<Point> nodes;

    private final Map<Point,Boolean> selectedNode;

    CanvasPanel(){
        this.gridSizeY = 10;
        this.gridSizeX = 10;
        this.sticks = new HashSet<>();
        this.nodes = new HashSet<>();
        this.selectedNode = new HashMap<>();
    }

    /**
     * creates the game by making the field and everything else that s part of the main frame
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        sticks.clear();
        nodes.clear();
        selectedNode.clear();

        int cellWidth = canvasWidth / (gridSizeX + 1);
        int cellHeight = canvasHeight / (gridSizeY + 1);

        int circleRadius = (cellWidth + cellHeight) / 4;

        for (int i = 1; i <= gridSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, cellHeight, x, canvasHeight - cellHeight);

            for (int j = 1; j <= gridSizeY; j++) {
                nodes.add(new Point(x, j * cellHeight));
                g.drawOval(x - circleRadius / 2, j * cellHeight - circleRadius / 2, circleRadius, circleRadius);
            }

            g.setColor(Color.WHITE);
            g.drawLine(x, gridSizeY * cellHeight, x, gridSizeY * cellHeight + circleRadius / 2);
            g.setColor(Color.BLACK);

        }
        for (int j = 1; j <= gridSizeY; j++) {
            int y = j * cellHeight;
            g.drawLine(cellWidth, y, canvasWidth - cellWidth, y);

            g.setColor(Color.WHITE);
            g.drawLine(gridSizeX * cellWidth, y, gridSizeX * cellWidth + circleRadius / 2, y);
            g.setColor(Color.BLACK);
        }

        for (int i = 0; i < ((gridSizeX - 1) * gridSizeY + (gridSizeY - 1) * gridSizeX) / 4 + ((gridSizeX - 1) * gridSizeY + (gridSizeY - 1) * gridSizeX) / 2; i++) {
            generateSticks(g, cellWidth, cellHeight);
        }

    }

    /**
     * generates a bunch of sticks
     * @param g   the graphics object necesarry to drawing objects
     * @param cellWidth  the width of a cell
     * @param cellHeight the height of a cell
     */
    private void generateSticks(Graphics g, int cellWidth, int cellHeight){

        if(gridSizeX == 1 && gridSizeY == 1)
            return;
        Random random = new Random();
        g.setColor(Color.RED);

        int x = random.nextInt(gridSizeX) + 1;
        int y = random.nextInt(gridSizeY) + 1;

        boolean lineDrawn = false;
        while(!lineDrawn) {
            int direction = random.nextInt(4);

            switch (direction) {
                case 0 -> {
                    if (y < gridSizeY)
                        lineDrawn = true;
                }
                case 1 -> {
                    if (x < gridSizeX)
                        lineDrawn = true;
                }
                case 2 -> {
                    if (y > 1)
                        lineDrawn = true;
                }
                case 3 -> {
                    if (x > 1)
                        lineDrawn = true;
                }
            }
            if(lineDrawn) {
                final Point point1 = new Point(x * cellWidth, y * cellHeight);
                switch (direction) {
                    case 0 -> {
                        drawThickLine(g, x * cellWidth, y * cellHeight, x * cellWidth, (y + 1) * cellHeight, true);
                        sticks.add(new Stick(point1, new Point(x * cellWidth, (y + 1) * cellHeight)));
                    }
                    case 1 -> {
                        drawThickLine(g, x * cellWidth, y * cellHeight, (x + 1) * cellWidth, y * cellHeight, false);
                        sticks.add(new Stick(point1, new Point((x + 1) * cellWidth, y * cellHeight)));
                    }
                    case 2 -> {
                        drawThickLine(g, x * cellWidth, y * cellHeight, x * cellWidth, (y - 1) * cellHeight, true);
                        sticks.add(new Stick(point1, new Point(x * cellWidth, (y - 1) * cellHeight)));
                    }
                    case 3 -> {
                        drawThickLine(g, x * cellWidth, y * cellHeight, (x - 1) * cellWidth, y * cellHeight, false);
                        sticks.add(new Stick(point1, new Point((x - 1) * cellWidth, y * cellHeight)));
                    }
                }
            }
        }
    }

    /**
     * this generates possible places to set a rock
     */
    private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, boolean up){
        if(up) {
            for (int i = x1 - 2; i <= x1 + 2; i++) {
                g.drawLine(i, y1, i, y2);
            }
        }else {
            for (int i = y1 - 2; i <= y1 + 2; i++) {
                g.drawLine(x1, i, x2, i);
            }
        }
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public void setGridSizeX(int gridSizeX) {
        this.gridSizeX = gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public void setGridSizeY(int gridSizeY) {
        this.gridSizeY = gridSizeY;
    }

    public int getCircleRadius(){
        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        int cellWidth = canvasWidth / (gridSizeX + 1);
        int cellHeight = canvasHeight / (gridSizeY + 1);

        return (cellWidth + cellHeight) / 4;
    }

    /**
     * checks if the given node has any neighbors that can be chosen
     * @param x  x pos
     * @param y  y pos
     * @param activeNode the latest chosen nodes
     * @return returns the node if there is one or nothing
     */
    public Optional<Point> isValidNode(int x, int y, Point activeNode){

        int r = getCircleRadius() / 2;

        Optional<Point> node = nodes.stream().filter(point -> (x - point.x()) * (x - point.x()) + (y - point.y()) * (y - point.y()) < r * r).findFirst();

        if(node.isPresent()) {
            Point concreteNode = node.get();

            Set<Stick> possibleSticks = sticks.stream()
                                              .filter(stick -> stick.getStartingPoint().equals(concreteNode) || stick.getFinishingPoint().equals(concreteNode)).collect(Collectors.toSet());

            if(!possibleSticks.isEmpty()) {
                if(activeNode.x() == -1 || possibleSticks.stream().anyMatch(stick -> stick.getStartingPoint().equals(activeNode) || stick.getFinishingPoint().equals(activeNode))) {

                    nodes.remove(concreteNode);
                    return node;
                }
            }
        }
        return Optional.empty();

    }

    /**
     * generates a new move in the pve game
     * @param activeNode the latest node
     * @return  a node if there is one or nothing
     */
    public Optional<Point> generateAiMove(Point activeNode){
        Optional<Point> bestMove = new AI().findBestMove(sticks, activeNode, nodes);
        bestMove.ifPresent(nodes::remove);
        return bestMove;
    }

    /**
     * checks if the game is over
     * @param activeNode the latest node
     * @return true if yes false if not
     */
    public boolean gameIsOver(Point activeNode){

        return nodes.stream()
                     .noneMatch(node -> sticks.stream()
                          .anyMatch(stick -> (stick.getStartingPoint().equals(node) && stick.getFinishingPoint().equals(activeNode))
                                || (stick.getStartingPoint().equals(activeNode) && stick.getFinishingPoint().equals(node))));
}

    /**
     * draws a rockthat s either blue or red
     * @param playerTurn
     * @param node
     * @param circleRadius
     */
    public void drawRock(boolean playerTurn, Point node, int circleRadius){
//        Graphics g = this.getGraphics();
//
//        if (playerTurn){
//            g.setColor(Color.BLUE);
//        }else {
//            g.setColor(Color.GREEN);
//        }
//
//        g.fillOval(node.x() - circleRadius / 2, node.y() - circleRadius / 2, circleRadius, circleRadius);
//        selectedNode.put(node, playerTurn);
//
//        g.dispose();
    }

    /**
     * uses serialization to save the object
     */
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    /**
     * loads the object from a file
     */
    public static CanvasPanel loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (CanvasPanel) in.readObject();
        }
    }
}

class Stick implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final Point point1;
    private final Point point2;

    Stick(Point point1, Point point2){
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getStartingPoint(){
        return point1;
    }

    public Point getFinishingPoint(){
        return point2;
    }
}