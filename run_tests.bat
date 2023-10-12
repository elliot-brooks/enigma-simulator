@echo off
javac .\src\test\enigma\*.java  --source-path .\src\ -d .\bin\ --class-path .\lib\*
java --enable-preview -cp .\bin\;.\lib\junit-4.13.2.jar;.\lib\hamcrest-core-1.3.jar org.junit.runner.JUnitCore^
 test.enigma.PlugboardTest^
 test.enigma.ReflectorTest^
 test.enigma.RotorTest