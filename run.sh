#!/bin/bash

# Runs MultiCast Video program
src=src/browser
jars=jars/*

os="`uname`"

echo Your OS is $os

# If not linux, we assume Windows.
case $os in
	Linux*) path=$src:$jars ;;
	*) path="$src;$jars" ;;
esac

java -Xmx1000g -cp $path Browser
