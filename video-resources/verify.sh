#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
for stage in stage-1 stage-2 stage-3; do
    echo "Testing $stage"
    sh "$stage/test.sh"
done
echo "All three stages passed."
