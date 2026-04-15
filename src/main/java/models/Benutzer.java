package models;

import exceptions.InvalidDataException;

public abstract class Benutzer implements Identifiable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String email;

    public Benutzer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Email cannot be empty");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "Benutzer [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}
