# Tian Yujia Stage 2 Project Dialogue

Member allocation: Tian Yujia

Focus: Sequence modelling and reflection

Stage: 2

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How can a Stage 2 use case demonstrate polymorphism?

**Assistant:** Follow a VIP order through the ordinary service sequence and include the customer-level pricing call. The client holds a Customer reference while the VIP implementation supplies its adjustment. Alternatively, follow a Critic's chooseMeal operation to the documented Pasta preference. The diagram should show the actual dispatch path rather than a controller branch labelled VIP or Critic.

## Exchange 2

**Student:** Should I use an alt fragment for each customer subtype?

**Assistant:** Not merely to reproduce dynamic dispatch as a manual type switch. An alt fragment is appropriate for a real conditional path in the scenario, such as a manager choosing between valid actions or an invalid action causing a retry. Show subtype behaviour through the corresponding operation call and note its result. This keeps the diagram consistent with the absence of type checks in the engine.

## Exchange 3

**Student:** What starting conditions make a manager-choice comparison convincing?

**Assistant:** Use the same initial state and the same commands up to the decision point. State the turn, available staff actions, occupied tables and relevant order states. Then identify the changed decision and trace its consequences. Without shared starting conditions, a different final result could arise from unrelated earlier decisions rather than the choice being discussed.

## Reference material

- [Stage 2 README](../../stage-2/README.md)
- [Stage 2 source](../../stage-2/src/restaurantrush/)
- [Stage 2 tests](../../stage-2/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

