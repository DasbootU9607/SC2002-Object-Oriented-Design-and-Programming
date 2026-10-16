package restaurantrush;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/** Boundary object: parses decisions and displays state. Domain objects enforce the rules. */
public final class ConsoleUI {
    private final BufferedReader input;
    private final PrintWriter output;
    private final boolean echo;
    public ConsoleUI(Reader input, PrintWriter output, boolean echo) {
        this.input = new BufferedReader(input);
        this.output = output;
        this.echo = echo;
    }
    public void play(GameEngine game) throws IOException {
        output.println("RESTAURANT RUSH | " + game.getStageName());
        output.println("18 turns; 2 tables. Goal: 4 paid customers, $45.00 revenue, 60 average satisfaction.");
        output.println("Customers choose their meals. IDs identify customers and their orders.");
        output.println("Menu:");
        for (MenuItem item : game.getState().getMenu().getItems())
            output.println("  " + item.getName() + " " + Money.format(item.getPriceCents())
                + " (" + item.getPreparationUnits() + " preparation unit(s))");
        while (!game.isFinished()) {
            readTurnStart(game);
            showState(game);
            readHost(game);
            readWaiter(game);
            readChef(game);
            for (String event : game.finishTurn()) output.println(event);
            output.println("End of turn " + game.getTurn() + ": " + summary(game.getState())
                + " | turns used " + game.getTurn() + "/18");
            output.println();
        }
        output.println(game.getResult().describe());
        output.flush();
    }
    private void showState(GameEngine game) {
        GameState state = game.getState();
        output.println("\nTURN " + game.getTurn() + "/18 | " + summary(state));
        output.println("Queue: " + (state.getWaiting().isEmpty() ? "(empty)" : state.getWaiting().stream()
            .map(this::customerText).collect(Collectors.joining("; "))));
        for (Table table : state.getTables())
            output.println("Table " + table.getId() + ": " + (table.isFree() ? "free" : customerText(table.getCustomer())));
        for (Order order : state.getOrders())
            output.println("Order " + order.getId() + " / customer " + order.getCustomerId() + ": "
                + order.getItem().getName() + " " + order.getStatus() + " " + order.getCompletedUnits()
                + "/" + order.getItem().getPreparationUnits() + " | locked bill " + Money.format(order.getBillCents()));
        for (Customer customer : state.getCustomers())
            if (customer.getStatus() == Customer.Status.PAID || customer.getStatus() == Customer.Status.LEFT)
                output.println("History: " + customerText(customer));
        output.println("Happy Hour: " + game.getHappyHourRemaining() + " active turn(s) remaining, including this turn."
            + " Activation used: " + game.hasUsedHappyHour() + ".");
        output.println("Staff available: Host 1 seating; Waiter 1 task; Chef 1 preparation unit; Cashier 1 automatic payment.");
    }
    private String customerText(Customer customer) {
        return "#" + customer.getId() + " " + customer.getType() + " " + customer.getStatus()
            + " satisfaction=" + customer.getSatisfaction();
    }
    private String summary(GameState state) {
        return "served=" + state.getServedCount() + ", paid=" + state.getPaidCount()
            + ", unhappy departures=" + state.getUnhappyDepartures()
            + ", revenue=" + Money.format(state.getRevenueCents())
            + ", average paid satisfaction=" + state.averageSatisfactionText();
    }
    private void readTurnStart(GameEngine game) throws IOException {
        while (true) {
            String[] parts = readCommand("Turn " + (game.getTurn() + 1)
                + " Happy Hour [activate | continue] (activation used=" + game.hasUsedHappyHour() + "): ");
            try {
                if (parts.length != 1 || (!parts[0].equalsIgnoreCase("activate")
                        && !parts[0].equalsIgnoreCase("continue")))
                    throw new IllegalArgumentException("Use activate once per game, or continue without changing the policy.");
                game.startTurn(parts[0].equalsIgnoreCase("activate"));
                return;
            } catch (IllegalArgumentException ex) {
                output.println("Invalid choice: " + ex.getMessage() + " Please choose again; the turn has not started.");
            }
        }
    }
    private void readHost(GameEngine game) throws IOException {
        while (true) {
            String[] parts = readCommand("Host [seat CUSTOMER_ID | wait]: ");
            try {
                if (!parts[0].equalsIgnoreCase("seat") && !parts[0].equalsIgnoreCase("wait"))
                    throw new IllegalArgumentException("Use seat ID or wait.");
                int id = parseTarget(parts,parts[0].equalsIgnoreCase("wait"));
                output.println(game.assignHost(id));
                return;
            } catch (IllegalArgumentException ex) {
                output.println("Invalid choice: " + ex.getMessage() + " Please choose again; no task was spent.");
            }
        }
    }
    private void readWaiter(GameEngine game) throws IOException {
        while (true) {
            String[] parts = readCommand("Waiter [seat ID | order ID | serve ID | wait]: ");
            try {
                WaiterTask task = WaiterTask.valueOf(parts[0].toUpperCase(Locale.ROOT));
                int id = parseTarget(parts, task == WaiterTask.WAIT);
                output.println(game.assignWaiter(task, id));
                return;
            } catch (IllegalArgumentException ex) {
                output.println("Invalid choice: " + ex.getMessage() + " Please choose again; no task was spent.");
            }
        }
    }
    private void readChef(GameEngine game) throws IOException {
        List<Order> available = game.getState().getOrders().stream().filter(Order::canPrepare).collect(Collectors.toList());
        output.println("Chef can advance order(s): " + (available.isEmpty() ? "(none)" : available.stream()
            .map(o -> String.valueOf(o.getId())).collect(Collectors.joining(", "))));
        while (true) {
            String[] parts = readCommand("Chef [cook ORDER_ID | wait]: ");
            try {
                if (!parts[0].equalsIgnoreCase("cook") && !parts[0].equalsIgnoreCase("wait"))
                    throw new IllegalArgumentException("Use cook ID or wait.");
                int id = parseTarget(parts, parts[0].equalsIgnoreCase("wait"));
                output.println(game.assignChef(id));
                return;
            } catch (IllegalArgumentException ex) {
                output.println("Invalid choice: " + ex.getMessage() + " Please choose again; no task was spent.");
            }
        }
    }
    private int parseTarget(String[] parts, boolean wait) {
        if (parts.length != (wait ? 1 : 2))
            throw new IllegalArgumentException(wait ? "Wait takes no ID." : "This command needs exactly one ID.");
        if (wait) return 0;
        try {
            int id = Integer.parseInt(parts[1]);
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("The ID must be a positive integer.");
        }
    }
    private String[] readCommand(String prompt) throws IOException {
        output.print(prompt);
        output.flush();
        while (true) {
            String line = input.readLine();
            if (line == null) throw new IOException("Input ended before turn 18. No final result was recorded.");
            line = line.trim();
            if (line.startsWith("#")) continue;
            if (echo) output.println(line);
            if (line.isEmpty()) return new String[] { "" };
            return line.split("\\s+");
        }
    }
}
