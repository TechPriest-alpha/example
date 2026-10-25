#!/usr/bin/env pwsh
<#
.SYNOPSIS
    Compile and optionally test the spring-boot4 Maven project.

.DESCRIPTION
    Use -Clean for a trustworthy run: incremental builds can keep stale
    resources in target/classes (e.g. db/changelog) and mask configuration errors.

.EXAMPLE
    .\verify.ps1
    .\verify.ps1 -Clean -RunTests
    .\verify.ps1 -RunTests -TestClass SomeTestAPITest
#>
param(
    [switch]$RunTests,
    [switch]$Clean,
    [string]$TestClass
)

$ErrorActionPreference = 'Stop'
$root = (Resolve-Path (Join-Path $PSScriptRoot '..\..\..\..')).Path

Push-Location $root
try {
    if ($Clean) {
        Write-Host "==> mvn clean ($root)"
        & mvn -B -q clean
        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
    }

    Write-Host '==> mvn compile'
    & mvn -B -q -DskipTests compile
    if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

    if ($RunTests) {
        if ($TestClass) {
            Write-Host "==> mvn test -Dtest=$TestClass"
            & mvn -B test "-Dtest=$TestClass"
        }
        else {
            Write-Host '==> mvn test'
            & mvn -B test
        }
        exit $LASTEXITCODE
    }
}
finally {
    Pop-Location
}
