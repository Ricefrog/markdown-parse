#!/usr/bin/bash

CLASSPATH=lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:.

java -cp $CLASSPATH MarkdownParse $1
