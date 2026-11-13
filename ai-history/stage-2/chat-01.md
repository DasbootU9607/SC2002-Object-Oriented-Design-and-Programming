# Deng Ruyu Stage 2 Project Dialogue

Member allocation: Deng Ruyu

Focus: OOD principles and stage-development evidence

Stage: 2

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How should I describe OCP when GameSetup still changes?

**Assistant:** State the scope of the claim. Customer behaviour is extended through subclasses while the stable service and turn-control logic remains unchanged. GameSetup still needs to instantiate and register the new types. This supports an extensible design at the relevant behavioural call sites; it does not mean that adding a feature requires zero edits anywhere in the application.

## Exchange 2

**Student:** What does LSP mean for these customer subclasses?

**Assistant:** A subtype should continue to participate in the common service lifecycle under the same contract. A VIP may adjust the bill and a Critic may choose differently, but neither should require the staff to bypass seating, preparation, serving or payment rules. Test behaviour through Customer references and check that all subtypes remain valid participants in the shared process.

## Exchange 3

**Student:** How should I analyse the Stage 1 to Stage 2 changes?

**Assistant:** Separate logical source changes from the addition of a complete stage folder. The stage documentation identifies VIPCustomer.java and CriticCustomer.java as new and GameSetup.java as changed; tests and documentation are also updated, with no source deletions. Explain the responsibility of each addition and why setup changes. Include the tag comparison and evidence that the shared lifecycle and regression checks still work.

## Reference material

- [Stage 2 README](../../stage-2/README.md)
- [Stage 2 source](../../stage-2/src/restaurantrush/)
- [Stage 2 tests](../../stage-2/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

