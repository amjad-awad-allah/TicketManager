package models;

import java.io.Serializable;

//search by ID, update
public interface Identifiable extends Serializable {
    int getId();
}
