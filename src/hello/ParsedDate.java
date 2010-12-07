package hello;

import java.util.Date;

/**
 *
 * @author jkindler
 */
public class ParsedDate {
    // Pattern delivered by Date.toString: dow mon dd hh:mm:ss zzz yyyy
    // Day names: "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    private static final String[] monthNames =
        {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private Date date;
    private String dow;
    private int day;
    private int month;
    private int year;
    private String timeZone;
    private int hour;
    private int minute;
    private int second;

    private ParsedDate() {
        super();
    }

    public ParsedDate(Date d) {
        this();
        this.date = d;
        this.parse();
    }

    public String getDayOfWeek() {
        return this.dow;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.month;
    }

    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public int getSecond() {
        return this.second;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public String getDmyHm() {
        StringBuffer res = new StringBuffer();
        res.append(getTwoDigitString(this.day));
        res.append(".");
        res.append(getTwoDigitString(this.month));
        res.append(".");
        res.append(this.year);
        res.append(" ");
        res.append(getTwoDigitString(this.hour));
        res.append(":");
        res.append(getTwoDigitString(this.minute));
        return res.toString();
    }

    private static final String getTwoDigitString(int val) {
        return (val < 10) ? "0" + val : "" + val;
    }

    private void parse() {
        String dateStr = this.date.toString();
        int pos = 0;
        int space = dateStr.indexOf(" ", pos);
        this.dow = dateStr.substring(pos, space);

        pos = ++space;
        space = dateStr.indexOf(" ", pos);
        this.month = monthNameToInt(dateStr.substring(pos, space));

        pos = ++space;
        space = dateStr.indexOf(" ", pos);
        this.day = stringToInt(dateStr.substring(pos, space));

        pos = ++space;
        space = dateStr.indexOf(" ", pos);
        String time = dateStr.substring(pos, space);
        parseTime(time);

        pos = ++space;
        space = dateStr.indexOf(" ", pos);
        this.timeZone = dateStr.substring(pos, space);

        pos = ++space;
        space = dateStr.indexOf(" ", pos);
        this.year = stringToInt(dateStr.substring(pos));
    }

    private static final int stringToInt(String s) {
        int result = 0;
        try {
            result = Integer.parseInt(s);

        } catch(NumberFormatException nfe) {
//            System.out.println(s + " could not be parsed.");
//            nfe.printStackTrace();
        }
        return result;
    }

    private static final int monthNameToInt(String name) {
        int result = -1;
        for (int i = 0; i < monthNames.length && result < 0; i++) {
            result = (monthNames[i].equals(name)) ? ++i : -1;
        }

        return result;
    }

    private void parseTime(String time) {
        int pos = 0;
        int colon = time.indexOf(":", pos);
        this.hour = stringToInt(time.substring(pos, colon));

        pos = ++colon;
        colon = time.indexOf(":", pos);
        this.minute = stringToInt(time.substring(pos, colon));

        pos = ++colon;
        colon = time.indexOf(":", pos);
        this.second = stringToInt(time.substring(pos));
    }
}
