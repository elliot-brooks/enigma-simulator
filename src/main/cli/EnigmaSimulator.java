package main.cli;

import org.xml.sax.SAXException;

import main.enigma.ComponentCache;
import main.enigma.Enigma;

public class EnigmaSimulator {
    public static void main(String[] args) {
        try {
            ComponentCache cache = new ComponentCache();
            cache.initialise();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        String message = "H3LLO world!!";
        Enigma machine = Enigma.createDefaultEnigma();
        System.out.println(machine.getCurrentSettings() + '\n');
        System.out.println(message + " = \n" + machine.encrypt(message));
    }
}
