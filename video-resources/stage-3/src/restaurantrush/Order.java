package restaurantrush;

public final class Order {
    public enum Status { PLACED, PREPARING, READY, SERVED, PAID, CANCELLED }
    private final int id;
    private final int customerId;
    private final MenuItem item;
    private final int billCents;
    private int completedUnits;
    private Status status = Status.PLACED;

    public Order(int id, int customerId, MenuItem item, int billCents) {
        if (id <= 0 || customerId <= 0 || item == null || billCents < 0)
            throw new IllegalArgumentException("Invalid order.");
        this.id = id;
        this.customerId = customerId;
        this.item = item;
        this.billCents = billCents;
    }
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public MenuItem getItem() { return item; }
    public int getBillCents() { return billCents; }
    public int getCompletedUnits() { return completedUnits; }
    public Status getStatus() { return status; }
    public boolean canPrepare() { return status == Status.PLACED || status == Status.PREPARING; }
    public void prepareOneUnit() {
        if (!canPrepare()) throw new IllegalArgumentException("Order " + id + " cannot be prepared: " + status + ".");
        completedUnits++;
        status = completedUnits == item.getPreparationUnits() ? Status.READY : Status.PREPARING;
    }
    public void serve() {
        require(Status.READY);
        status = Status.SERVED;
    }
    public void pay() {
        require(Status.SERVED);
        status = Status.PAID;
    }
    public void cancel() {
        if (status != Status.PLACED && status != Status.PREPARING && status != Status.READY)
            throw new IllegalArgumentException("Only an unserved order can be cancelled.");
        status = Status.CANCELLED;
    }
    private void require(Status expected) {
        if (status != expected)
            throw new IllegalArgumentException("Order " + id + " is " + status + ", expected " + expected + ".");
    }
}
