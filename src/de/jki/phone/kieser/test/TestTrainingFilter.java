package de.jki.phone.kieser.test;

import de.jki.phone.kieser.persistence.TrainingFilter;
import de.jki.phone.kieser.persistence.data.Training;
import de.jki.phone.kieser.persistence.data.TrainingUnit;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestTrainingFilter extends TestCase {
    private TrainingFilter tf;

    public TestTrainingFilter() {
        super(2, "Test Training filter");
    }

    public void setUp() throws Exception {
        this.tf = new TrainingFilter();
    }

    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0: testIsDeserializable(); break;
            case 1: testIsNotDeserializable(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }

    private void testIsDeserializable() throws Exception {
        byte[] trainingBytes = new Training().serialize();
        assertTrue(this.tf.matches(trainingBytes));
    }

    private void testIsNotDeserializable() throws Exception {
        byte[] trainingBytes = new TrainingUnit("A1").serialize();
        assertFalse(this.tf.matches(trainingBytes));
    }

}
