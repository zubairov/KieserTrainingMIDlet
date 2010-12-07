package de.jki.phone.kieser.test;

import de.jki.phone.kieser.persistence.TrainingComparator;
import de.jki.phone.kieser.persistence.data.Training;
import de.jki.phone.kieser.persistence.data.TrainingUnit;
import javax.microedition.rms.RecordComparator;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestTrainingComparator extends TestCase {
    private TrainingComparator tc;
    private final static String A1 = "A1";
    private final static String B1 = "B1";
    private final static int FOLLOWS = RecordComparator.FOLLOWS;
    private final static int EQUIVALENT = RecordComparator.EQUIVALENT;
    private final static int PRECEDES = RecordComparator.PRECEDES;


    public TestTrainingComparator() {
        super(4, "Test TrainingComparator");
    }

    public void setUp() throws Exception {
        tc = new TrainingComparator();
    }

    private TrainingUnit getUnfinished(String id) {
        TrainingUnit u = new TrainingUnit(id);
        return u;
    }

    private TrainingUnit getFinished(String id) {
        TrainingUnit u = getUnfinished(id);
        u.setSecondsDone(u.getSecondsPlanned());
        return u;
    }

    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0: test2FinishedSortByDate(); break;
            case 1: test2UnstartedSortByDate(); break;
            case 2: test2UnfinishedSortByReversedDate(); break;
            case 3: testFinishedFollowsUnfinished(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }

    private void test2FinishedSortByDate() throws Exception {
        Training t1 = new Training();
        t1.setUnits(new TrainingUnit[] {getFinished(A1), getUnfinished(B1)});
        t1.doStart();
        t1.doFinish();

        Thread.sleep(10);
        Training t2 = new Training();
        t2.setUnits(new TrainingUnit[] {getFinished(A1), getUnfinished(B1)});
        t2.doStart();
        t2.doFinish();

        assertEquals(EQUIVALENT, tc.compare(t1.serialize(), t1.serialize()));
        assertEquals(PRECEDES, tc.compare(t1.serialize(), t2.serialize()));
        assertEquals(FOLLOWS, tc.compare(t2.serialize(), t1.serialize()));
    }

    private void test2UnstartedSortByDate() throws Exception {
        Training t1 = new Training();
        t1.setUnits(new TrainingUnit[] {getUnfinished(A1)});

        Training t2 = new Training();
        t2.setUnits(new TrainingUnit[] {getUnfinished(A1)});
        assertEquals(EQUIVALENT, tc.compare(t1.serialize(), t2.serialize()));
    }

    private void test2UnfinishedSortByReversedDate() throws Exception {
        Training t1 = new Training();
        t1.setUnits(new TrainingUnit[] {getUnfinished(A1)});
        t1.doStart();

        Thread.sleep(10);
        Training t2 = new Training();
        t2.setUnits(new TrainingUnit[] {getUnfinished(A1)});
        t2.doStart();
        assertEquals(FOLLOWS, tc.compare(t1.serialize(), t2.serialize()));
    }

    private void testFinishedFollowsUnfinished() throws Exception {
        Training t1 = new Training();
        t1.setUnits(new TrainingUnit[] {getFinished(A1)});
        t1.doStart();
        t1.doFinish();

        Training t2 = new Training();
        t2.setUnits(new TrainingUnit[] {getUnfinished(A1)});
        t2.doStart();
        assertEquals(FOLLOWS, tc.compare(t1.serialize(), t2.serialize()));
    }

}
