package restaurantrush;

public final class CriticCustomer extends Customer {
    public CriticCustomer(int id) { super(id); }
    public String getType() { return "Critic"; }
    public int waitingLoss() { return 12; }
    /** Critics always choose Pasta, independently of their arrival ID. */
    public MenuItem chooseMeal(Menu menu) { return menu.get("Pasta"); }
}
