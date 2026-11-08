package restaurantrush;

public final class Waiter {
    public String seat(GameState state, Customer customer) {
        state.seat(customer);
        return "Waiter seated customer " + customer.getId() + ".";
    }
    public String takeOrder(GameState state, Customer customer) {
        if (customer.getStatus() != Customer.Status.SEATED)
            throw new IllegalArgumentException("Only a seated customer without an order can order.");
        MenuItem item = customer.chooseMeal(state.getMenu());
        Order order = new Order(customer.getId(), customer.getId(), item, state.quoteBill(customer, item));
        state.addOrder(order);
        return "Customer " + customer.getId() + " chose " + item.getName() + ". Order " + order.getId()
            + ": " + Money.format(order.getBillCents()) + ", " + item.getPreparationUnits() + " preparation unit(s).";
    }
    public String serve(GameState state, Customer customer, int turn) {
        Order order = state.orderFor(customer.getId());
        if (order == null || order.getStatus() != Order.Status.READY
                || customer.getStatus() != Customer.Status.WAITING_FOR_FOOD)
            throw new IllegalArgumentException("The customer's dish must already be READY when the Waiter acts.");
        order.serve();
        customer.serve(turn);
        state.recordServed();
        return "Waiter served order " + order.getId() + ". Customer " + customer.getId() + " is eating.";
    }
}
