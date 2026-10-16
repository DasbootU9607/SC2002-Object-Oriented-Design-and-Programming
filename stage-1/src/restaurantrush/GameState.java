package restaurantrush;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public final class GameState {
    private final Menu menu;
    private final List<Table> tables = List.of(new Table(1), new Table(2));
    private final List<Customer> customers = new ArrayList<>();
    private final Deque<Customer> waiting = new ArrayDeque<>();
    private final List<Order> orders = new ArrayList<>();
    private final List<Receipt> receipts = new ArrayList<>();
    private int servedCount;
    private int unhappyDepartures;

    public GameState(Menu menu) { this.menu = menu; }
    public Menu getMenu() { return menu; }
    public List<Table> getTables() { return tables; }
    public List<Customer> getCustomers() { return Collections.unmodifiableList(customers); }
    public List<Customer> getWaiting() { return List.copyOf(waiting); }
    public List<Order> getOrders() { return Collections.unmodifiableList(orders); }
    public List<Receipt> getReceipts() { return Collections.unmodifiableList(receipts); }
    public int getServedCount() { return servedCount; }
    public int getUnhappyDepartures() { return unhappyDepartures; }
    public int getRevenueCents() { return receipts.stream().mapToInt(Receipt::getCents).sum(); }
    public int getPaidCount() { return receipts.size(); }
    public int getPaidSatisfactionSum() { return receipts.stream().mapToInt(Receipt::getSatisfaction).sum(); }
    public String averageSatisfactionText() {
        return receipts.isEmpty() ? "N/A" : String.format(java.util.Locale.ROOT, "%.2f",
            (double) getPaidSatisfactionSum() / receipts.size());
    }
    public Customer customer(int id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No customer with ID " + id + "."));
    }
    public Order order(int id) {
        return orders.stream().filter(o -> o.getId() == id).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No order with ID " + id + "."));
    }
    public Order orderFor(int customerId) {
        return orders.stream().filter(o -> o.getCustomerId() == customerId).findFirst().orElse(null);
    }
    public void arrive(Customer customer) {
        customers.add(customer);
        waiting.addLast(customer);
    }
    public void seat(Customer customer) {
        if (!waiting.contains(customer)) throw new IllegalArgumentException("This customer is not in the waiting queue.");
        Table table = tables.stream().filter(Table::isFree).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Both tables are occupied."));
        table.seat(customer);
        waiting.remove(customer);
    }
    public int quoteBill(Customer customer, MenuItem item) { return customer.adjustBill(item.getPriceCents()); }
    public void addOrder(Order order) {
        if (orderFor(order.getCustomerId()) != null) throw new IllegalArgumentException("This customer already ordered.");
        customer(order.getCustomerId()).order();
        orders.add(order);
    }
    public void recordServed() { servedCount++; }
    public void recordPayment(Customer customer, Order order, int turn) {
        if (!customer.canPay(turn) || order.getStatus() != Order.Status.SERVED
                || order.getCustomerId() != customer.getId())
            throw new IllegalArgumentException("The customer and order are not ready for payment.");
        Receipt receipt = new Receipt(customer.getId(), order.getBillCents(), customer.getSatisfaction(), turn);
        customer.pay(turn);
        order.pay();
        receipts.add(receipt);
        releaseTable(customer);
    }
    public void recordDeparture(Customer customer) {
        if (customer.getStatus() != Customer.Status.LEFT) throw new IllegalArgumentException("Customer has not left.");
        waiting.remove(customer);
        Order order = orderFor(customer.getId());
        if (order != null) order.cancel();
        releaseTable(customer);
        unhappyDepartures++;
    }
    private void releaseTable(Customer customer) {
        tables.stream().filter(t -> t.getCustomer() == customer).findFirst().ifPresent(Table::release);
    }
}
