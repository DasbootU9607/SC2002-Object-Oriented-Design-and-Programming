$ErrorActionPreference = 'Stop'
foreach ($stage in @('stage-1','stage-2','stage-3')) {
    Write-Output ("Testing " + $stage)
    & (Join-Path $PSScriptRoot ($stage + '/test.ps1'))
    if ($LASTEXITCODE -ne 0) { throw ("Verification failed for " + $stage) }
}
Write-Output 'All three stages passed.'
