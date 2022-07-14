package mcdonald.example.Policies.domain;

public class Policy {
    private int id;
    private String name;
    private double price;

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public Policy() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Policy(int id, String name, double price, String firstName, String lastName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
