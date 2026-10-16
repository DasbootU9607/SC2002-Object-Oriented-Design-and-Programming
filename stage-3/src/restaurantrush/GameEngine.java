package restaurantrush;

import java.util.ArrayList;
import java.util.List;

/** The turn controller. Staff act only through this API, in the prescribed phase order. */
public final class GameEngine {
    private final GameSetup setup;
    private final GameState state;
    private final HappyHourPolicy happyHour = new HappyHourPolicy();
    private final Host host = new Host();
    private final Waiter waiter = new Waiter();
    private final Chef chef = new Chef();
    private final Cashier cashier = new Cashier();
    private int turn;
    private boolean turnOpen;
    private boolean hostAssigned;
    private boolean waiterAssigned;
    private boolean chefAssigned;

    public GameEngine(GameSetup setup) {
        this.setup = setup;
        this.state = new GameState(setup.createMenu(),happyHour);
    }
    public GameState getState() { return state; }
    public String getStageName() { return setup.getStageName(); }
    public int getTurn() { return turn; }
    public boolean isFinished() { return turn == GameSetup.MAX_TURNS && !turnOpen; }
    public int getHappyHourRemaining() { return happyHour.getRemainingTurns(); }
    public boolean hasUsedHappyHour() { return happyHour.hasBeenUsed(); }
    public void startTurn() { startTurn(false); }
    public void startTurn(boolean activateHappyHour) {
        if (turnOpen || isFinished()) throw new IllegalArgumentException("Cannot start another turn now.");
        happyHour.beginTurn(activateHappyHour); // Rejected activation never advances time.
        turn++;
        turnOpen = true;
        hostAssigned = false;
        waiterAssigned = false;
        chefAssigned = false;
        if (happyHour.isActive())
            for (Customer customer : state.getCustomers()) customer.recoverSatisfaction(15);
        // New arrivals start at 100, so start-of-turn recovery cannot increase them.
        if (setup.hasArrival(turn)) state.arrive(setup.createCustomer((turn - 1) / 3 + 1));
    }
    public String assignHost(int customerId) {
        requireOpen();
        if (hostAssigned) throw new IllegalArgumentException("The Host has already used this turn's task.");
        String message = customerId == 0 ? "Host waits." : host.seat(state,state.customer(customerId));
        hostAssigned = true;
        return message;
    }
    public String assignWaiter(WaiterTask task, int customerId) {
        requireOpen();
        if (!hostAssigned) throw new IllegalArgumentException("Assign the Host before the Waiter.");
        if (waiterAssigned) throw new IllegalArgumentException("The Waiter has already used this turn's task.");
        if (task == null) throw new IllegalArgumentException("Choose a Waiter task.");
        String message;
        switch (task) {
            case SEAT: message = waiter.seat(state, state.customer(customerId)); break;
            case ORDER: message = waiter.takeOrder(state, state.customer(customerId)); break;
            case SERVE: message = waiter.serve(state, state.customer(customerId), turn); break;
            case WAIT: message = "Waiter waits."; break;
            default: throw new IllegalArgumentException("Unknown Waiter task.");
        }
        waiterAssigned = true; // Invalid choices never reach this line.
        return message;
    }
    public String assignChef(int orderId) {
        requireOpen();
        if (!waiterAssigned) throw new IllegalArgumentException("Assign the Waiter before the Chef.");
        if (chefAssigned) throw new IllegalArgumentException("The Chef has already used this turn's task.");
        String message = orderId == 0 ? "Chef waits." : chef.prepare(state.order(orderId));
        chefAssigned = true;
        return message;
    }
    public List<String> finishTurn() {
        requireOpen();
        if (!waiterAssigned || !chefAssigned) throw new IllegalArgumentException("Assign both staff, or choose Wait.");
        List<String> events = new ArrayList<>();
        for (Customer customer : state.getCustomers()) {
            if (customer.finishEating(turn))
                events.add("Customer " + customer.getId() + " is READY_TO_PAY; payment is possible next turn.");
        }
        Receipt receipt = cashier.collectOldestEligible(state, turn);
        if (receipt != null) events.add("Cashier collected " + Money.format(receipt.getCents())
            + " from customer " + receipt.getCustomerId() + "; table released.");
        for (Customer customer : state.getCustomers()) {
            if (customer.waitAtTurnEnd()) {
                state.recordDeparture(customer);
                events.add("Customer " + customer.getId() + " left unhappy; any unserved order was cancelled.");
            }
        }
        happyHour.endTurn();
        turnOpen = false;
        return List.copyOf(events);
    }
    public GameResult getResult() {
        if (!isFinished()) throw new IllegalArgumentException("The result is checked only after turn 18.");
        return new GameResult(state);
    }
    private void requireOpen() {
        if (!turnOpen) throw new IllegalArgumentException("Start a turn first.");
    }
}
