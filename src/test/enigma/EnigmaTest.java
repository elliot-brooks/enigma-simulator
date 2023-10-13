package test.enigma;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.enigma.Enigma;
import main.enigma.MissingEncodingException;

public class EnigmaTest {

    @Test
    public void enigmaSettingsTest() {
        Enigma machine = Enigma.createDefaultEnigma();
        assertEquals(
                "Plugboard : [AA, BB, CC, DD, EE, FF, GG, HH, II, JJ, KK, LL, MM, NN, OO, PP, QQ, RR, SS, TT, UU, VV, WW, XX, YY, ZZ]\n"
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
    public void defaultEncryptionTest() throws MissingEncodingException {
        Enigma machine = Enigma.createDefaultEnigma();

        assertEquals("BDZGO", machine.encrypt("AAAAA"));
    }

}
