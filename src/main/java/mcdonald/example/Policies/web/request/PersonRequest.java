package mcdonald.example.Policies.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Validated
public class PersonRequest {
    private int age;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;
    private int phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRequest that = (PersonRequest) o;
        return age == that.age && phoneNumber == that.phoneNumber && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, firstName, lastName, address, phoneNumber);
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

    public PersonRequest(@JsonProperty(value = "age") int age,
                         @JsonProperty(value = "firstName") String firstName,
                         @JsonProperty(value = "lastName") String lastName,
                         @JsonProperty(value = "address") String address,
                         @JsonProperty(value = "phoneNumber") int phoneNumber) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
