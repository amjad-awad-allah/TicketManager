package models;

public class Admin extends Benutzer {
    public Admin(int id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public String toString() {
        return getName();
    }

}
