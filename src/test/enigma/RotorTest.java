package test.enigma;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.enigma.InvalidReflectorEncodingException;
import main.enigma.MissingEncodingException;
import main.enigma.Rotor;
import main.enigma.RotorFactory;
import main.enigma.Direction;
import main.tools.Tools;

public class RotorTest {

    private Rotor firstRotor;

    @Before
    public void setup() throws InvalidReflectorEncodingException {
        firstRotor = RotorFactory.buildPresetRotor(RotorFactory.I_ROTOR, 0, 0);
    }

    /**
     * Simulates a single-rotor enigma machine
     * 
     * @throws MissingEncodingException
     */
    @Test
    public void testEncryptionForward() throws MissingEncodingException {
        int[] inputText = { 0, 0, 0, 0 };
        String expectedOutput = "JKCH";
        StringBuilder sb = new StringBuilder();
        for (int i : inputText) {
            firstRotor.rotate();
            int newChar = firstRotor.encrypt(i, Direction.FORWARD);
            sb.append(Tools.convertIndexToCharacter(newChar));
        }
        assertEquals(expectedOutput, sb.toString());
    }

    @Test
    public void testEncryptionBackwards() throws MissingEncodingException {
        int[] inputText = { 9, 10, 2, 7 };
        firstRotor.setRotationPosition(0);
        String expectedOutput = "AAAA";
        StringBuilder sb = new StringBuilder();
        for (int i : inputText) {
            firstRotor.rotate();
            int newChar = firstRotor.encrypt(i, Direction.BACKWARD);
            sb.append(Tools.convertIndexToCharacter(newChar));
        }
        assertEquals(expectedOutput, sb.toString());
    }
}
