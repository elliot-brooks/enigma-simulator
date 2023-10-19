@echo off
dir /s /B *.java > sources.txt
javac @sources.txt --source-path .\src\ -d .\bin\ --class-path .\lib\*
java -cp .\bin --module-path .\lib\  main.cli.EnigmaSimulator 