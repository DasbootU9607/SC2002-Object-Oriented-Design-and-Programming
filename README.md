# Restaurant Rush

An offline Java 17 command-line restaurant management game for SC2002.

Repository: https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming

## Recorded demonstration

Play the full demonstration directly in the video player below.

https://github.com/user-attachments/assets/45a1620d-9534-41c2-940e-3e1de5dfab6b

[Download the original MP4](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/releases/download/demo-video-final/Demo-Video-Final.mp4) · [Reproducible video resources](video-resources/README.md)

Duration: approximately 14 minutes 42 seconds. The embedded version is compressed for online playback; the download preserves the original video. Segment start times are listed below for reference. Original timecodes use `HH:MM:SS:FF` at 30 fps, where `FF` is the frame number.

| Segment | Content | Original timecode | Start time |
| --- | --- | --- | --- |
| 1 | Launching the submitted project | `00:00:08:18` | `00:08.600` |
| 2 | Manager choices and constrained staff actions | `00:01:30:24` | `01:30.800` |
| 3 | Dish lifecycle: READY, SERVED and payment | `00:02:57:14` | `02:57.467` |
| 4 | VIP customer behaviour | `00:04:29:12` | `04:29.400` |
| 5 | Stage 3 feature: Host responsibilities and implementation | `00:05:31:13` | `05:31.433` |
| 6 | Reproducible victory and defeat | `00:06:55:17` | `06:55.567` |
| 7 | Stage tag comparisons, Stage 3 class diagram and Customer 4 sequence diagram | `00:08:54:06` | `08:54.200` |

## Video reference files

[Open the video reference folder](video-resources/README.md) to find supporting files by video segment, with reproduction commands and expected results. The folder brings together the source code, replay inputs and diagrams demonstrated in the video so the results can be reproduced.

Key reference files and folders:

```text
video-resources/
├── README.md              Segment index, run commands and expected results
├── stage-1/               Stage 1 source, launchers, tests and replays
├── stage-2/               Stage 2 source, VIP examples and tests
├── stage-3/               Stage 3 source, victory and defeat replays
├── video-demo/
│   ├── inputs/            Additional replays, including turn-13 Strategy B
│   └── assets/            Class diagram, Customer 4 sequence diagram and guide
└── verify.ps1 / verify.sh  Test launchers for all three stages
```

## Build and play

Install JDK 17 or newer. No Maven, Gradle, external libraries, server or network access is needed.

Windows, from the repository root:

```powershell
.\stage-3\run.ps1
.\stage-3\run.ps1 --replay examples/victory.txt
.\stage-3\run.ps1 --replay examples/defeat.txt
.\verify.ps1
```

macOS/Linux:

```sh
sh stage-3/run.sh
sh stage-3/run.sh --replay examples/victory.txt
sh stage-3/run.sh --replay examples/defeat.txt
sh verify.sh
```

Replace `stage-3` with `stage-1` or `stage-2` to run an earlier stage.
The scripts resolve paths from their own folder. Each stage is independently compilable.

For a direct build from a stage folder:

```sh
mkdir -p build/classes
javac -encoding UTF-8 --release 17 -d build/classes src/restaurantrush/*.java
java -cp build/classes restaurantrush.Main
```

## Console interface

All three stages display a welcome banner, a menu and a short command guide.
Each turn has separate status, queue, table, active-order, staff-action and
end-of-turn sections. Commands include descriptions and current target IDs;
long feedback wraps within a 72-column layout. Finished or cancelled orders
leave the active-order display, and the closing page shows the final score,
each guest's final status and a goodbye banner.

Stage 3 also displays Happy Hour availability and remaining duration, and gives
the Host a separate prompt. The input commands and turn order are unchanged;
the supplied replay files work without extra menu choices or Enter presses.
Plain ASCII output needs no terminal colours, screen-clearing support or libraries.
Use a monospaced terminal font and a window at least 72 columns wide.

`ConsoleUI` parses decisions and calls `GameEngine`; `ConsoleView` renders the
screens and reads state for display. The view does not change game state or
decide whether an action is valid. Both classes are included in each stage so
that all three folders remain independently runnable. This presentation update
is applied to the working source; the original stage tags remain historical snapshots.

The visual reference is [FYPMS](https://github.com/pufanyi/FYPMS), particularly
its welcome/exit pages and separators. The Restaurant Rush banners and layout
are implemented for this game's commands and state.

## Runnable stages

| Folder | Git tag | Features | Behavioural assertions |
| --- | --- | --- | ---: |
| [stage-1](stage-1/README.md) | `stage-1` | Complete basic game with Regular customers | 99 |
| [stage-2](stage-2/README.md) | `stage-2` | Adds VIP and Critic behaviours | 110 |
| [stage-3](stage-3/README.md) | `stage-3` | Adds Host, ComboMeal and Happy Hour | 159 |

Every folder contains `src/restaurantrush`, `test/restaurantrush`, `examples`,
and its own build/run/test scripts. Repeated source files are intentional: stages do not depend on each other.

The tags are successive real development snapshots. The first tag contains only Stage 1;
the second adds Stage 2; the third adds Stage 3. To inspect a historical snapshot,
use a separate checkout or `git switch --detach stage-1`; return with `git switch main`.

## Rules and reproducibility

The base game always uses 18 turns, two tables and six arrivals on turns 1, 4, 7, 10, 13 and 16.
A result is evaluated only after all 18 turns. Victory requires at least four payments,
$45.00 revenue and average paid-customer satisfaction of 60.

Customers choose their own meals deterministically; no seed or external data is needed.
The stage READMEs explain food preferences, customer schedules, exact turn order, money rounding,
Host priority and Happy Hour. Prices are stored as integer cents and locked when orders are taken.
Average satisfaction uses payment-time snapshots; it is N/A before any payment.
GUI, networking, multiplayer and persistent save/load are outside the mandatory implementation.

| Replay | Paid | Served | Revenue | Paid satisfaction average | Outcome |
| --- | ---: | ---: | ---: | ---: | --- |
| Stage 1 victory | 5 | 5 | $66.00 | 79.20 | VICTORY |
| Stage 2 victory | 5 | 5 | $65.10 | 78.00 | VICTORY |
| Stage 3 victory | 5 | 6 | $72.78 | 85.60 | VICTORY |
| Each stage defeat | 0 | 0 | $0.00 | N/A | DEFEAT |

Every supplied replay runs all 18 turns. The Stage 3 victory activates Happy Hour on turn 4,
processes a VIP order at the promotional price, prepares a three-unit combo,
and uses the Host in addition to the Waiter.

## Stage 1 to Stage 2

[GitHub comparison](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/compare/stage-1...stage-2)

New logical source files: `VIPCustomer.java`, `CriticCustomer.java`.

Changed source: `GameSetup.java` registers the mixed arrival schedule and changes the stage banner.
`GameTests.java` adds polymorphism checks and updates replay expectations; the stage README documents the rules.
No source files were deleted. GameEngine, staff, ConsoleUI and existing model classes are unchanged.

## Stage 2 to Stage 3

[GitHub comparison](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/compare/stage-2...stage-3)

New logical source files: `Host.java`, `ComboMeal.java`, `HappyHourPolicy.java`.

| Changed source | Reason |
| --- | --- |
| `GameSetup.java` | Register the combo and update the stage banner |
| `Menu.java` | Store combo choices and implement the documented selection rule |
| `Customer.java` | Encapsulate satisfaction recovery without reviving departed customers |
| `GameState.java` | Delegate price quotations to the active policy |
| `GameEngine.java` | Add the Host phase and policy hooks around the existing turn sequence |
| `ConsoleUI.java` | Collect the new choices and display remaining Happy Hour duration |

The test suite, stage README and both replay files are extended for the new phases.
No source files were deleted. Order lifecycle, preparation, serving, payment, table release,
customer subclasses, integer money and result rules remain unchanged.

Since stages live in separate self-contained folders, GitHub compares added snapshots.
For a focused source comparison locally, use:
```sh
git diff --no-index stage-1/src stage-2/src
git diff --no-index stage-2/src stage-3/src
```
A diff exit code of 1 means differences were found, not a failed build.

## Validation

`verify.ps1` / `verify.sh` runs all three dependency-free suites, totalling 368 assertions.
They cover the brief's Pasta timeline, staff capacity, invalid input retries, queue/table rules,
abandonment and cancellation, payment order, price and satisfaction snapshots, both endings,
polymorphic customer behaviour, Host actions, combo rounding, and Happy Hour duration/lock-in.
The Stage 3 suite also checks each victory threshold separately.

Sources were compiled with `javac --release 17 -Xlint:all -Werror`.
Command-line help, invalid arguments and missing replay files were also checked.
Earlier behaviour is exercised in Stage 3 with the Host waiting and promotion unused.

## Course submission materials

The repository provides runnable source, tests, replay inputs, UML diagram references and the recorded demonstration linked above. The course submission also requires the PDF report, signed original-work declarations and individual GAI-use declarations.
