package restaurantrush;

public final class Dish implements MenuItem {
    private final String name;
    private final int priceCents;
    private final int preparationUnits;
    public Dish(String name, int priceCents, int preparationUnits) {
        if (name == null || name.isBlank() || priceCents <= 0 || preparationUnits <= 0)
            throw new IllegalArgumentException("A dish needs a name, positive price and preparation time.");
        this.name = name;
        this.priceCents = priceCents;
        this.preparationUnits = preparationUnits;
    }
    public String getName() { return name; }
    public int getPriceCents() { return priceCents; }
    public int getPreparationUnits() { return preparationUnits; }
}
