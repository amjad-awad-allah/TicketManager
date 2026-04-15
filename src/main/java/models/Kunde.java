package models;

public class Kunde extends Benutzer {
    private String username;
    private String phone;
    private String website;
    private Address address;
    private Company company;

    public Kunde(int id, String name, String email) {
        super(id, name, email);
        this.username = "";
        this.phone = "";
        this.website = "";
        this.address = null;
        this.company = null;
    }

    public Kunde(int id, String name, String email, String username, String phone, String website,
            Address address, Company company) {
        super(id, name, email);
        this.username = username;
        this.phone = phone;
        this.website = website;
        this.address = address;
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return getName();
    }
}
