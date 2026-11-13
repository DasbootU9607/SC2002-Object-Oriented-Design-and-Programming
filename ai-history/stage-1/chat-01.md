# Wong Yong Sheng Jordon Stage 1 Project Dialogue

Member allocation: Wong Yong Sheng Jordon

Focus: UML class diagrams

Stage: 1

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** What should the baseline class diagram contain?

**Assistant:** Include the main customer, order, table, menu, payment and state abstractions, together with the controller, staff and boundary classes needed to explain execution. Show relevant enums, important attributes and operations, visibility and meaningful relationships. Check the source version being described: later presentation changes in the working folders must not be silently attributed to an earlier Git tag.

## Exchange 2

**Student:** Should every field that references another object be drawn as composition?

**Assistant:** No. A field establishes a reference, not necessarily exclusive ownership. Choose composition only when the model and implementation justify strong ownership and lifecycle responsibility. Otherwise use an association, or aggregation if its weaker whole-part meaning is useful. For example, a table has at most one current occupant, so the association to Customer has multiplicity 0..1.

## Exchange 3

**Student:** Which encapsulation detail is useful to annotate?

**Assistant:** Use Order as a concrete example. Its status and completed preparation units are private and change through operations such as prepareOneUnit, serve, pay and cancel. Those operations check valid transitions. This shows encapsulation protecting behaviour, rather than merely demonstrating private fields with unrestricted setters. Explain the fixed bill separately as a preserved order-time value.

## Reference material

- [Stage 1 README](../../stage-1/README.md)
- [Stage 1 source](../../stage-1/src/restaurantrush/)
- [Stage 1 tests](../../stage-1/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

