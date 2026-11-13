# Deng Ruyu Stage 3 Project Dialogue

Member allocation: Deng Ruyu

Focus: OOD principles and stage-development evidence

Stage: 3

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** Do the new Host phase and Happy Hour hooks show that OCP has failed?

**Assistant:** Not automatically. Host introduces a new phase, and phase coordination belongs to GameEngine. Happy Hour also has start- and end-of-turn effects that require integration. Explain whether the edits are focused and preserve the existing service flow. OCP is useful for evaluating extension points, but it does not justify hiding necessary orchestration changes or claiming that all existing classes were untouched.

## Exchange 2

**Student:** How can the report discuss ISP and DIP accurately?

**Assistant:** MenuItem is small and contains operations needed by its implementations, so it can illustrate avoiding unrelated obligations. Do not claim a larger interface was historically split unless that occurred. For DIP, acknowledge that GameState depends on the concrete HappyHourPolicy. Discuss a PricingPolicy interface as an alternative, with the trade-off of introducing an abstraction for one current implementation.

## Exchange 3

**Student:** What should the final evolution and reflection evidence contain?

**Assistant:** For Stage 2 to Stage 3, describe Host, ComboMeal and HappyHourPolicy, then explain changes to setup, menu selection, recovery, quotations, coordination and input handling. Identify deleted files explicitly, even when there are none. Connect earlier-behaviour checks to the unchanged rules and give the actual stage links. Personal reflections should describe genuine contributions, learning and challenges.

## Reference material

- [Stage 3 README](../../stage-3/README.md)
- [Stage 3 source](../../stage-3/src/restaurantrush/)
- [Stage 3 tests](../../stage-3/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

