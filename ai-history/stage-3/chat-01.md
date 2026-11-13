# Zhang Xiaozhuang Stage 3 Project Dialogue

Member allocation: Zhang Xiaozhuang

Focus: Requirements and system architecture

Stage: 3

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** Which Stage 3 features change the architecture, rather than just adding data?

**Assistant:** Host introduces another controlled staff phase. HappyHourPolicy introduces promotion state and pricing behaviour, with hooks at the start and end of a turn. ComboMeal extends the kinds of items accepted through MenuItem. Explain these different kinds of change separately: a new phase, a policy and a new implementation of an existing abstraction.

## Exchange 2

**Student:** Why does the manager activate Happy Hour before assigning staff?

**Assistant:** Activation applies to the current turn and the next. Taking this decision first establishes the policy before new orders are quoted. Existing eligible customers recover satisfaction at the active turn's start, then arrivals and staff actions occur. The interface should show the remaining duration clearly. Choosing continue does not cancel a promotion that is already active.

## Exchange 3

**Student:** What limitation should the architecture discussion acknowledge?

**Assistant:** GameState delegates quotations to a concrete HappyHourPolicy, so the implementation does not fully invert that dependency through an interface. A PricingPolicy interface could make alternative promotions easier to substitute, but would add an abstraction with only one current implementation. Explain why the concrete policy was sufficient for this scope while identifying when the alternative would become useful.

## Reference material

- [Stage 3 README](../../stage-3/README.md)
- [Stage 3 source](../../stage-3/src/restaurantrush/)
- [Stage 3 tests](../../stage-3/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

