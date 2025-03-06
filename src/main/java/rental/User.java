package rental;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String name;
    private String email;
    private List<Rental> rentals;

    public User() {}

    @JsonCreator
    public User(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("email") String email,
                @JsonProperty("rentals") List<Rental> rentals) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.rentals = rentals;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Rental> getRentals() { return rentals; }
}
