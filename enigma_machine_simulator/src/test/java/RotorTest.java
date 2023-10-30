
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.enigma_machine.enigma.Direction;
import com.enigma_machine.enigma.Rotor;
import com.enigma_machine.enigma.RotorFactory;
import com.enigma_machine.enigma.exceptions.InvalidReflectorEncodingException;
import com.enigma_machine.tools.Tools;

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
    public void testEncryptionForward() {
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
    public void testEncryptionBackwards() {
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
