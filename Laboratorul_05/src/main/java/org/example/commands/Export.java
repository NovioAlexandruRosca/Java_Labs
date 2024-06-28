package org.example.commands;

import org.example.datatypes.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.Display;
import org.example.repository.DocumentRepo;

import java.io.IOException;
import java.io.File;

/**
 * Exports the repository as a a json file using jackson
 */
public class Export extends CommandAbstract implements Command{

    /**
     * The document repository
     */
    private final DocumentRepo repository;

    public Export(String repositoryPath, DocumentRepo repository) {
        super(repositoryPath);
        this.repository = repository;
    }


    /**
     * Creates a saves the depository in json format
     * @throws IOException If the file where the depository will be saved cant be used it s going to throw this error
     */
    @Override
    public void run() throws IOException {
        DataModel dataModel = repository.displayRepo(Display.DisplayOff);;
        String filePath = repository.getName() + "repository.json";
        exportToJSON(dataModel, filePath);
    }

    /**
     * Transforms the repository in a JSON file
     * @param dataModel the repository itself represented in a more usable way
     * @param filePath  the path to the file where the repository will be saved
     * @throws IOException  If the file where the depository will be saved cant be used it s going to throw this error
     */
    public void exportToJSON(DataModel dataModel, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), dataModel);
        System.out.println("Repository exported to JSON file successfully.");
    }
}
