package restaurantrush;

/** An independent seating task, performed before the Waiter in this implementation. */
public final class Host {
    public String seat(GameState state, Customer customer) {
        state.seat(customer);
        return "Host seated customer " + customer.getId() + ".";
    }
}
