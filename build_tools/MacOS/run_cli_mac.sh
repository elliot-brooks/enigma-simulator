#!/bin/bash
find src/main/ -name "*.java" > sources.txt
javac @sources.txt --source-path ./src/ -d ./bin/ --module-path ./lib/javafx-sdk-21.0.1-macos/lib --add-modules javafx.controls
java -cp ./bin --module-path ./lib/  main.cli.EnigmaSimulator 