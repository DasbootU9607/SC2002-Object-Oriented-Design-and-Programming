# Zhang Xiaozhuang Stage 1 Project Dialogue
Member allocation: Zhang Xiaozhuang

Focus: Requirements and system architecture

Stage: 1

The stage scope is based on the current working source and documentation. Historical Git tags may differ in presentation details.

## Exchange 1

**Student:** What should the requirement analysis say about the player's role?

**Assistant:** The player is the restaurant manager and chooses how limited staff actions are allocated. The player chooses seating, ordering, serving and kitchen priorities, but never chooses the customer's meal. The base game has 18 turns, two tables and six scheduled arrivals. Victory requires all three targets after turn 18: four payments, at least $45.00 revenue and average paid-customer satisfaction of at least 60.

## Exchange 2

**Student:** How should I separate the console from game rules?

**Assistant:** Keep command parsing and display at the boundary. Let GameEngine coordinate turn phases, and let staff and domain objects perform and validate operations. ConsoleUI may reject malformed command syntax, but checks such as whether an order is READY belong to the game objects. The same domain rules should apply whether commands come from a keyboard or a replay file.

## Exchange 3

**Student:** What responsibilities belong to Customer, Order and GameState?

**Assistant:** Customer owns its service status and satisfaction. Order owns preparation progress, its order status and the locked bill. GameState maintains the restaurant's collections, queue, tables and recorded payments. These responsibilities prevent the console from becoming the owner of all mutable state. The report should explain collaboration between these objects as well as listing their names.

## Reference material

- [Stage 1 README](../../stage-1/README.md)
- [Stage 1 source](../../stage-1/src/restaurantrush/)
- [Stage 1 tests](../../stage-1/test/restaurantrush/GameTests.java)
- Provided report draft: the named member's contribution and related report sections.

