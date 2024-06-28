package org.example.datatypes;

import org.example.datatypes.ID;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the Id of every employee of the repository
 */
public class DataModel {

    /**
     * the list of id's
     */
    private final List<ID> idList;

    public DataModel() {
        this.idList = new ArrayList<>();
    }

    public List<ID> getIdList() {
        return idList;
    }

    public void addId(ID id) {
        this.idList.add(id);
    }
}