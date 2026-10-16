package restaurantrush;

import java.util.Comparator;

public final class Cashier {
    public Receipt collectOldestEligible(GameState state, int turn) {
        Customer customer = state.getCustomers().stream().filter(c -> c.canPay(turn))
            .min(Comparator.comparingInt(Customer::getReadyToPayTurn).thenComparingInt(Customer::getId))
            .orElse(null);
        if (customer == null) return null;
        state.recordPayment(customer, state.orderFor(customer.getId()), turn);
        return state.getReceipts().get(state.getReceipts().size() - 1);
    }
}
