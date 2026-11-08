package restaurantrush;

public final class Table {
    private final int id;
    private Customer customer;
    public Table(int id) { this.id = id; }
    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public boolean isFree() { return customer == null; }
    public void seat(Customer customer) {
        if (!isFree()) throw new IllegalArgumentException("Table " + id + " is occupied.");
        customer.seat();
        this.customer = customer;
    }
    public void release() {
        if (customer == null || (customer.getStatus() != Customer.Status.PAID
                && customer.getStatus() != Customer.Status.LEFT))
            throw new IllegalArgumentException("The table is released only after payment or departure.");
        customer = null;
    }
}
