package de.jki.phone.kieser.test;

import de.jki.phone.kieser.data.Machine;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestMachine extends TestCase {
    private Machine ma;
    private final String[] attrNames = new String[] {"Name1", "Name2"};
    private final String id = "id";
    private final String descr = "description";
    private final short minRom = (short) 1;
    private final short maxRom = (short) 120;
    private final short maxPounds = (short) 400;


    public TestMachine() {
        super(1, "Test Maschine");
    }

    public void setUp() throws Exception {
        ma = new Machine(id, descr, minRom, maxRom, maxPounds, attrNames);
    }

    public void test(int testNumber) throws Throwable {
        switch (testNumber) {
            case 0: testCreatedMachine(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }

    private void testCreatedMachine() throws Exception {
        assertEquals(id, ma.getId());
        assertEquals(descr, ma.getDescription());
        assertEquals(minRom, ma.getMinRom());
        assertEquals(maxRom, ma.getMaxRom());
        assertEquals(maxPounds, ma.getMaxPounds());
        assertEquals(attrNames, ma.getValueNames());
    }
}
