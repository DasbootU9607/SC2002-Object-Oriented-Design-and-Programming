package restaurantrush;

import java.util.ArrayList;
import java.util.List;

public final class Menu {
    private final List<MenuItem> regularItems;
    private final List<MenuItem> combos;
    private final List<MenuItem> items;
    public Menu(List<MenuItem> items) { this(items,List.of()); }
    public Menu(List<MenuItem> regularItems, List<MenuItem> combos) {
        if (regularItems == null || regularItems.isEmpty()) throw new IllegalArgumentException("The menu cannot be empty.");
        this.regularItems = List.copyOf(regularItems);
        this.combos = List.copyOf(combos);
        List<MenuItem> all = new ArrayList<>(this.regularItems);
        all.addAll(this.combos);
        this.items = List.copyOf(all);
    }
    public List<MenuItem> getItems() { return items; }
    public MenuItem get(String name) {
        return items.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown menu item: " + name));
    }
    public MenuItem chooseFor(int customerId) {
        // Every fourth Regular/VIP customer chooses the first combo, if one is offered.
        if (customerId % 4 == 0 && !combos.isEmpty()) return combos.get(0);
        return regularItems.get((customerId - 1) % regularItems.size());
    }
}
