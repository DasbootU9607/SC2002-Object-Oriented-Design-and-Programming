package restaurantrush;

import java.util.List;

/** The documented, reproducible base setting. No network or random generator is needed. */
public class GameSetup {
    public static final int MAX_TURNS = 18;
    public String getStageName() { return "Stage 1 - Basic operation"; }
    public Menu createMenu() {
        return new Menu(List.of(new Dish("Burger", 1500, 1),
            new Dish("Salad", 900, 1), new Dish("Pasta", 1800, 2)));
    }
    public Customer createCustomer(int id) { return new RegularCustomer(id); }
    public boolean hasArrival(int turn) { return turn >= 1 && turn <= 16 && (turn - 1) % 3 == 0; }
}
