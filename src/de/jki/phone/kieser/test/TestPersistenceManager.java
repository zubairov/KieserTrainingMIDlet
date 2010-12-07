package de.jki.phone.kieser.test;

import de.jki.phone.kieser.persistence.PersistenceManager;
import de.jki.phone.kieser.persistence.TrainingComparator;
import de.jki.phone.kieser.persistence.TrainingFilter;
import de.jki.phone.kieser.persistence.data.Training;
import de.jki.phone.kieser.persistence.data.TrainingUnit;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestPersistenceManager extends TestCase {
    private PersistenceManager pm;

    public TestPersistenceManager() {
        super(8, "Test PersistenceManager");
    }

    public void setUp() throws Exception {
        this.pm = new PersistenceManager("TestStore");
    }


    public void tearDown() {
        try {
            this.pm.delete();
            
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }

    
    public void test(int testNumber) throws Throwable {
        switch(testNumber) {
            case 0: testQueryRequiresOpen(); break;
            case 1: testDeleteRecordRequiresOpen(); break;
            case 2: testSaveRecordRequiresOpen(); break;
            case 3: testReadRecordRequiresOpen(); break;
            case 4: testOpenCloseDeleteStore(); break;
            case 5: testSaveRecords(); break;
            case 6: testDeleteRecords(); break;
            case 7: testQueryRecords(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }

    
    private void testQueryRequiresOpen() throws Exception {
        try {
            this.pm.query(null, null);
            fail("Query should not work on not opened store");
        } catch(IllegalStateException ise) {
            // OK
        }
    }


    private void testDeleteRecordRequiresOpen() throws Exception {
        try {
            this.pm.delete(1);
            fail("Delete should not work on not opened store");
        } catch(IllegalStateException ise) {
            // OK
        }
    }


    private void testSaveRecordRequiresOpen() throws Exception {
        try {
            this.pm.save(null);
            fail("Save should not work on not opened store");
        } catch(IllegalStateException ise) {
            // OK
        }
    }


    private void testReadRecordRequiresOpen() throws Exception {
        try {
            this.pm.read(1, null);
            fail("Read should not work on not opened store");
        } catch(IllegalStateException ise) {
            // OK
        }
    }


    private void testOpenCloseDeleteStore() throws Exception {
        this.pm.open();
        this.pm.close();
        this.pm.open();
        this.pm.delete();
    }


    private void testSaveRecords() throws Exception {
        this.pm.open();
        Training t1 = new Training();
        this.pm.save(t1);
        assertNotEquals(0, t1.getId());
        t1.doStart();
        t1.setUnits(new TrainingUnit[] {new TrainingUnit("A1")});
        this.pm.save(t1);
        Training t2 = (Training) this.pm.read(t1.getId(), t1);
        assertEquals("ID is unchanged", t1.getId(), t2.getId());
        assertEquals("Unit has been added", 1, t2.getUnits().length);
    }


    private void testDeleteRecords() throws Exception {
        this.pm.open();
        Training t1 = new Training();
        t1.doStart();
        this.pm.save(t1);
        Training t2 = (Training) this.pm.read(t1.getId(), t1);
        this.pm.delete(t2.getId());

        try {
            this.pm.read(t1.getId(), t1);
            fail("Read with deleted ID should fail");
        } catch(RecordStoreException rse) {
            // OK
        }
    }


    private void testQueryRecords() throws Exception {
        this.pm.open();
        Training t1 = new Training();
        Training t2 = new Training();
        t1.doStart();
        this.pm.save(t1);
        this.pm.save(t2);
        RecordEnumeration recs =
                this.pm.query(new TrainingFilter(), new TrainingComparator());

        assertEquals(2, recs.numRecords());
    }
}
