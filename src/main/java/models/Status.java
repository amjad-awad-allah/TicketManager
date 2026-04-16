package models;

public enum Status {
    Offen,
    In_Bearbeitung,
    Geschlossen;

    @Override
    public String toString() {
        return name().replace("_", " ");
    }
}
