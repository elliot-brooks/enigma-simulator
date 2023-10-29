# Enigma Simulator
A Simulation of the Enigma Machine used to encrypt/decrypt messages amongst the German forces during WW2. This is a **Java** based project.

## How to run the program
The program can be compiled by using
```
// Windows
./build_tools/Windows/compile_all.bat

// Linux/MacOS
./build_tools/MacOS/compile_all.sh
```
### Command Line Interface
You can configure the enigma machine settings by using `config/enigma_settings.xml`

The program can be ran using 
```
// Windows
./build_tools/Windows/run_cli.bat

// Linux/MacOS
./build_tools/MacOS/run_cli.sh
```
### GUI
TO BE CONTINUED...

### Creating custom components
`config` contains `rotor_bank.xml` and `reflector_bank.xml`. These files can be used to create custom rotors and reflectors which can be referenced by name in the CLI or GUI. Below shows some examples of custom components.

```
<rotor>
    <name>MyCustomRotor</name>
    <encoding>ZYXWVUTSRQPONMLKJIHGFEDCBA</encoding>
    <turnover_position>E</turnover_position>
</rotor>
```
```
<reflector>
    <name>MyCustomReflector</name>
    <encoding>ZYXWVUTSRQPONMLKJIHGFEDCBA</encoding>
</reflector>
```
**Note** : It is important to define the components correctly, or they will be ignored when parsed

## Included Rotor Configurations
- $I$ Rotor
- $II$ Rotor
- $III$ Rotor
- $IV$ Rotor
- $V$ Rotor

## Included Reflector Configurations
- **UKW-A** Reflector
- **UKW-B** Reflector
- **UKW-C** Reflector
---
**Created by**

@elliot-brooks
