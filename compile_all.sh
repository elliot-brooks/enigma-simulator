#!/bin/bash
find . -name "*.java" > sources.txt
javac @sources.txt --source-path ./src/ -d ./bin/ --class-path ./lib/junit-4.13.2.jar