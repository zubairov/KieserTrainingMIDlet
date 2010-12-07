package de.jki.phone.kieser.test;

import de.jki.phone.kieser.persistence.data.TrainingUnit;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestTrainingUnit extends TestCase {
    private static final String MID = "A1";
    private static final TrainingUnit T = new TrainingUnit(MID);


    public TestTrainingUnit() {
        super(4, "Test TrainingUnit");
    }

    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0: testDefaultInstance(); break;
            case 1: testSerialize(); break;
            case 2: testSerializeDeserialize(); break;
            case 3: testFailingDeserialize(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }


    private void testDefaultInstance() throws Exception {
        TrainingUnit tu = new TrainingUnit(MID);
        assertEquals(MID, tu.getMachineId());
        assertEquals(0, tu.getPounds());
        assertEquals(0, tu.getRom());
        assertEquals(90, tu.getSecondsPlanned());
        assertEquals((short) -1, tu.getSecondsDone());
        assertFalse(tu.isFinished());
    }


    private void testSerialize() throws Exception {
        TrainingUnit tu = new TrainingUnit(MID);
        byte[] serialized = tu.serialize();
        assertEquals(20, serialized.length);
    }


    private void testSerializeDeserialize() throws Exception {
        TrainingUnit tu = new TrainingUnit(MID);
        tu.setSecondsDone(tu.getSecondsPlanned());
        byte[] serialized = tu.serialize();

        DataInputStream di = new DataInputStream(new ByteArrayInputStream(serialized));
        TrainingUnit deserialized = (TrainingUnit) T.deserialize(di);
        assertEquals(tu.getMachineId(), deserialized.getMachineId());
        assertEquals(tu.getPounds(), deserialized.getPounds());
        assertEquals(tu.getRom(), deserialized.getRom());
        assertEquals(tu.getSecondsPlanned(), deserialized.getSecondsPlanned());
        assertEquals(tu.getSecondsDone(), deserialized.getSecondsDone());
        assertTrue(deserialized.isFinished());
    }


    private void testFailingDeserialize() throws Exception {
        byte[] serialized = new TrainingUnit(MID).serialize();
        serialized[2] = ++serialized[2]; // corrupt record identifier!
        DataInputStream di = new DataInputStream(new ByteArrayInputStream(serialized));

        try {
            TrainingUnit deserialized = T.deserialize(di);
            fail();
        } catch (IOException iox) {
            // success!
        }
    }

}
