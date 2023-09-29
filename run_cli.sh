#!/bin/bash
javac src/main/CLI/EnigmaSimulator.java  --source-path src/ -d bin/ --class-path lib/
java -cp bin/:lib/  main.cli.EnigmaSimulator 