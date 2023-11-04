import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.enigma_machine.enigma.ComponentCache;

public class ComponentCacheTest {

    ComponentCache cache = new ComponentCache();

    @Test
    public void initialisationTest() throws SAXException {
        cache.initialise();
        assertEquals("EJMZALYXVBWFCRQUONTSPIKHGD", cache.getReflector("UKW-A").getEncoding());
        assertEquals("EKMFLGDQVZNTOWYHXUSPAIBRCJ", cache.getRotor("I").getEncoding());
    }

}
