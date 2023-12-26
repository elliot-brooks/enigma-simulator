
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.enigma_machine.enigma.Enigma;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;

public class EnigmaTest {

    @Test
    public void enigmaSettingsTest() {
        Enigma machine = Enigma.createDefaultEnigma();
        assertEquals(
                "Plugboard : []\n"
                        + //
                        "Reflector : UKW-B (YRUHQSLDPXNGOKMIEBFZCWVJAT)\n" + //
                        "Right Rotor : III\n" + //
                        "    Rotation : A\n" + //
                        "    Ring Setting : 1\n" + //
                        "    Encoding : BDFHJLCPRTXVZNYEIWGAKMUSQO\n" + //
                        "Middle Rotor : II\n" + //
                        "    Rotation : A\n" + //
                        "    Ring Setting : 1\n" + //
                        "    Encoding : AJDKSIRUXBLHWTMCQGZNPYFVOE\n" + //
                        "Left Rotor : I\n" + //
                        "    Rotation : A\n" + //
                        "    Ring Setting : 1\n" + //
                        "    Encoding : EKMFLGDQVZNTOWYHXUSPAIBRCJ\n" + //
                        "",
                machine.getCurrentSettings());
    }

    @Test
    public void configurationTest() throws PlugboardConnectionDoesNotExistException {
        Enigma machine = Enigma.createDefaultEnigma();
        
        List<String> plugboardPairings = new ArrayList<>();
        plugboardPairings.add("AB");
        plugboardPairings.add("YZ");
        machine.addCables(plugboardPairings);
        assertEquals("AB BA YZ ZY", machine.getPlugboard().getEncoding());
        machine.removeCable("AB");
        assertEquals("YZ ZY", machine.getPlugboard().getEncoding());


        machine.configureRotorRingSetting(0, 5);
        assertEquals(5, machine.getRotors().get(0).getRingSetting());

        machine.configureRotorRotation(0, 5);
        assertEquals(5, machine.getRotors().get(0).getRotationPosition());

        machine.resetMachine();
        assertEquals(0, machine.getRotors().get(0).getRingSetting());
        assertEquals(0, machine.getRotors().get(0).getRotationPosition());
        assertEquals("", machine.getPlugboard().getEncoding());

        
    }

    @Test
    public void defaultEncryptionTest() {
        Enigma machine = Enigma.createDefaultEnigma();

        assertEquals("BD ZGO", machine.encrypt("AA AAA", false));
    }

    @Test
    public void decryptionTest() {
        String input_text = "ABCDEFGHIJKLMNOPQRSTUVWXYZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBZZZZZ";
        Enigma machine1 = Enigma.createDefaultEnigma();
        Enigma machine2 = Enigma.createDefaultEnigma();

        String cybpherText = machine1.encrypt(input_text, true);
        assertEquals(input_text, machine2.encrypt(cybpherText, true));

    }

    @Test
    public void doubleStepTest() {
        String input_text = "QQQQQQ";
        Enigma machine = Enigma.createDefaultEnigma();
        machine.configureRotorRotations(new int[] { 19, 3, 16 });
        assertEquals("LIOTLD", machine.encrypt(input_text, true));
    }
    
}
