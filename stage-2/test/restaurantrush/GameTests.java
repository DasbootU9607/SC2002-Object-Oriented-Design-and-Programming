package restaurantrush;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Dependency-free behavioural regression suite. Throws on failure even without -ea. */
public final class GameTests {
    private static int assertions;
    public static void main(String[] args) throws Exception {
        baseSettingAndDefeat();
        pastaTimeline();
        invalidActionsAreRetryable();
        fullTablesAndAbandonment();
        cashierWaitsAndUsesOldestFirst();
        moneyAndEncapsulation();
        customerPolymorphism();
        replayVictory();
        replayInvalidInput();
        earlyEndOfInput();
        System.out.println("PASS: " + assertions + " assertions.");
    }
    private static GameSetup basicSetup() {
        return new GameSetup() {
            public Menu createMenu() {
                return new Menu(List.of(new Dish("Burger",1500,1), new Dish("Salad",900,1), new Dish("Pasta",1800,2)));
            }
            public Customer createCustomer(int id) { return new RegularCustomer(id); }
        };
    }
    private static GameEngine basicGame() { return new GameEngine(basicSetup()); }
    private static void begin(GameEngine game) { game.startTurn(); }
    private static void step(GameEngine game, WaiterTask task, int customerId, int orderId) {
        begin(game);
        game.assignWaiter(task, customerId);
        game.assignChef(orderId);
        game.finishTurn();
    }
    private static void idle(GameEngine game) { step(game, WaiterTask.WAIT, 0, 0); }
    private static void check(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }
    private static void rejects(Runnable operation, String message) {
        assertions++;
        try { operation.run(); }
        catch (IllegalArgumentException | UnsupportedOperationException expected) { return; }
        throw new AssertionError(message);
    }
    private static void baseSettingAndDefeat() {
        GameEngine game = basicGame();
        check(game.getState().getTables().size() == 2, "Exactly two tables");
        check(game.getState().getMenu().get("Salad").getPriceCents() == 900, "Salad price");
        check(game.getState().getMenu().get("Burger").getPreparationUnits() == 1, "Burger preparation");
        check(game.getState().getMenu().get("Pasta").getPreparationUnits() == 2, "Pasta preparation");
        check(game.getState().averageSatisfactionText().equals("N/A"), "Unpaid average is N/A");
        rejects(game::getResult, "No result before turn 18");
        for (int turn = 1; turn <= 18; turn++) {
            idle(game);
            check(game.getState().getCustomers().size() == (turn - 1) / 3 + 1, "Arrival schedule at " + turn);
            check(game.isFinished() == (turn == 18), "All eighteen turns are required");
        }
        check(!game.getResult().isVictory(), "All-wait strategy loses");
        check(game.getState().getRevenueCents() == 0, "Unpaid revenue stays zero");
        check(game.getState().getUnhappyDepartures() == 2, "First two Regular customers abandon");
        check(game.getState().customer(1).getSatisfaction() == 0, "Satisfaction floors at zero");
        rejects(game::startTurn, "No nineteenth turn");
    }
    private static void pastaTimeline() {
        GameEngine game = new GameEngine(new GameSetup() {
            public Menu createMenu() { return new Menu(List.of(new Dish("Pasta",1800,2))); }
            public Customer createCustomer(int id) { return new RegularCustomer(id); }
        });
        step(game,WaiterTask.SEAT,1,0);
        check(game.getState().customer(1).getSatisfaction() == 92, "Pasta example turn 1 satisfaction");
        step(game,WaiterTask.ORDER,1,1);
        check(game.getState().order(1).getCompletedUnits() == 1, "Chef can work on a newly placed order");
        check(game.getState().customer(1).getSatisfaction() == 84, "Pasta example turn 2 satisfaction");
        begin(game);
        rejects(() -> game.assignWaiter(WaiterTask.SERVE,1), "Cannot serve an incomplete dish");
        game.assignWaiter(WaiterTask.WAIT,0);
        game.assignChef(1);
        rejects(() -> game.assignWaiter(WaiterTask.SERVE,1), "Cannot serve a dish just completed by Chef");
        game.finishTurn();
        check(game.getState().order(1).getStatus() == Order.Status.READY, "Pasta ready after turn 3");
        check(game.getState().customer(1).getSatisfaction() == 76, "Pasta example turn 3 satisfaction");
        step(game,WaiterTask.SERVE,1,0);
        check(game.getState().customer(1).getStatus() == Customer.Status.EATING, "Serving begins eating");
        check(game.getState().customer(1).getSatisfaction() == 76, "Served customer loses no satisfaction");
        check(game.getState().getPaidCount() == 0, "Cannot pay on serving turn");
        idle(game);
        check(game.getState().customer(1).getStatus() == Customer.Status.READY_TO_PAY, "Ready after a full eating turn");
        check(game.getState().getPaidCount() == 0, "Cannot pay on newly ready turn");
        check(!game.getState().getTables().get(0).isFree(), "Still occupies table before payment");
        idle(game);
        check(game.getState().order(1).getStatus() == Order.Status.PAID, "Pasta paid on turn 6");
        check(game.getState().getRevenueCents() == 1800, "Correct Pasta revenue");
        check(game.getState().getReceipts().get(0).getSatisfaction() == 76, "Payment snapshot");
        check(game.getState().getTables().get(0).isFree(), "Payment frees table");
    }
    private static void invalidActionsAreRetryable() {
        GameEngine game = basicGame();
        begin(game);
        rejects(() -> game.assignChef(0), "Waiter must act before Chef");
        rejects(() -> game.assignWaiter(WaiterTask.SEAT,999), "Unknown customer");
        rejects(() -> game.assignWaiter(WaiterTask.ORDER,1), "Cannot order before seating");
        game.assignWaiter(WaiterTask.SEAT,1);
        rejects(() -> game.assignWaiter(WaiterTask.WAIT,0), "Waiter only one task");
        rejects(() -> game.assignChef(1), "Cannot prepare an absent order");
        game.assignChef(0);
        rejects(() -> game.assignChef(0), "Chef only one task");
        game.finishTurn();
        check(game.getState().customer(1).getSatisfaction() == 92, "Retries never advance time");
        rejects(game::finishTurn, "Cannot finish the same turn twice");
    }
    private static void fullTablesAndAbandonment() {
        GameEngine game = basicGame();
        step(game,WaiterTask.SEAT,1,0);
        step(game,WaiterTask.ORDER,1,0);
        idle(game);
        step(game,WaiterTask.SEAT,2,0);
        idle(game);
        idle(game);
        begin(game);
        rejects(() -> game.assignWaiter(WaiterTask.SEAT,3), "A third customer cannot take an occupied table");
        check(game.getState().customer(3).getStatus() == Customer.Status.WAITING, "Invalid seating preserves queue state");
        game.assignWaiter(WaiterTask.WAIT,0);
        game.assignChef(0);
        game.finishTurn();
        while (game.getTurn() < 13) idle(game);
        check(game.getState().customer(1).getStatus() == Customer.Status.LEFT, "Unserved customer abandons");
        check(game.getState().order(1).getStatus() == Order.Status.CANCELLED, "Unfinished order cancelled");
        check(game.getState().getRevenueCents() == 0, "Abandoned order earns nothing");
        begin(game);
        game.assignWaiter(WaiterTask.SEAT,3);
        rejects(() -> game.assignChef(1), "Cancelled order cannot be cooked");
        game.assignChef(0);
        game.finishTurn();
        check(game.getState().customer(3).getStatus() == Customer.Status.SEATED, "Freed table can be reused");
    }
    private static void cashierWaitsAndUsesOldestFirst() {
        GameState state = new GameState(basicSetup().createMenu());
        Customer first = new RegularCustomer(1);
        Customer second = new RegularCustomer(2);
        state.arrive(first); state.arrive(second);
        Waiter waiter = new Waiter();
        waiter.seat(state,first); waiter.seat(state,second);
        waiter.takeOrder(state,first); waiter.takeOrder(state,second);
        new Chef().prepare(state.order(1)); new Chef().prepare(state.order(2));
        waiter.serve(state,second,1);
        waiter.serve(state,first,2);
        second.finishEating(2); first.finishEating(3);
        Cashier cashier = new Cashier();
        Receipt receipt = cashier.collectOldestEligible(state,3);
        check(receipt.getCustomerId() == 2, "Oldest ready customer, not smallest ID, pays first");
        check(state.getPaidCount() == 1, "One payment per Cashier invocation");
        check(!first.canPay(3), "Newly ready customer cannot pay");
        receipt = cashier.collectOldestEligible(state,4);
        check(receipt.getCustomerId() == 1, "Next eligible customer pays later");
        check(cashier.collectOldestEligible(state,5) == null, "No double payment");
    }
    private static void moneyAndEncapsulation() {
        check(Money.percent(105,90) == 95, "Half-cent rounds up");
        check(Money.format(1080).equals("$10.80"), "Exactly two currency decimals");
        GameEngine game = basicGame();
        idle(game);
        rejects(() -> game.getState().getCustomers().clear(), "Customer collection cannot be externally cleared");
        rejects(() -> game.getState().getWaiting().clear(), "Waiting collection cannot be externally cleared");
    }
    private static String replay(GameEngine game, String script) throws IOException {
        StringWriter text = new StringWriter();
        new ConsoleUI(new StringReader(script),new PrintWriter(text,true),true).play(game);
        return text.toString();
    }
    private static void replayVictory() throws IOException {
        GameEngine game = new GameEngine(new GameSetup());
        String output = replay(game,Files.readString(Path.of("examples/victory.txt")));
        check(game.getResult().isVictory(), "Documented victory replay wins");
        check(game.getState().getPaidCount() == 5, "Exactly five customers can pay in this replay");
        check(game.getState().getRevenueCents() == 6510, "Victory replay revenue");
        check(game.getState().getPaidSatisfactionSum() == 390, "Victory replay satisfaction sum");
        check(game.getState().customer(6).getStatus() != Customer.Status.PAID, "Turn-16 arrival cannot pay by 18");
        check(!output.contains("Invalid choice"), "Victory replay contains only valid choices");
        check(output.contains("VICTORY"), "CLI reports victory");
        GameEngine defeat = new GameEngine(new GameSetup());
        String failure = replay(defeat,Files.readString(Path.of("examples/defeat.txt")));
        check(failure.contains("DEFEAT") && defeat.getTurn() == 18, "Defeat replay runs all turns");
    }
    private static void replayInvalidInput() throws IOException {
        String script = Files.readString(Path.of("examples/victory.txt"));
        script = "nonsense\nseat nope\nseat -1\nseat 999\norder 1\n" + script;
        GameEngine game = new GameEngine(new GameSetup());
        String output = replay(game,script);
        check(output.contains("Invalid choice"), "CLI explains invalid input");
        check(game.getResult().isVictory(), "Retries preserve a winning replay");
    }
    private static void earlyEndOfInput() {
        try {
            replay(basicGame(),"");
            throw new AssertionError("Truncated input must not fabricate a completed game");
        } catch (IOException expected) {
            check(expected.getMessage().contains("Input ended"), "EOF gives a clear message");
        }
    }

    private static void customerPolymorphism() {
        Menu menu = basicSetup().createMenu();
        Customer regular = new RegularCustomer(1);
        Customer vip = new VIPCustomer(1);
        Customer critic = new CriticCustomer(1);
        check(regular.chooseMeal(menu).getName().equals("Burger"), "Regular follows ID-based cycle");
        check(critic.chooseMeal(menu).getName().equals("Pasta"), "Critic preference differs even with same ID");
        check(vip.adjustBill(1500) == 1350, "VIP receives ten percent discount");
        check(critic.adjustBill(1800) == 1800, "Critic pays full price");
        for (Customer customer : List.of(regular,vip,critic)) customer.waitAtTurnEnd();
        check(regular.getSatisfaction() == 92, "Regular polymorphic waiting loss");
        check(vip.getSatisfaction() == 95, "VIP polymorphic waiting loss");
        check(critic.getSatisfaction() == 88, "Critic polymorphic waiting loss");
        for (int i = 0; i < 8; i++) critic.waitAtTurnEnd();
        check(critic.getStatus() == Customer.Status.LEFT && critic.getSatisfaction() == 0,
            "Critic abandons on ninth waiting turn");
        GameEngine game = new GameEngine(new GameSetup());
        while (!game.isFinished()) idle(game);
        check(game.getState().getCustomers().size() == 6, "Mixed setting still has six arrivals");
        check(game.getState().customer(2).getType().equals("VIP"), "Reproducible setting includes a VIP");
        check(game.getState().customer(3).getType().equals("Critic"), "Reproducible setting includes a Critic");
    }
}
