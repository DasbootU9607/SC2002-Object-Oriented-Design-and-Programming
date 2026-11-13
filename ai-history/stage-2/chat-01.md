# Wong Yong Sheng Jordon Stage 2 Project Dialogue

Member allocation: Wong Yong Sheng Jordon

Focus: UML class diagrams

Stage: 2

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** How should the Stage 2 diagram show the new customer behaviours?

**Assistant:** Add VIPCustomer and CriticCustomer as subclasses of Customer, alongside RegularCustomer. Show the overridden operations that explain their behaviour, such as waitingLoss, adjustBill and chooseMeal where applicable. The inheritance arrow points toward Customer. Keep the shared lifecycle in the superclass instead of duplicating its state and operations in every subclass.

## Exchange 2

**Student:** Do Waiter and GameEngine need direct associations to every customer subclass?

**Assistant:** Only draw dependencies justified by actual code. The important design point is that ordinary service logic works with Customer references and relies on dynamic dispatch. Adding direct relationships to every subtype can falsely suggest that the controller needs subtype-specific knowledge. Creation and registration dependencies belong where concrete instances are actually constructed.

## Exchange 3

**Student:** How do I check that the second diagram is more than an edited copy of the first?

**Assistant:** Compare it with the Stage 2 source. Confirm the new subclasses, the setup changes, relevant inherited operations and the absence of later features. Recheck associations and multiplicities instead of assuming that all copied relationships are correct. Record which code version the diagram represents so later UI refactoring does not create an unexplained mismatch.

## Reference material

- [Stage 2 README](../../stage-2/README.md)
- [Stage 2 source](../../stage-2/src/restaurantrush/)
- [Stage 2 tests](../../stage-2/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

