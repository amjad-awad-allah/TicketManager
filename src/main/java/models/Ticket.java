package models;

import java.util.Date;

import exceptions.InvalidDataException;

public class Ticket implements Identifiable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titel;
    private String beschreibung;
    private Priority priority;
    private Date datum;
    private Status status;
    private Kunde kunde;
    private Admin admin;

    public Ticket(int id, String titel, String beschreibung, Priority priority, Date datum, Status status,
            Kunde kunde, Admin admin) {
        if (titel == null || titel.trim().isEmpty()) {
            throw new InvalidDataException("Titel cannot be empty");
        }
        this.id = id;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.priority = priority;
        this.datum = datum;
        this.status = status;
        this.kunde = kunde;
        this.admin = admin;
    }

    public Ticket(String titel, String beschreibung, Priority priority, Kunde kunde) {
        this(0, titel, beschreibung, priority, new Date(), Status.Open, kunde, null);
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitel() {

        return titel;
    }

    public void setTitel(String titel) {
        if (titel == null || titel.trim().isEmpty()) {
            throw new InvalidDataException("Titel cannot be empty");
        }
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        if (beschreibung == null) {
            throw new InvalidDataException("Beschreibung cannot be null");
        }
        this.beschreibung = beschreibung;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        if (priority == null) {
            throw new InvalidDataException("Priority cannot be null");
        }
        this.priority = priority;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        if (datum == null) {
            throw new InvalidDataException("Datum cannot be null");
        }
        this.datum = datum;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new InvalidDataException("Status cannot be null");
        }
        this.status = status;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        if (kunde == null) {
            throw new InvalidDataException("Kunde cannot be null");
        }
        this.kunde = kunde;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        // Admin can be null for new tickets
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", titel=" + titel + ", beschreibung=" + beschreibung + ", priority=" + priority
                + ", datum=" + datum + ", status=" + status + ", kunde=" + kunde.getName() + ", admin="
                + (admin != null ? admin.getName() : "null") + "]";
    }
}
