param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('Stage1','Stage2','StrategyA','StrategyB','HappyHour')]
    [string]$Scenario,
    [ValidateRange(1,18)][int]$FromTurn = 1,
    [ValidateRange(1,18)][int]$ToTurn = 18,
    [switch]$Copy
)
$ErrorActionPreference = 'Stop'
if ($FromTurn -gt $ToTurn) { throw 'FromTurn must not exceed ToTurn.' }
$bundleRoot = Split-Path -Parent $PSScriptRoot
$paths = @{
    Stage1='stage-1/examples/victory.txt'
    Stage2='stage-2/examples/victory.txt'
    StrategyA='stage-3/examples/victory.txt'
    StrategyB='video-demo/inputs/strategy-b.txt'
    HappyHour='video-demo/inputs/happy-hour.txt'
}
$turn = 0
$commands = @(foreach ($line in Get-Content -LiteralPath (Join-Path $bundleRoot $paths[$Scenario])) {
    if ($line -match '^# Turn (\d+)\s*$') { $turn = [int]$Matches[1] }
    elseif ($line.Trim() -and $line -notmatch '^\s*#' -and $turn -ge $FromTurn -and $turn -le $ToTurn) { $line.Trim() }
})
if (!$commands.Count) { throw 'No commands found for the requested range.' }
if ($Copy) {
    Set-Clipboard -Value (($commands -join "`r`n") + "`r`n")
    Write-Host "Copied $($commands.Count) commands. Paste at the first prompt of turn $FromTurn in the matching game."
} else { $commands }
