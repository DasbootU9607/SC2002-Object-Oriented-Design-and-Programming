package restaurantrush;

public class RegularCustomer extends Customer {
    public RegularCustomer(int id) { super(id); }
    public String getType() { return "Regular"; }
    public int waitingLoss() { return 8; }
}
