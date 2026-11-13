# Tian Yujia Stage 3 Project Dialogue

Member allocation: Tian Yujia

Focus: Sequence modelling and reflection

Stage: 3

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How does Customer 4 reach the turn-13 decision point?

**Assistant:** In the reported Stage 3 scenario, Customer 4 arrives on turn 10. Host seats the customer, allowing Waiter to take the combo order that turn. The Salad plus Pasta combo costs $24.30 and needs three preparation units. Chef advances it on turns 10, 11 and 12, making it READY after the turn-12 kitchen action. Its earliest service opportunity is turn 13.

## Exchange 2

**Student:** What should the two branches show?

**Assistant:** Strategy A serves Customer 4 on turn 13. Strategy B takes Customer 5's order first and serves Customer 4 on turn 14, producing one additional waiting loss of 8. Customer 4's payment-time satisfaction is therefore 76 in A and 68 in B. Represent the choice with an alt fragment and follow the full valid replay for subsequent actions; do not assume every later command remains identical.

## Exchange 3

**Student:** How should the scenario end and connect to the test evidence?

**Assistant:** Continue through eating and payment: Customer 4 pays on turn 15 in A and turn 16 in B. Then show the result evaluation after turn 18. The report records five payments and $72.78 revenue in both runs, with average paid satisfaction of 85.60 versus 84.00 and served counts of six versus five. Present these as reported outcomes and link them to the actual replay evidence.

## Reference material

- [Stage 3 README](../../stage-3/README.md)
- [Stage 3 source](../../stage-3/src/restaurantrush/)
- [Stage 3 tests](../../stage-3/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

