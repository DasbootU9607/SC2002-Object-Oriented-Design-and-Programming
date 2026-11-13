# Wong Yong Sheng Jordon Stage 3 Project Dialogue

Member allocation: Wong Yong Sheng Jordon

Focus: UML class diagrams

Stage: 3

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How should ComboMeal relate to MenuItem and its components?

**Assistant:** ComboMeal realises the MenuItem interface, allowing Order and Chef to use the same abstraction for dishes and combos. It holds exactly two existing MenuItem components and derives its price and preparation units from them. Reusing component references is object composition in the programming sense, but it does not automatically justify an exclusive-ownership UML composition diamond.

## Exchange 2

**Student:** Which Stage 3 relationships need particular attention?

**Assistant:** Show the Host collaboration with the controller and state, the promotion policy used for quotations, and the new menu-item implementation. Also reflect affected attributes and operations in existing classes. Distinguish the policy instance created by GameEngine from the reference held by GameState; the same object should not appear to have two independent exclusive owners.

## Exchange 3

**Student:** How can the final diagram remain readable while demonstrating OOP?

**Assistant:** Keep principal responsibilities and significant operations, omit routine accessors where appropriate, and explain omissions in the legend. Annotate Customer polymorphism, MenuItem substitution and guarded state changes with specific methods. Use separate close-ups when necessary, but also provide a complete diagram. Export at a resolution or vector format that keeps labels readable after the report is converted to PDF.

## Reference material

- [Stage 3 README](../../stage-3/README.md)
- [Stage 3 source](../../stage-3/src/restaurantrush/)
- [Stage 3 tests](../../stage-3/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

