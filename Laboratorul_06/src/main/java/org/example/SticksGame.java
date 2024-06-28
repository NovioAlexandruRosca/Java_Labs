package org.example;

import utility.Utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class representing the game itself with it s logic
 */
public class SticksGame extends JFrame {

    /**
     * Logger for logging information
     * */
    private static final Logger logger = Logger.getLogger(SticksGame.class.getName());

    /**
     * the x dimension
     */
    private JSpinner dimensionsInput1;

    /**
     * the y dimension
     */
    private JSpinner dimensionsInput2;
    private CanvasPanel canvasPanel;

    /**
     * the node that was the last chosen
     */
    private Point activeNode;

    /**
     * pvp or pve mode
     */
    private boolean realPlayer = true;


    public SticksGame() {

        generateGameData();
        generateCanvas();
        gameLogic();
        generateConfigurationPanel();
        generateControlPanel();

        setVisible(true);
    }

    /**
     * Generates the canvas panel and adds it to the frame.
     */
    public void generateCanvas(){

        this.canvasPanel = new CanvasPanel();
        canvasPanel.setBackground(Color.WHITE);
        add(canvasPanel, BorderLayout.CENTER);
    }

    /**
     * Manages the game logic, if the game ends and if the player is real or an AI
     */
    public void gameLogic(){

        final boolean[] playerTurn = {true};

        canvasPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(activeNode.x() == -1)
                    playerTurn[0] = true;

                int mouseX = e.getX();
                int mouseY = e.getY();

                Optional<Point> node;

                node = canvasPanel.isValidNode(mouseX, mouseY, activeNode);

                nextMove(node, playerTurn);

                if (!playerTurn[0] && !realPlayer){
                    node = canvasPanel.generateAiMove(activeNode);
                    nextMove(node, playerTurn);
                }

            }
        });
    }

    /**
     * Checks if the next move is possible and if so it Makes the next move in the game.
     * @param node The node representing the move.
     * @param playerTurn The current player's turn.
     */
    private void nextMove(Optional<Point> node, boolean[] playerTurn){
        if(node.isPresent()) {
            activeNode = node.get();
            canvasPanel.drawRock(playerTurn[0], node.get(), canvasPanel.getCircleRadius());

            if(canvasPanel.gameIsOver(activeNode)){
                StringBuilder winningMessage = new StringBuilder();
                winningMessage.append("Congratulations ");
                if(playerTurn[0])
                    winningMessage.append("Player 1!");
                else
                    winningMessage.append("Player 2!");
                JOptionPane.showMessageDialog(null, winningMessage.toString(), "Victory", JOptionPane.INFORMATION_MESSAGE);
            }

            playerTurn[0] = !playerTurn[0];
        }
    }

    /**
     * Generates the control panel with various buttons such as save load and screenshot
     */
    private void generateControlPanel(){
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.GRAY);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        controlPanelExitButton(controlPanel);
        controlPanelSaveButton(controlPanel);
        controlPanelLoadButton(controlPanel);
        controlPanelScreenshotButton(controlPanel);
        controlPanelGameMode(controlPanel);

        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the button to switch game mode between Player vs Player and Player vs Environment
     * @param controlPanel The panel to add the button to.
     */
    private void controlPanelGameMode(JPanel controlPanel) {
        JButton gameModeButton = new JButton("PvP");

        gameModeButton.addActionListener(e -> {
            if (realPlayer){
                realPlayer = false;
                gameModeButton.setText("PvE");
            }else{
                realPlayer = true;
                gameModeButton.setText("PvP");
            }
        });
        controlPanel.add(gameModeButton);
    }

    private void controlPanelScreenshotButton(JPanel controlPanel) {

        JButton screenshotButton = new JButton("Screenshot");

        screenshotButton.addActionListener(e -> {
            try {
                java.awt.Point panelLocation = canvasPanel.getLocationOnScreen();
                Dimension panelSize = canvasPanel.getSize();
                Rectangle captureRect = new Rectangle(panelLocation.x, panelLocation.y, panelSize.width, panelSize.height);

                Robot robot = new Robot();
                BufferedImage screenshot = robot.createScreenCapture(captureRect);

                File screenshotFile = new File("./screenshots/boardImage" + indexOfScreenshot() + ".png");
                ImageIO.write(screenshot, "PNG", screenshotFile);
                logger.log(Level.INFO, Utility.textColoring("A screenshot of the board has been saved here: " + screenshotFile.getPath(), Utility.ansiEscapeCodes.GREEN));

                Desktop.getDesktop().open(screenshotFile);
            }catch (FileNotFoundException error){
                logger.log(Level.INFO, Utility.textColoring("The given directory doesnt exists", Utility.ansiEscapeCodes.GREEN));
            } catch (IOException error) {
                logger.log(Level.INFO, Utility.textColoring("There is an I/O error", Utility.ansiEscapeCodes.GREEN));
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
        });
        controlPanel.add(screenshotButton);
    }

    private int indexOfScreenshot() throws FileNotFoundException{
        String directoryPath = "./screenshots";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            int numberOfFiles = 0;

            if(files != null)
                numberOfFiles = files.length;

            return numberOfFiles;
        } else {
            throw new FileNotFoundException("The given directory doesnt exists");
        }
    }

    private void controlPanelLoadButton(JPanel controlPanel) {
        JButton loadButton = new JButton("Load");

        loadButton.addActionListener(e -> {
            try {
                logger.log(Level.INFO, Utility.textColoring("Loading File...", Utility.ansiEscapeCodes.GREEN));
                canvasPanel = CanvasPanel.loadFromFile("./serializedCanva/state.ser");
                logger.log(Level.INFO, Utility.textColoring("Loading Finished", Utility.ansiEscapeCodes.GREEN));

                canvasPanel.repaint();

            } catch (ClassNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        controlPanel.add(loadButton);
    }

    private void controlPanelSaveButton(JPanel controlPanel) {
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> {
            try {
                canvasPanel.saveToFile("./serializedCanva/state.ser");
                logger.log(Level.INFO, Utility.textColoring("File has been saved here: " + "./serializedCanva/state.ser", Utility.ansiEscapeCodes.GREEN));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        controlPanel.add(saveButton);
    }

    private void controlPanelExitButton(JPanel controlPanel) {
        JButton exitButton = new JButton("Exit");

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        controlPanel.add(exitButton);
    }

    /**
     * Generates the configuration panel with the input fields
     */
    private void generateConfigurationPanel(){
        JPanel configurationPanel = new JPanel();
        configurationPanel.setBackground(Color.GRAY);
        configurationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel gridSizeLabel = new JLabel("Grid size:");

        SpinnerModel spinnerModel1 = new SpinnerNumberModel(10, 1, 20, 1);
        this.dimensionsInput1 = new JSpinner(spinnerModel1);
        SpinnerModel spinnerModel2 = new SpinnerNumberModel(10, 1, 20, 1);
        this.dimensionsInput2 = new JSpinner(spinnerModel2);

        configurationPanel.add(gridSizeLabel);
        configurationPanel.add(dimensionsInput1);
        configurationPanel.add(dimensionsInput2);

        configurationPanelButton(configurationPanel, dimensionsInput1, dimensionsInput2);

        add(configurationPanel, BorderLayout.NORTH);
    }

    private void configurationPanelButton(JPanel configurationPanel, JSpinner dimensionsInput1, JSpinner dimensionsInput2){
        JButton createButton = new JButton("Create");

        createButton.addActionListener(e -> {
            int gridSizeX = (int) dimensionsInput1.getValue();
            int gridSizeY = (int) dimensionsInput2.getValue();
            canvasPanel.setGridSizeX(gridSizeX);
            canvasPanel.setGridSizeY(gridSizeY);
            activeNode = new Point(-1, -1);
            canvasPanel.repaint();
        });

        configurationPanel.add(createButton);
    }

    /**
     * Generates initial data for the game window
     */
    private void generateGameData(){
        setTitle("SticksGame");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        activeNode = new Point(-1, -1);
    }

    /**
     * The method that starts the game
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SticksGame::new);
    }
}