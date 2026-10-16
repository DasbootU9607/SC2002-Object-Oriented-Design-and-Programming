package restaurantrush;

public final class Chef {
    public String prepare(Order order) {
        order.prepareOneUnit();
        return "Chef advanced order " + order.getId() + ": " + order.getCompletedUnits()
            + "/" + order.getItem().getPreparationUnits() + ", " + order.getStatus() + ".";
    }
}
