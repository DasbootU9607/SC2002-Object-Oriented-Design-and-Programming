package restaurantrush;

public final class GameResult {
    private final boolean victory;
    private final int paidCount;
    private final int revenueCents;
    private final String average;
    public GameResult(GameState state) {
        paidCount = state.getPaidCount();
        revenueCents = state.getRevenueCents();
        average = state.averageSatisfactionText();
        victory = paidCount >= 4 && revenueCents >= 4500
            && state.getPaidSatisfactionSum() >= 60 * paidCount;
    }
    public boolean isVictory() { return victory; }
    public String describe() {
        return (victory ? "VICTORY" : "DEFEAT") + " | paid " + paidCount + "/4"
            + " | revenue " + Money.format(revenueCents) + "/$45.00"
            + " | average paid satisfaction " + average + "/60";
    }
}
