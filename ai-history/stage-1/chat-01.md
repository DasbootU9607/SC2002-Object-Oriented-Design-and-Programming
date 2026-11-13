# Mei Chuhan Stage 1 Project Dialogue

Member allocation: Mei Chuhan

Focus: Implementation and testing

Stage: 1

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How should I coordinate one turn without letting a staff member act twice?

**Assistant:** Let GameEngine coordinate the phases and keep separate action-used flags. Validate the phase, staff availability and target before changing the game. Set a flag only after an action succeeds. This preserves the rule that an invalid choice explains the failure and allows another choice without consuming the staff task. Waiter and Chef have separate limits; Cashier automatically collects at most one eligible payment.

## Exchange 2

**Student:** Could I finish cooking, serve the food and collect payment in the same turn?

**Assistant:** No. Waiter acts before Chef, so an order completed by Chef is not available to that turn's earlier Waiter action. A served customer finishes a full turn of eating on a later turn and becomes READY_TO_PAY. Payment must then wait until a further turn. The existing Customer implementation records servedTurn and readyToPayTurn to distinguish these events.

## Exchange 3

**Student:** What should I test before treating the baseline as complete?

**Assistant:** Check the six-turn Pasta example, task limits, table occupancy, invalid-action retries, abandonment, cancellation, payment order and payment-time satisfaction snapshots. Also replay one complete victory and one defeat. The Stage 1 README documents the victory expectation as five paid customers, $66.00 and 79.20 average satisfaction. Those are documented expectations, not a claim that a test was executed in this dialogue.

## Reference material

- [Stage 1 README](../../stage-1/README.md)
- [Stage 1 source](../../stage-1/src/restaurantrush/)
- [Stage 1 tests](../../stage-1/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

