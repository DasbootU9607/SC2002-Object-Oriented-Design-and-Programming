package restaurantrush;

/** Immutable payment-time evidence for revenue and satisfaction statistics. */
public final class Receipt {
    private final int customerId;
    private final int cents;
    private final int satisfaction;
    private final int turn;
    public Receipt(int customerId, int cents, int satisfaction, int turn) {
        this.customerId = customerId;
        this.cents = cents;
        this.satisfaction = satisfaction;
        this.turn = turn;
    }
    public int getCustomerId() { return customerId; }
    public int getCents() { return cents; }
    public int getSatisfaction() { return satisfaction; }
    public int getTurn() { return turn; }
}
