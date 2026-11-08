# Restaurant Rush video reference files

This folder is the single entry point for the submitted video's source code, replay inputs, diagrams and cover images. **Segment 2 uses the original turn-13 comparison; the other segments follow the later recording script.** The selected reference script is [final-video.md](scripts/final-video.md).

本文件夹是视频配套资料统一入口：Segment 2 为旧版第 13 回合“先上菜还是先接单”；其余段落按新版。Segment 7 展示 Stage 3 类图和 Customer 4 的顺序图（分为三张连续图）。下方第 16 回合资料仅作为历史备选保留，不属于本视频的案例。

## Quick links by video segment

| Segment | Files to inspect |
| --- | --- |
| 1. Launching the project | [Stage 1 instructions](stage-1/README.md), [Windows launcher](stage-1/run.ps1), [Main.java](stage-1/src/restaurantrush/Main.java) |
| 2. Original turn-13 manager choice | [Strategy A: serve customer 4 first](stage-3/examples/victory.txt), [Strategy B: order for customer 5 first](video-demo/inputs/strategy-b.txt) |
| 3. Customer 1 Burger lifecycle, turns 1–4 | [Replay: first four turns](stage-3/examples/victory.txt), [Order](stage-3/src/restaurantrush/Order.java), [Customer](stage-3/src/restaurantrush/Customer.java), [Waiter](stage-3/src/restaurantrush/Waiter.java), [Chef](stage-3/src/restaurantrush/Chef.java), [Cashier](stage-3/src/restaurantrush/Cashier.java) |
| 4. Stage 2 VIP behaviour | [Stage 2 replay: turns 4–6](stage-2/examples/victory.txt), [VIPCustomer](stage-2/src/restaurantrush/VIPCustomer.java), [Customer](stage-2/src/restaurantrush/Customer.java) |
| 5. Host code explanation | [Host.seat](stage-3/src/restaurantrush/Host.java), [GameState.seat](stage-3/src/restaurantrush/GameState.java), [GameEngine.assignHost and assignWaiter](stage-3/src/restaurantrush/GameEngine.java) |
| 6. Stage 3 victory and defeat | [Victory: also Strategy A](stage-3/examples/victory.txt), [Defeat](stage-3/examples/defeat.txt), [arrival rules](stage-3/src/restaurantrush/GameSetup.java), [meal choices](stage-3/src/restaurantrush/Menu.java), [Critic preference](stage-3/src/restaurantrush/CriticCustomer.java) |
| 7. Stage 3 class diagram and Customer 4 sequence diagram | [Diagram guide and previews](video-demo/assets/README.md), [class diagram](video-demo/assets/class-diagram.png), Customer 4 sequence parts [A: preparation](video-demo/assets/sequence-a.png), [B: turn-13 decision](video-demo/assets/sequence-b.png), [C: payment and result](video-demo/assets/sequence-c.png) |

## Run the original turn-13 comparison

Download the repository and open PowerShell **inside this `video-resources` folder**. JDK 17 or newer is required; the application uses only the Java standard library and works offline. Each launcher compiles its own stage before running it.

```powershell
# From the repository root, enter this folder first:
Set-Location video-resources

# Strategy A: turn 13 serve 4; turn 14 order 5.
.\stage-3\run.ps1 --replay examples/victory.txt

# Strategy B: turn 13 order 5; turn 14 serve 4.
.\stage-3\run.ps1 --replay ../video-demo/inputs/strategy-b.txt
```

The two strategies share turns 1–12. Subsequent assignments also account for table occupancy and staff capacity. They are not complete runs differing in only one command.

| Original scenario | Paid | Served | Revenue | Average paid satisfaction | Result |
| --- | ---: | ---: | ---: | ---: | --- |
| Strategy A | 5 | 6 | $72.78 | 85.60 | VICTORY |
| Strategy B | 5 | 5 | $72.78 | 84.00 | VICTORY |

Customer 4 is served at satisfaction 76 in A and 68 in B. A collects their payment on turn 15; B collects it on turn 16. Both runs complete all 18 turns.

## Other reproducible examples

Run these commands from `video-resources` as well:

```powershell
.\stage-1\run.ps1 --replay examples/victory.txt
.\stage-1\run.ps1 --replay examples/defeat.txt
.\stage-2\run.ps1 --replay examples/victory.txt
.\stage-2\run.ps1 --replay examples/defeat.txt
.\stage-3\run.ps1 --replay examples/defeat.txt

# Separate demonstration: promotion on turns 3 and 4,
# a rejected second activation on turn 5, and locked bills.
.\stage-3\run.ps1 --replay ../video-demo/inputs/happy-hour.txt

# Run the three regression suites: 99 + 110 + 159 = 368 assertions.
.\verify.ps1
```

On macOS/Linux, replace `.\stage-N\run.ps1` with `sh stage-N/run.sh`, and `.\verify.ps1` with `sh verify.sh`.

| Replay | Paid | Served | Revenue | Average paid satisfaction | Result |
| --- | ---: | ---: | ---: | ---: | --- |
| Stage 1 victory | 5 | 5 | $66.00 | 79.20 | VICTORY |
| Stage 2 victory | 5 | 5 | $65.10 | 78.00 | VICTORY |
| Any stage's supplied defeat | 0 | 0 | $0.00 | N/A | DEFEAT |
| Separate Happy Hour demonstration | 2 | 2 | $21.48 | 91.00 | DEFEAT |

The Happy Hour input is a feature demonstration, not the victory strategy. Replay files supply ordinary commands through the same ConsoleUI as interactive play; they do not inject results.

## Archived alternative turn-16 scenario — not used in this video

Use these files only if the video shows **two READY meals on turn 16** and compares which customer can pay before closing:

| Input | Paid | Served | Revenue | Average paid satisfaction |
| --- | ---: | ---: | ---: | ---: |
| [Combo first](video-demo/inputs/video-main.txt) | 4 | 5 | $63.78 | 80.00 |
| [Salad first](video-demo/inputs/video-salad-first.txt) | 4 | 5 | $48.48 | 86.00 |
| [Combo first without promotion](video-demo/inputs/video-no-promotion.txt) | 4 | 5 | $65.40 | 78.75 |

All three produce VICTORY. The service-order branches share turns 1–15; only the Waiter targets on turns 16 and 17 are swapped. Their satisfaction averages cover different sets of paying customers.

```powershell
.\stage-3\run.ps1 --replay ../video-demo/inputs/video-main.txt
.\stage-3\run.ps1 --replay ../video-demo/inputs/video-salad-first.txt
.\stage-3\run.ps1 --replay ../video-demo/inputs/video-no-promotion.txt
```

Matching sequence diagrams: [preparation](video-demo/assets/video-sequence-1.svg), [service decision](video-demo/assets/video-sequence-2.svg), [payment and result](video-demo/assets/video-sequence-3.svg). PNG copies and [diagram source data](video-demo/assets/video-sequence-source.json) are in the same directory.

## Recording scripts and covers

- **[Selected video reference script: old Segment 2, newer other segments](scripts/final-video.md)**
- [Archived original recording script](scripts/legacy-turn13.md)
- [Archived alternative recording script](scripts/alternative-turn16.md)
- [Relationship between the alternative scenario and the original report](scripts/REPORT_ALIGNMENT.md)
- [4K 16:9 cover PNG](covers/restaurant-rush-cover-4k-16x9.png)
- [Original-aspect-ratio cover PNG](covers/restaurant-rush-cover.png)
- [Optional command-copy helper for the original script](video-demo/Get-DemoCommands.ps1)

The scripts retain the proposed recording sequence and timings; these are not confirmed timestamps for the final video. Execute their commands from this folder. The cover images retain the provided cover wording. Original report diagrams are preserved at their supplied resolution; the video/report submission is separate from this reference folder.

## Source and development history

The runnable `stage-1`, `stage-2`, `stage-3` folders and verification scripts are copies of committed source at [7321dc8](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/tree/7321dc8a5a5007ad7fe84a843e9eef67cab21bab). This includes the console presentation update. The original historical stage tags retain their earlier interfaces.

- [Stage tags](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/tags)
- [Stage 1 to Stage 2](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/compare/stage-1...stage-2)
- [Stage 2 to Stage 3](https://github.com/DasbootU9607/SC2002-Object-Oriented-Design-and-Programming/compare/stage-2...stage-3)
- [Arrival and customer-type rules](stage-3/src/restaurantrush/GameSetup.java)
- [Default meal-choice rules](stage-3/src/restaurantrush/Menu.java)
- [Integer-cent calculations](stage-3/src/restaurantrush/Money.java)
- [Victory thresholds](stage-3/src/restaurantrush/GameResult.java)

The source copies keep this reference folder independently runnable. Development history remains in the repository's original stage paths and tags.

Validation before publication: all 368 regression assertions passed from this folder. All 11 listed complete replays ran to turn 18 with the results shown above. Local Markdown file links and the original script's command-copy helper were also checked.

## Folder structure

```text
video-resources/
  README.md                   Video evidence index and reproduction commands
  scripts/final-video.md      Selected script matching the confirmed segment choices
  scripts/                    Also retains earlier scripts as historical references
  stage-1/                    Runnable basic game, source, tests and examples
  stage-2/                    Runnable customer extension and examples
  stage-3/                    Runnable final game and examples
  video-demo/
    inputs/                   Additional comparison and promotion replays
    assets/README.md          Selected Stage 3 class and Customer 4 sequence diagrams
    assets/                   Selected diagrams and archived alternative diagrams
    Get-DemoCommands.ps1       Optional interactive command-copy helper
  covers/                     Cover PNGs, including 3840 x 2160
  verify.ps1                  Windows regression-suite launcher
  verify.sh                   macOS/Linux regression-suite launcher
```
