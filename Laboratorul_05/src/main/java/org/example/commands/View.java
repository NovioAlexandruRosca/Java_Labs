package org.example.commands;

import org.example.utility.ServiceException;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The class opens a file with the default app
 */
public class View extends CommandAbstract implements Command{

    /**
     * The document repository
     */
    private final String filePath;

    public View(String filePath, String repositoryPath) {
        super(repositoryPath);
        this.filePath = filePath;
    }

    /**
     * Opens a file with the default app on windows
     * @throws ServiceException   the app is not supported on windows
     * @throws IOException the file cant be fined
     */
    @Override
    public void run() throws ServiceException, IOException {
        File file = new File(filePath);

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();

            if (file.exists()) {
                desktop.open(file);
            } else {
                throw new FileNotFoundException("File does not exist: " + filePath);
            }
        } else {
            throw new ServiceException("The file is not supported on windows");
        }
    }
}
