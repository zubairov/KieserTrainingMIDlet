package de.jki.phone.kieser.test;

import de.jki.phone.kieser.data.MachineRegistry;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestMachineRegistry extends TestCase {
    private MachineRegistry reg;

    public TestMachineRegistry() {
        super(2, "Test MachineRegistry");
    }

    public void setUp() {
        reg = new MachineRegistry();
    }

    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0 : testAvailable(); break;
            case 1 : testGetAll(); break;
            default: throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }

    private void testAvailable() {
        assertEquals("Number of machines", 40 , reg.getAvailable().length);
    }

    private void testGetAll() {
        String[] all = reg.getAvailable();
        for (int i = 0; i < all.length; i++) {
            assertEquals("First", all[i] , reg.getMachine(all[i]).getId());
        }
    }
}
