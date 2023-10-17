package test.enigma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.enigma.Enigma;

public class EnigmaTest {

    @Test
    public void enigmaSettingsTest() {
        Enigma machine = Enigma.createDefaultEnigma();
        assertEquals(
                "Plugboard : [AB, BA]\n"
                        + //
                        "Reflector : UKW-B (YRUHQSLDPXNGOKMIEBFZCWVJAT)\n" + //
                        "Right Rotor : III\n" + //
                        "    Current Rotation : 0\n" + //
                        "    Ring Setting : 0\n" + //
                        "    Encoding : BDFHJLCPRTXVZNYEIWGAKMUSQO\n" + //
                        "Middle Rotor : II\n" + //
                        "    Current Rotation : 0\n" + //
                        "    Ring Setting : 0\n" + //
                        "    Encoding : AJDKSIRUXBLHWTMCQGZNPYFVOE\n" + //
                        "Left Rotor : I\n" + //
                        "    Current Rotation : 0\n" + //
                        "    Ring Setting : 0\n" + //
                        "    Encoding : EKMFLGDQVZNTOWYHXUSPAIBRCJ\n" + //
                        "",
                machine.getCurrentSettings());
    }

    @Test
    public void defaultEncryptionTest() {
        Enigma machine = Enigma.createDefaultEnigma();

        assertEquals("ADZGO", machine.encrypt("BBBBB"));
    }

    @Test
    public void decryptionTest() {
        String input_text = "ABCDEFGHIJKLMNOPQRSTUVWXYZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBZZZZZ";
        Enigma machine1 = Enigma.createDefaultEnigma();
        Enigma machine2 = Enigma.createDefaultEnigma();

        String cybpherText = machine1.encrypt(input_text);
        assertEquals(input_text, machine2.encrypt(cybpherText));

    }

}
