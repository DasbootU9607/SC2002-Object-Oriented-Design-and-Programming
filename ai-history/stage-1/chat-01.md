# Deng Ruyu Stage 1 Project Dialogue

Member allocation: Deng Ruyu

Focus: OOD principles and stage-development evidence

Stage: 1

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How can the first-stage discussion demonstrate SRP without just listing definitions?

**Assistant:** Assign each claim to a responsibility visible in the implementation. ConsoleUI parses decisions, GameEngine coordinates turn order, staff objects perform their respective operations, and entities protect valid state changes. Explain why these responsibilities change for different reasons. Avoid claiming that a small method or short class alone proves the Single Responsibility Principle.

## Exchange 2

**Student:** What makes Stage 1 a genuine development snapshot?

**Assistant:** It should be a runnable basic game containing only its own required features, with source, build instructions and reproducible test inputs. A Git tag should point to the corresponding commit. A written statement that Stage 1 once existed is insufficient if the source at the tag already includes later-stage features. Preserve the snapshot so its behaviour can be examined independently.

## Exchange 3

**Student:** What evidence should accompany a baseline result?

**Assistant:** Give the environment requirements, exact run command, replay input and expected output. Explain that paid count and served count are distinct and that satisfaction is recorded at payment. Keep the fixed 18-turn base setting and show both victory and defeat. Distinguish expected values recorded in documentation from results actually obtained during a particular execution.

## Reference material

- [Stage 1 README](../../stage-1/README.md)
- [Stage 1 source](../../stage-1/src/restaurantrush/)
- [Stage 1 tests](../../stage-1/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

