package restaurantrush;

import java.util.List;

/** The documented, reproducible base setting. No network or random generator is needed. */
public class GameSetup {
    public static final int MAX_TURNS = 18;
    public String getStageName() { return "Stage 3 - Host, ComboMeal and Happy Hour"; }
    public Menu createMenu() {
        MenuItem burger = new Dish("Burger",1500,1);
        MenuItem salad = new Dish("Salad",900,1);
        MenuItem pasta = new Dish("Pasta",1800,2);
        return new Menu(List.of(burger,salad,pasta),List.of(new ComboMeal(salad,pasta)));
    }
    public Customer createCustomer(int id) {
        if (id == 2) return new VIPCustomer(id);
        if (id == 3) return new CriticCustomer(id);
        return new RegularCustomer(id);
    }
    public boolean hasArrival(int turn) { return turn >= 1 && turn <= 16 && (turn - 1) % 3 == 0; }
}
