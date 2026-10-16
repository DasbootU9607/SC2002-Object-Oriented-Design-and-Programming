package restaurantrush;

public final class VIPCustomer extends Customer {
    public VIPCustomer(int id) { super(id); }
    public String getType() { return "VIP"; }
    public int waitingLoss() { return 5; }
    public int adjustBill(int cents) { return Money.percent(cents,90); }
}
