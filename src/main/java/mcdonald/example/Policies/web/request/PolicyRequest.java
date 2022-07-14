package mcdonald.example.Policies.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Validated
public class PolicyRequest {

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

    @NotBlank
    private String name;
    private double price;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public PolicyRequest(@JsonProperty(value="name") String name,
                         @JsonProperty(value="price") double price,
                         @JsonProperty(value="firstName") String firstName,
                         @JsonProperty(value="lastName") String lastName) {
        this.name = name;
        this.price = price;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolicyRequest that = (PolicyRequest) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(name, that.name) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, price, firstName, lastName);
    }
}
