package rental;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private String id;
    private String name;
    private String category;
    private String history;
    private double pricePerDay;
    private boolean available;

    public Item() {}

    @JsonCreator
    public Item(@JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("category") String category,
                @JsonProperty("history") String history,
                @JsonProperty("pricePerDay") double pricePerDay,
                @JsonProperty("available") boolean available) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.history = history;
        this.pricePerDay = pricePerDay;
        this.available = available;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getHistory() { return history; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
