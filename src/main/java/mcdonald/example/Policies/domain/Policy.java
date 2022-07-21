package mcdonald.example.Policies.domain;

import java.util.Objects;

public class Policy implements Entity {
    private int id;
    private String name;
    private double price;

    private String firstName;
    private String lastName;

    public Policy() {
    }

    public Policy(int id, String name, double price, String firstName, String lastName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.firstName = firstName;
        this.lastName = lastName;
    }

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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Policy policy = (Policy) o;
        return id == policy.id && Double.compare(policy.price, price) == 0 && Objects.equals(name, policy.name) && Objects.equals(firstName, policy.firstName) && Objects.equals(lastName, policy.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, firstName, lastName);
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
}
