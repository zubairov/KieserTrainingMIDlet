package de.jki.phone.kieser.test;

import de.jki.phone.kieser.persistence.data.Training;
import de.jki.phone.kieser.persistence.data.TrainingUnit;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestTraining extends TestCase {
    private Training tr = new Training();
    private static final TrainingUnit[] TWO_UNITS
            = new TrainingUnit[] {new TrainingUnit("A1"), new TrainingUnit("B1")};

    public TestTraining() {
        super(5, "Run TestTraining");
    }

    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0: testDefaultInstance(); break;
            case 1: testWith2Units(); break;
            case 2: testSerialize(); break;
            case 3: testSerializeDeserialize(); break;
            case 4: testFailingDeserialize(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }


    private void testDefaultInstance() throws Exception {
        Training t = new Training();
        assertEquals(0, t.getCurrentUnit());
        assertEquals(0, t.getId());
        assertEquals("TR", t.getType());
        assertEquals(0, t.getUnits().length);
        assertTrue(t.isFinished());
        assertFalse(t.isDeleted());
    }


    private void testWith2Units() throws Exception {
        Training t = new Training();
        t.setUnits(TWO_UNITS);
        t.setCurrentUnit((short) 1);
        assertFalse(t.isFinished());
    }


    private void testSerialize() throws Exception {
        Training t = new Training();
        byte[] serialized = t.serialize();
        assertEquals(22, serialized.length);
        
        t.setUnits(TWO_UNITS);
        byte[] serialized2 = t.serialize();
        assertEquals(62, serialized2.length);
    }

    private void testSerializeDeserialize() throws Exception {
        Training t = new Training();
        t.setUnits(TWO_UNITS);
        byte[] serialized = t.serialize();

        DataInputStream di = new DataInputStream(new ByteArrayInputStream(serialized));
        Training deserialized = (Training) tr.deserialize(di);
        assertEquals(t.getUnits()[0].getMachineId(), deserialized.getUnits()[0].getMachineId());
        assertEquals(t.isFinished(), deserialized.isFinished());
    }


    private void testFailingDeserialize() throws Exception {
        byte[] serialized = new Training().serialize();
        serialized[2] = ++serialized[2]; // corrupt record identifier!
        DataInputStream di = new DataInputStream(new ByteArrayInputStream(serialized));

        try {
            Training deserialized = (Training) tr.deserialize(di);
            fail("Deserialization worked unexpectedly");
        } catch (IOException iox) {
            // success!
        }
    }

}
