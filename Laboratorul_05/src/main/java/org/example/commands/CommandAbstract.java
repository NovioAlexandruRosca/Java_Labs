package org.example.commands;

/**
 * This abstract class stores the repository necesarry to run their commands.
 */
public abstract class CommandAbstract {

    /**
     * The document repository
     */
    private final String repositoryPath;


    CommandAbstract(String repositoryPath){
        this.repositoryPath = repositoryPath;
    }

    protected String getRepositoryPath(){
        return repositoryPath;
    }
}
