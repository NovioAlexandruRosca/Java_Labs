package org.example.datatypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Every ID has the id of an employee and a set of files associate with it
 */
public class ID {

    /**
     * the id of an employee
     */
    private int id;

    /**
     * a list of files
     */
    private List<String> files;

    public ID(int id) {
        this.id = id;
        this.files = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}