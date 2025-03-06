package rental;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import rental.Item;
import rental.User;
import utils.JsonUtils;

import java.util.List;
import java.util.Optional;

public class DataFetchers {

    private static final List<User> users = JsonUtils.loadListFromJsonFile("/data/users.json", User[].class);
    private static final List<Item> items = JsonUtils.loadListFromJsonFile("/data/items.json", Item[].class);

    public static DataFetcher<List<Item>> getAllItemsFetcher() {
        return dataFetchingEnvironment -> items;
    }

    public static DataFetcher<User> getUserByIdFetcher() {
        return dataFetchingEnvironment -> {
            String userId = dataFetchingEnvironment.getArgument("id");
            Optional<User> user = users.stream().filter(u -> u.getId().equals(userId)).findFirst();
            return user.orElse(null);
        };
    }
}
