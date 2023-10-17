package main.cli;

import org.xml.sax.SAXException;

import main.enigma.ComponentCache;
import main.enigma.Enigma;
import main.enigma.exceptions.MissingEncodingException;

public class EnigmaSimulator {
    public static void main(String[] args) throws MissingEncodingException {
        try {
            ComponentCache cache = new ComponentCache();
            cache.initialise();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        Enigma machine = Enigma.createDefaultEnigma();
        System.out.println(machine.getCurrentSettings() + '\n');
        System.out.println(machine.encrypt("Hello World"));
    }
}
