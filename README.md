# Restaurant Rush

An offline Java 17 command-line restaurant management game for SC2002.

## Runnable stages

| Folder | Git tag | Features |
| --- | --- | --- |
| [stage-1](stage-1/README.md) | `stage-1` | Complete basic game with Regular customers |
| [stage-2](stage-2/README.md) | `stage-2` | Adds VIP and Critic behaviours |

Each stage is self-contained. See its README for build, play, replay and test commands.
Source code is under `src/restaurantrush`, tests under `test/restaurantrush`,
and deterministic gameplay inputs under `examples`.

Repository: https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming

A result is evaluated only after all 18 turns. Victory requires at least four payments,
$45.00 revenue and average paid-customer satisfaction of 60.

## Stage 1 to Stage 2

New logical source files: `VIPCustomer.java`, `CriticCustomer.java`.
Changed: `GameSetup.java` registers two customer types and changes the banner.
`GameTests.java` adds behavioural checks and updates expected revenue/satisfaction; stage README explains the rules.
No source files were deleted. Engine, staff and UI are unchanged.
Each new folder includes independent copies of unchanged sources so it can run alone.
[Commit comparison](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/compare/stage-1...stage-2).
