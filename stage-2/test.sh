#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p build/tests
javac -encoding UTF-8 --release 17 -d build/tests src/restaurantrush/*.java test/restaurantrush/*.java
exec java -cp build/tests restaurantrush.GameTests
