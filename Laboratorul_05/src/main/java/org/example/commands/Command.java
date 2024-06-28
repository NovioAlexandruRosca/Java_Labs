package org.example.commands;

import org.example.utility.ServiceException;

import java.io.IOException;

/**
 * The base of all commands
 */
public interface Command {

    /*
    * The run method starts the execution of the command
     */
    void run() throws ServiceException, IOException;
}
