@echo off
dir /s /B *.java > sources.txt
javac @sources.txt --source-path .\src\ -d .\bin\ --class-path .\lib\*
java -cp .\bin;.\lib\jaylib-4.2.0-1.jar main.app.TestApp 