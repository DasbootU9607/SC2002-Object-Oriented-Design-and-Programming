# Mei Chuhan Stage 3 Project Dialogue

Member allocation: Mei Chuhan

Focus: Implementation and testing

Stage: 3

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How can the Host be added without giving the Waiter an extra action?

**Assistant:** Give Host its own phase and action-used flag. Host may seat at most one queued customer; Waiter independently retains one task, including the option to seat another customer. In the current design Host acts before Waiter, allowing the newly seated customer to order immediately. Both seating paths must apply the same queue and table checks.

## Exchange 2

**Student:** How should combo, VIP and Happy Hour discounts interact?

**Assistant:** Compute the combo price first as 90% of its two components. Apply the customer adjustment next, then the Happy Hour reduction. Round each monetary percentage half up to integer cents and store the final quotation in Order when the order is taken. Salad plus Pasta costs $24.30; for a VIP during Happy Hour, $24.30 becomes $21.87 and then $17.50.

## Exchange 3

**Student:** Which tests are most likely to expose a faulty promotion implementation?

**Assistant:** Test activation only once, activity on the activation turn and the following turn, refusal to reactivate, and the displayed remaining duration. Check satisfaction recovery only for customers still waiting for service, capped at 100; ordinary end-of-turn losses still apply. Verify that existing orders never change price. Retain baseline tests with Host waiting and promotion unused, and test each final victory threshold separately.

## Reference material

- [Stage 3 README](../../stage-3/README.md)
- [Stage 3 source](../../stage-3/src/restaurantrush/)
- [Stage 3 tests](../../stage-3/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

