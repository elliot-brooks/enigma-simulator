
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.enigma_machine.enigma.Plugboard;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionAlreadyEstablishedException;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;
import com.enigma_machine.tools.Constants;

public class PlugboardTest {

    private static Plugboard plugboardUnderTest = new Plugboard();
    int aIndex = ('A' - Constants.JAVA_A_VALUE);
    int eIndex = ('E' - Constants.JAVA_A_VALUE);

    @Test
    public void encryptionTest() {
        try {
            plugboardUnderTest.addCable(aIndex, eIndex);
        } catch (PlugboardConnectionAlreadyEstablishedException e) {
            fail("Failed to add new connection to plugboard");
        }
        assertEquals(eIndex, plugboardUnderTest.encrypt(aIndex));
        assertEquals(aIndex, plugboardUnderTest.encrypt(eIndex));

        try {
            plugboardUnderTest.removeCable(aIndex, eIndex);
        } catch (PlugboardConnectionDoesNotExistException e) {
            fail("Failed to remove connection from plugboard");
        }
        assertEquals(aIndex, plugboardUnderTest.encrypt(aIndex));
        assertEquals(eIndex, plugboardUnderTest.encrypt(eIndex));
    }

    @Test
    public void initialisationTest() {
        plugboardUnderTest = new Plugboard();
        for (int i = 0; i < 26; i++) {
            assertEquals(i, plugboardUnderTest.encrypt(i));
        }
    }

}