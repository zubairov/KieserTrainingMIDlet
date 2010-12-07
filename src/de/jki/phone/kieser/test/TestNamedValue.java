package de.jki.phone.kieser.test;

import de.jki.phone.kieser.persistence.data.NamedValue;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestNamedValue extends TestCase {
    private final static String NAME = "Hasi";
    private final static String VALUE = "rulez";

    public TestNamedValue() {
        super(5, "Test name value");
    }

    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0: testInstanceWithName(); break;
            case 1: testInstanceWithNameAndValue(); break;
            case 2: testSerialize(); break;
            case 3: testSerializeDeserialize(); break;
            case 4: testFailingDeserialize(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }


    private void testInstanceWithName() throws Exception {
        NamedValue nv = new NamedValue(NAME);
        assertEquals(NAME, nv.getName());
        assertEquals("", nv.getValue());
    }


    private void testInstanceWithNameAndValue() throws Exception {
        NamedValue nv = new NamedValue(NAME, VALUE);
        assertEquals(NAME, nv.getName());
        assertEquals(VALUE, nv.getValue());
    }


    private void testSerialize() throws Exception {
        NamedValue nv = new NamedValue(NAME, VALUE);
        byte[] serialized = nv.serialize();
        assertEquals(17, serialized.length);
    }

    
    private void testSerializeDeserialize() throws Exception {
        NamedValue nv = new NamedValue(NAME, VALUE);
        byte[] serialized = nv.serialize();

        DataInputStream di = new DataInputStream(new ByteArrayInputStream(serialized));
        NamedValue deserialized = NamedValue.deserialize(di);
        assertEquals(nv.getName(), deserialized.getName());
        assertEquals(nv.getValue(), deserialized.getValue());
    }

    
    private void testFailingDeserialize() throws Exception {
        byte[] serialized = new NamedValue(NAME, VALUE).serialize();
        serialized[2] = ++serialized[2]; // corrupt record identifier!
        DataInputStream di = new DataInputStream(new ByteArrayInputStream(serialized));

        try {
            NamedValue deserialized = NamedValue.deserialize(di);
            fail();
        } catch (IOException iox) {
            // success!
        }
    }

}
