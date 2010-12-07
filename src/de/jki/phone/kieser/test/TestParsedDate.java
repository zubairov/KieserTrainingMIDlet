package de.jki.phone.kieser.test;

import de.jki.phone.util.ParsedDate;
import java.util.Date;
import jmunit.framework.cldc11.TestCase;

/**
 *
 * @author jkindler
 */
public class TestParsedDate extends TestCase {
    // Wed Nov 17 18:25:52 GMT+01:00 2010
    private static final long A_DAY_IN_MY_LIFE = 1290014752649L;
    private static final Date A_DAY = new Date(A_DAY_IN_MY_LIFE);
    private ParsedDate pd;

    public TestParsedDate() {
        super(8, "Test ParsedDate");
    }

    public void setUp() throws Exception {
        this.pd = new ParsedDate(A_DAY);
    }

    public void test(int testNumber) throws Throwable {
        switch (testNumber) {
            case 0: testParseDay(); break;
            case 1: testParseMonth(); break;
            case 2: testParseYear(); break;
            case 3: testParseDayOfWeek(); break;
            case 4: testParseHour(); break;
            case 5: testParseMinute(); break;
            case 6: testParseSecond(); break;
            case 7: testParseDmyHm(); break;
            default:
                throw new IllegalArgumentException("Test " +  testNumber + " does not exist");
        }
    }

    private void testParseDay() {
        assertEquals(17, pd.getDay());
    }

    private void testParseMonth() {
        assertEquals(11, pd.getMonth());
    }

    private void testParseYear() {
        assertEquals(2010, pd.getYear());
    }

    private void testParseDayOfWeek() {
        assertEquals("Wed", pd.getDayOfWeek());
    }

    private void testParseHour() {
        assertEquals(18, pd.getHour());
    }

    private void testParseMinute() {
        assertEquals(25, pd.getMinute());
    }

    private void testParseSecond() {
        assertEquals(52, pd.getSecond());
    }

    private void testParseDmyHm() {
        assertEquals("17.11.2010 18:25", pd.getDmyHm());
    }

}
