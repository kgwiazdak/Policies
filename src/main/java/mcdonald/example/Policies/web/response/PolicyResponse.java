package mcdonald.example.Policies.web.response;

import java.util.Objects;

public class PolicyResponse {
    private final int id;
    private final String name;
    private final double price;
    private final String firstName;
    private final String lastName;

    public PolicyResponse(int id, String name, double price, String firstName, String lastName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.firstName = firstName;
        this.lastName = lastName;
    }



    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyResponse that = (PolicyResponse) o;
        return id == that.id && Double.compare(that.price, price) == 0 && name.equals(that.name) && firstName.equals(that.firstName) && lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, firstName, lastName);
    }

    @Override
    public String toString() {
        return "PolicyResponse{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
    }
}

