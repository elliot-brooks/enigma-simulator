@echo off
dir /s /B *.java > sources.txt
javac @sources.txt --source-path .\src\ -d .\bin\ --class-path .\lib\* --module-path .\lib\openjfx-21.0.1_windows-x64_bin-sdk\javafx-sdk-21.0.1\lib --add-modules javafx.controls
java -cp .\bin --module-path .\lib\openjfx-21.0.1_windows-x64_bin-sdk\javafx-sdk-21.0.1\lib --add-modules javafx.controls main.app.TestApp 