# Tian Yujia Stage 1 Project Dialogue

Member allocation: Tian Yujia

Focus: Sequence modelling and reflection

Stage: 1

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** Which baseline scenario best reveals ordering mistakes?

**Assistant:** Use the brief's Regular customer ordering Pasta. The customer arrives and is seated on turn 1, orders while Chef prepares unit 1 on turn 2, and reaches READY after unit 2 on turn 3. Waiter serves on turn 4; eating finishes on turn 5; Cashier collects payment on turn 6. This exposes both preparation-to-serving and ready-to-pay-to-payment delays.

## Exchange 2

**Student:** Should the sequence diagram show only the manager and GameEngine?

**Assistant:** No. That would hide the object collaboration being assessed. Show the CLI forwarding manager decisions to the controller, the controller delegating to staff, and staff interacting with Customer, Order and GameState as appropriate. Message names should match actual operations. Use guards for relevant state conditions and loops for repetition instead of replacing the entire flow with narrative labels.

## Exchange 3

**Student:** How can I connect this scenario to a meaningful reflection?

**Assistant:** Discuss why action order and state transition timing are difficult to coordinate. Use the Pasta example to explain what must be checked and which objects enforce it. A reflection should connect a concrete issue to a design decision and verification evidence. Do not invent an earlier bug, attempted fix or personal experience merely to make the reflection sound more detailed.

## Reference material

- [Stage 1 README](../../stage-1/README.md)
- [Stage 1 source](../../stage-1/src/restaurantrush/)
- [Stage 1 tests](../../stage-1/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

