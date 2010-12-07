package hello;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStoreException;

public class HelloMIDlet extends MIDlet {
    private Display display;     // The display for this MIDlet
    private RecordPersistenceManager rpm;
    private RecordCommandListener rcl;
    private RecordSelectionList rsl;
    private RecordInputForm rif;

    public HelloMIDlet() {
        this.display = Display.getDisplay(this);
        this.rpm = new RecordPersistenceManager("HelloMIDlet");
        this.rcl = new RecordCommandListener(this, this.rpm);
        this.rsl = new RecordSelectionList("Records", List.IMPLICIT);
        this.rif = new RecordInputForm("Input Record");
        this.rcl.register(this.rif);
        this.rcl.register(this.rsl);
    }

    public void startApp() {
        this.display.setCurrent(this.rsl);

        try {
            rpm.open(true);
        } catch (RecordStoreException ex) {
            handleError(ex, rsl);
        }
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        try {
            rpm.close();
        } catch (RecordStoreException ex) {
            handleError(ex, null);
        }
    }

    private void handleError(Exception ex, Displayable nextScreen) {
        Alert a = new Alert("Error occurred: ", ex.getMessage(), null, AlertType.ERROR);
        display.setCurrent(a, nextScreen);
    }
}
