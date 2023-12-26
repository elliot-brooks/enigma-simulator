import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.enigma_machine.enigma.EnhancedEnigma;
import com.enigma_machine.enigma.exceptions.PlugboardConnectionDoesNotExistException;

public class EnhancedEnigmaTest {
    
    @Test
    public void configurationTest() throws PlugboardConnectionDoesNotExistException {
        EnhancedEnigma machine = EnhancedEnigma.createDefaultEnhancedEnigma();
        
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
        EnhancedEnigma machine = EnhancedEnigma.createDefaultEnhancedEnigma();
        String cypherText = machine.encrypt("HEL LO", true);
        machine.resetMachine();
        assertEquals("HEL LO", machine.decode(cypherText, true));
    }

    @Test
    public void doubleStepTest() {
        String input_text = "QQQQQQ";
        EnhancedEnigma machine = EnhancedEnigma.createDefaultEnhancedEnigma();
        machine.configureRotorRotations(new int[] { 19, 3, 16 });
        assertEquals("VBNWJA", machine.encrypt(input_text, true));
    }
}
