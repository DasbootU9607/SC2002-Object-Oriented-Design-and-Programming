# Zhang Xiaozhuang Stage 2 Project Dialogue

Member allocation: Zhang Xiaozhuang

Focus: Requirements and system architecture

Stage: 2

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** Does adding VIP and Critic change the basic scope of the game?

**Assistant:** It extends customer behaviour while retaining the original service flow and winning conditions. The base run still has six arrivals. Replace two Regular arrivals with a VIP and a Critic rather than adding extra customers. Document the Critic's distinct preference and show that the same staff operations serve every customer type.

## Exchange 2

**Student:** Should I introduce separate strategy interfaces for waiting, pricing and food choice?

**Assistant:** That is a reasonable alternative when behaviours need to be combined independently. For this project, the differences are small and all customers share one lifecycle, so subclass overrides are a simpler solution. Explain the trade-off: inheritance keeps the implementation compact, but combinations such as a customer with both VIP and Critic behaviour may require another subclass.

## Exchange 3

**Student:** How should I explain the updated deterministic customer schedule?

**Assistant:** State that customer 2 is VIP, customer 3 is Critic, and the other four are Regular. Regular and VIP customers use the ID-based Burger, Salad, Pasta cycle. Critics always choose Pasta. Document these choices in the stage README and requirement discussion so a grader can reproduce the expected run without guessing or relying on randomness.

## Reference material

- [Stage 2 README](../../stage-2/README.md)
- [Stage 2 source](../../stage-2/src/restaurantrush/)
- [Stage 2 tests](../../stage-2/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

