package restaurantrush;

import java.util.List;

/** A menu item composed of two existing items, with one combined preparation cost and bill. */
public final class ComboMeal implements MenuItem {
    private final List<MenuItem> components;
    private final int priceCents;
    private final int preparationUnits;
    public ComboMeal(MenuItem first, MenuItem second) {
        components = List.of(first,second);
        priceCents = Money.percent(Math.addExact(first.getPriceCents(),second.getPriceCents()),90);
        preparationUnits = Math.addExact(first.getPreparationUnits(),second.getPreparationUnits());
    }
    public List<MenuItem> getComponents() { return components; }
    public String getName() { return components.get(0).getName() + " + " + components.get(1).getName() + " Combo"; }
    public int getPriceCents() { return priceCents; }
    public int getPreparationUnits() { return preparationUnits; }
}
