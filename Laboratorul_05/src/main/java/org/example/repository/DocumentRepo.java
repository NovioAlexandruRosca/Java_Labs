package org.example.repository;

import org.example.datatypes.DataModel;
import org.example.datatypes.ID;
import org.example.enums.Display;
import org.example.utility.ServiceException;
import org.example.utility.Utility;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

/**
 * Represents a person with a name and an ID.
 */
record Person(String name, int id) {}

/**
 * Represents a document with a name and a format.
 */
record Document(String name, String format) {}

/**
 * A class that gives functionality over a master directory which has folders for each of it's employees, such as printing the
 * documents available and creating reports
 */
public class DocumentRepo {

    /**
     * Represents the master directory for document storage.
     */
    private final File masterDir;

    /** Logger for logging information. */
    private static final Logger logger = Logger.getLogger(DocumentRepo.class.getName());

    /**
     * Constructs a DocumentRepo object with the specified master directory path
     * if it can't be created it throws a custom exception
     *
     * @param masterDirectoryPath the path to the master directory
     */
    public DocumentRepo(String masterDirectoryPath) {
        this.masterDir = new File(masterDirectoryPath);

        try {

            if (!isValidPath(masterDirectoryPath))
                throw new IllegalArgumentException();

            if (!this.masterDir.exists()) {
                throw new ServiceException("The master directory doesnt exists.");
            }
        } catch (ServiceException e) {
            logger.log(Level.INFO, Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
            System.exit(0);
        } catch (IllegalArgumentException e) {
            logger.log(Level.INFO, Utility.textColoring("The path " + masterDirectoryPath + " is invalid", Utility.ansiEscapeCodes.RED));
            System.exit(0);
        }
    }


    public static boolean isValidPath(String path) {
        Path filePath = Paths.get(path);

        return filePath.isAbsolute() && filePath.normalize().equals(filePath);
    }

    /**
     * Displays the content of the repository in an organized way
     */
    public DataModel displayRepo(Display display) {
        File[] listOfDirectories = masterDir.listFiles();
        DataModel dataMode = new DataModel();

        try {
            if (listOfDirectories == null)
                throw new ServiceException("The directory is empty");

            for (File personDirectory : listOfDirectories) {
                if (personDirectory.isDirectory()){

                    ID id = new ID(Integer.parseInt(personDirectory.getName()));
                    List<String> listOfFiles = new ArrayList<>();

                    StringBuilder message = new StringBuilder();
                    message.append("\nEmployee: ").append(personDirectory.getName()).append('\n');

                    File[] documents = personDirectory.listFiles();
                    if (documents == null) {
                        throw new ServiceException("Error at reading the documents of the employee with the ID: " + personDirectory.getName());
                    } else if (documents.length == 0) {
                        message.append("  * No documents found");
                    } else {
                        for (File document : documents) {
                            if (document.isDirectory()) {
                                try {
                                    walkFolder(document.getPath(), message, listOfFiles);
                                } catch (IOException e) {
                                    throw new ServiceException("There is an error walking a subdirectory of the employee with the ID: " + personDirectory.getName());
                                }
                            } else {
                                message.append(" * Document: ").append(document.getName()).append('\n');
                                listOfFiles.add(document.getName());
                            }
                        }
                    }
                    if(display == Display.DisplayOn)
                        logger.log(Level.INFO, Utility.textColoring(message.toString(), Utility.ansiEscapeCodes.GREEN));

                    id.setFiles(listOfFiles);
                    dataMode.addId(id);

                }
            }
        }catch(ServiceException e){
            logger.log(Level.SEVERE, Utility.textColoring(e.getMessage(), Utility.ansiEscapeCodes.RED));
        }

        return dataMode;
    }

    /**
     * Recursively walks through a folder and its subfolders, appending documents to the provided StringBuilder
     *
     * @param folderPath the path to the folder to walk through
     * @param message the StringBuilder to append the document information to
     * @throws IOException if an I/O error occurs
     */
    public static void walkFolder(String folderPath, StringBuilder message, List<String> listOfFiles) throws IOException {
        Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                message.append(" * Document: ").append(file.getFileName()).append('\n');
                listOfFiles.add(String.valueOf(file.getFileName()));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public String getName(){
        return masterDir.getPath();
    }
}