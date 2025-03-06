package rental;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rental {
    private String id;
    private User user;
    private Item item;
    private int days;
    private double totalPrice;
    private String rentalDate;

    public Rental() {}

    @JsonCreator
    public Rental(@JsonProperty("id") String id,
                  @JsonProperty("user") User user,
                  @JsonProperty("item") Item item,
                  @JsonProperty("days") int days,
                  @JsonProperty("totalPrice") double totalPrice,
                  @JsonProperty("rentalDate") String rentalDate) {
        this.id = id;
        this.user = user;
        this.item = item;
        this.days = days;
        this.totalPrice = totalPrice;
        this.rentalDate = rentalDate;
    }

    public String getId() { return id; }
    public User getUser() { return user; }
    public Item getItem() { return item; }
    public int getDays() { return days; }
    public double getTotalPrice() { return totalPrice; }
    public String getRentalDate() { return rentalDate; }
}