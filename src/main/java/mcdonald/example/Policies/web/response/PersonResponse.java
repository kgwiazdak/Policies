package mcdonald.example.Policies.web.response;

import java.util.Objects;

public class PersonResponse {
    private final int id;
    private final int age;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int phoneNumber;

    @Override
    public String toString() {
        return "PersonResponse{" +
            "id=" + id +
            ", age=" + age +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", address='" + address + '\'' +
            ", phoneNumber=" + phoneNumber +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonResponse that = (PersonResponse) o;
        return id == that.id && age == that.age && phoneNumber == that.phoneNumber && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, firstName, lastName, address, phoneNumber);
    }

    public PersonResponse(int id, int age, String firstName, String lastName, String address, int phoneNumber) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
