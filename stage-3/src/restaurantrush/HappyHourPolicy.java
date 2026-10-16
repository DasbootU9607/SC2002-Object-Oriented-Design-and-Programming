package restaurantrush;

/** One activation, two turns. Customer discount precedes the temporary menu policy. */
public final class HappyHourPolicy {
    private boolean used;
    private int remainingTurns;
    public boolean hasBeenUsed() { return used; }
    public int getRemainingTurns() { return remainingTurns; }
    public boolean isActive() { return remainingTurns > 0; }
    public void beginTurn(boolean activate) {
        if (!activate) return;
        if (used) throw new IllegalArgumentException("Happy Hour can be activated only once per game.");
        used = true;
        remainingTurns = 2;
    }
    public void endTurn() {
        if (remainingTurns > 0) remainingTurns--;
    }
    public int quote(Customer customer, MenuItem item) {
        int customerPrice = customer.adjustBill(item.getPriceCents());
        return isActive() ? Money.percent(customerPrice,80) : customerPrice;
    }
}
