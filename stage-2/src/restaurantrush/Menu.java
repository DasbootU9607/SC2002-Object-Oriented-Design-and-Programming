package restaurantrush;

import java.util.List;

public final class Menu {
    private final List<MenuItem> items;
    public Menu(List<MenuItem> items) {
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("The menu cannot be empty.");
        this.items = List.copyOf(items);
    }
    public List<MenuItem> getItems() { return items; }
    public MenuItem get(String name) {
        return items.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown menu item: " + name));
    }
    public MenuItem chooseFor(int customerId) {
        return items.get((customerId - 1) % items.size());
    }
}
