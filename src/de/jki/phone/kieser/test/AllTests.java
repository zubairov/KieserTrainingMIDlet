package de.jki.phone.kieser.test;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import jmunit.framework.cldc11.Test;
import jmunit.framework.cldc11.TestSuite;
/**
 *
 * @author jkindler
 */
public class AllTests extends MIDlet {
    public AllTests() {
        super();
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

    protected void pauseApp() {
    }

    protected void startApp() throws MIDletStateChangeException {
        Test.initialize(this);
        KieserTestSuite suite = new KieserTestSuite("Test everything ...");
        suite.add(new TestMachine()) ;
        suite.add(new TestMachineRegistry()) ;
        suite.add(new TestParsedDate()) ;
        suite.add(new TestNamedValue());
        suite.add(new TestTrainingUnit());
        suite.add(new TestTraining());
        suite.add(new TestTrainingFilter());
        suite.add(new TestTrainingComparator());
        suite.add(new TestPersistenceManager());
        suite.test();
    }

    private class KieserTestSuite extends TestSuite {
        KieserTestSuite(String name) {
            super(name);
        }

    }
}
