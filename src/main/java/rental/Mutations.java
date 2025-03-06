package rental;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import utils.JsonUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map;

public class Mutations {

    //private static final String USERS_DATA_PATH = "/data/users.json";
    //private static final String ITEMS_DATA_PATH = "/data/items.json";


    private static final List<User> users = JsonUtils.loadListFromJsonFile("/data/users.json", User[].class);
    private static final List<Item> items = JsonUtils.loadListFromJsonFile("/data/items.json", Item[].class);

    public static DataFetcher<Rental> rentItemFetcher() {
        return environment -> {
            Map<String, Object> input = environment.getArgument("input");
            String userId = (String) input.get("userId");
            String itemId = (String) input.get("itemId");
            int days = (int) input.get("days");

            Optional<User> userOpt = users.stream().filter(u -> u.getId().equals(userId)).findFirst();
            Optional<Item> itemOpt = items.stream().filter(i -> i.getId().equals(itemId) && i.isAvailable()).findFirst();

            if (userOpt.isEmpty() || itemOpt.isEmpty()) {
                throw new RuntimeException("Invalid user or item not available");
            }

            User user = userOpt.get();
            Item item = itemOpt.get();
            item.setAvailable(false);

            Rental rental = new Rental(
                    UUID.randomUUID().toString(),
                    user,
                    item,
                    days,
                    days * item.getPricePerDay(),
                    LocalDate.now().toString()
            );
            user.getRentals().add(rental);

            return rental;
        };
    }

    public static DataFetcher<Boolean> returnItemFetcher() {
        return environment -> {
            String rentalId = environment.getArgument("rentalId");

            for (User user : users) {
                Optional<Rental> rentalOpt = user.getRentals().stream()
                        .filter(r -> r.getId().equals(rentalId))
                        .findFirst();

                if (rentalOpt.isPresent()) {
                    Rental rental = rentalOpt.get();
                    rental.getItem().setAvailable(true);
                    user.getRentals().remove(rental);
                    return true;
                }
            }
            return false;
        };
    }
}
