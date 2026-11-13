# Mei Chuhan Stage 2 Project Dialogue

Member allocation: Mei Chuhan

Focus: Implementation and testing

Stage: 2

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** Where should the VIP discount and different waiting losses be implemented?

**Assistant:** Keep the common lifecycle in Customer and override the behaviour in its subclasses. Regular customers lose 8 satisfaction per waiting turn, VIPs lose 5 and Critics lose 12. VIPCustomer adjusts the bill to 90% of its original amount using integer-cent rounding. The engine and staff should call Customer methods rather than inspect the concrete customer type.

## Exchange 2

**Student:** The Critic chooses Pasta, but the third Regular customer already chooses Pasta. How can I test that the override matters?

**Assistant:** The scheduled Critic still satisfies the documented preference, but that particular ID does not distinguish the override from the ordinary cycle. Add a focused test using a Critic whose ID would otherwise select Burger or Salad. Invoke chooseMeal through a Customer reference and check that it still chooses Pasta. This tests the behavioural override rather than only one favourable schedule.

## Exchange 3

**Student:** How do I preserve the Stage 1 rules while adding the new customers?

**Assistant:** Retain the shared timeline and lifecycle tests, then add assertions for customer-specific waiting losses, VIP rounding and the Critic preference. Keep six arrivals, with customer 2 as VIP and customer 3 as Critic. Update only expectations affected by those behaviours: the documented Stage 2 victory remains five payments, with revenue $65.10 and average satisfaction 78.00. Serving and payment timing remain unchanged.

## Reference material

- [Stage 2 README](../../stage-2/README.md)
- [Stage 2 source](../../stage-2/src/restaurantrush/)
- [Stage 2 tests](../../stage-2/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

