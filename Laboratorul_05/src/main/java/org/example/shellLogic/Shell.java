package org.example.shellLogic;

import org.example.repository.DocumentRepo;
import org.example.commands.Command;
import org.example.commands.Export;
import org.example.commands.Report;
import org.example.commands.View;
import org.example.utility.ServiceException;
import org.example.utility.Utility;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * A shell that can be used to run different commands on an employee repository
 */
public class Shell {

    /**
     * the document repository
     */
    private final DocumentRepo repository;

    public Shell(DocumentRepo repository){
        this.repository = repository;
        System.out.println(Utility.textColoring("  Thank you for choosing our file manager", Utility.ansiEscapeCodes.GREEN));
        System.out.println(Utility.textColoring("  The repository you will be browsing is located at this path -> " + repository.getName() + "\n", Utility.ansiEscapeCodes.GREEN));

        displayShell();
    }

    /**
     * Displays the folders and files associated to an employee
     */
    private void displayShell(){
        System.out.println(Utility.textColoring(" >  View  [folder_Name + file_Name] (Opens a specific file)", Utility.ansiEscapeCodes.YELLOW));
        System.out.println(Utility.textColoring(" > Report (Create an HTML file with every employee's data)", Utility.ansiEscapeCodes.YELLOW));
        System.out.println(Utility.textColoring(" > Export (Export the repository as JSON)", Utility.ansiEscapeCodes.YELLOW));

        Scanner scanner = new Scanner(System.in);

        System.out.print("\u001B[31m" + " > ");

        String commandName;
        String path;
        try {

            String[] result = extractTwoWords(scanner.nextLine());
            System.out.print("\u001B[0m");

            commandName = result[0];
            path = result[1];

            run_command(commandName, path);

        }catch(ServiceException | IllegalArgumentException e){
            System.out.println(Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            displayShell();
            return;
        }catch(FileNotFoundException e){
            System.out.println(Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            displayShell();
            return;
        }catch(IOException e){
            System.out.println(Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            displayShell();
            return;
        }finally {
            scanner.close();
        }
    }

    /**
     * Runs ones of the predefined command
     * @param commandName  the name of a command
     * @param path   the path to a file that you want to open
     * @throws ServiceException  when the command cant be ran this will be thrown
     * @throws IllegalArgumentException  the given command name or path is incorrect
     * @throws IOException  the given file cant be opened
     */
    private void run_command(String commandName, String path) throws ServiceException, IllegalArgumentException,IOException {

        if(path != null) {
            Path filePath = Paths.get(path);

            if (!filePath.isAbsolute() || !filePath.normalize().equals(filePath)) {
                throw new IllegalArgumentException("Invalid path!");
            }
        }

        Command command = null;

        switch (commandName) {
            case "view"   -> { command = new View(path, repository.getName());}
            case "report" -> { command = new Report(repository.getName(), repository);}
            case "export" -> { command = new Export(repository.getName(), repository);}
        }

        if(command == null){
            throw new ServiceException("Error at running command");
        }else {
            command.run();
        }
    }

    /**
     * Extracts the command name and possibly the path of the file that you d want to open otherwise an error is thrown if the number of words given is more than expected
     * @param input  the given command and or path
     * @return  a command and a path (lowercased)
     * @throws IllegalArgumentException the word's format is incorrect or there are too many words or not enough
     */
    private String[] extractTwoWords(String input) throws IllegalArgumentException {

        String commandName = null;
        String correctPath = null;

        int firstSpaceIndex = input.indexOf(' ');
        if (firstSpaceIndex != -1) {
            commandName = input.substring(0, firstSpaceIndex).toLowerCase();

            if (!commandName.equals("view")) {
                throw new IllegalArgumentException("Invalid command name! The allowed commands are : view, report or export");
            }

            correctPath = input.substring(firstSpaceIndex + 1);

            if(correctPath.charAt(0) == '\\') {
                correctPath = repository.getName() + correctPath;
            }

        } else if(input.equalsIgnoreCase("view")){
            throw new IllegalArgumentException("Too few arguments provided");
        } else{
            if(!input.equalsIgnoreCase("report") && !input.equalsIgnoreCase("export")){
                throw new IllegalArgumentException("Invalid command name! The allowed commands are : view, report or export");
            }
            commandName = input.toLowerCase();
        }

        return new String[]{commandName, correctPath};
    }
}
