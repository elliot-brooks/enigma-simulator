package main.cli;

import main.enigma.Enigma;

public class EnigmaSimulator {
    public static void main(String[] args) {
        Enigma machine = Enigma.createDefaultEnigma();
        System.out.println(machine.getCurrentSettings());
    }
}
