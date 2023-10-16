package main.cli;

import main.enigma.Enigma;
import main.enigma.exceptions.MissingEncodingException;

public class EnigmaSimulator {
    public static void main(String[] args) throws MissingEncodingException {
        Enigma machine = Enigma.createDefaultEnigma();
        System.out.println(machine.getCurrentSettings() + '\n');
        System.out.println(machine.encrypt("Hello World"));
    }
}
