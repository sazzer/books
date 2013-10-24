#!/bin/bash

pushd `dirname $0`
BASE_DIR=`pwd -P`

shifter --walk --recursive --build-dir $BASE_DIR/build/webapp/scripts/
yogi loader --yes --start $BASE_DIR/src/main/scripts --json $BASE_DIR/build/webapp/scripts/loader.json --js $BASE_DIR/build/webapp/scripts/loader.js

popd
