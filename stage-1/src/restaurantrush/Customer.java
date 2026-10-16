package restaurantrush;

/** Owns the customer lifecycle; subclasses supply behaviour, not a different service flow. */
public abstract class Customer {
    public enum Status { WAITING, SEATED, WAITING_FOR_FOOD, EATING, READY_TO_PAY, PAID, LEFT }
    private final int id;
    private int satisfaction = 100;
    private Status status = Status.WAITING;
    private int servedTurn = -1;
    private int readyToPayTurn = -1;

    protected Customer(int id) {
        if (id <= 0) throw new IllegalArgumentException("Customer ID must be positive.");
        this.id = id;
    }
    public int getId() { return id; }
    public int getSatisfaction() { return satisfaction; }
    public Status getStatus() { return status; }
    public int getServedTurn() { return servedTurn; }
    public int getReadyToPayTurn() { return readyToPayTurn; }
    public abstract String getType();
    public abstract int waitingLoss();
    public MenuItem chooseMeal(Menu menu) { return menu.chooseFor(id); }
    public int adjustBill(int cents) { return cents; }
    public boolean isWaitingForService() {
        return status == Status.WAITING || status == Status.SEATED || status == Status.WAITING_FOR_FOOD;
    }
    public void seat() {
        require(Status.WAITING);
        status = Status.SEATED;
    }
    public void order() {
        require(Status.SEATED);
        status = Status.WAITING_FOR_FOOD;
    }
    public void serve(int turn) {
        require(Status.WAITING_FOR_FOOD);
        status = Status.EATING;
        servedTurn = turn;
    }
    public boolean finishEating(int turn) {
        if (status != Status.EATING || servedTurn >= turn) return false;
        status = Status.READY_TO_PAY;
        readyToPayTurn = turn;
        return true;
    }
    public boolean canPay(int turn) {
        return status == Status.READY_TO_PAY && readyToPayTurn < turn;
    }
    public void pay(int turn) {
        if (!canPay(turn)) throw new IllegalArgumentException("This customer cannot pay until a later turn.");
        status = Status.PAID;
    }
    /** Returns true only when this update causes an unhappy departure. */
    public boolean waitAtTurnEnd() {
        if (!isWaitingForService()) return false;
        satisfaction = Math.max(0, satisfaction - waitingLoss());
        if (satisfaction != 0) return false;
        status = Status.LEFT;
        return true;
    }
    private void require(Status expected) {
        if (status != expected)
            throw new IllegalArgumentException("Customer " + id + " is " + status + ", expected " + expected + ".");
    }
}
