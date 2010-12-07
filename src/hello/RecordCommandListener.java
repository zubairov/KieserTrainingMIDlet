package hello;

import java.io.IOException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.ItemCommandListener;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author jkindler
 */
public class RecordCommandListener implements CommandListener, ItemCommandListener {

    private HelloMIDlet application;
    private RecordPersistenceManager rpm;
    private Display display;
    private Record record;

    private Command exitCommand;
    private Command newCommand;
    private Command listCommand;
    private Command selectCommand;
    private Command storeCommand;
    private Command deleteCommand;

    private RecordInputForm input;
    private RecordSelectionList select;

    public RecordCommandListener(HelloMIDlet application, RecordPersistenceManager rpm) {
        this.application = application;
        this.display = Display.getDisplay(application);
        this.rpm = rpm;

        this.exitCommand = new Command("Exit", Command.EXIT, 0);
        this.newCommand = new Command("New", Command.ITEM, 0);
        this.listCommand = new Command("List", Command.ITEM, 0);
        this.selectCommand = new Command("Select", Command.OK, 0);
        this.storeCommand = new Command("Store", Command.OK, 0);
        this.deleteCommand = new Command("Delete", Command.ITEM, 0);
    }


    public void register(RecordInputForm recInput) {
        this.input = recInput;
        this.input.addCommand(this.newCommand);
        this.input.addCommand(this.storeCommand, this);
        this.input.addCommand(this.listCommand, this);
        this.input.addCommand(this.exitCommand);
        this.input.setCommandListener(this);
    }


    public void register(RecordSelectionList recSelect) {
        this.select = recSelect;
        this.select.addCommand(this.newCommand);
        this.select.addCommand(this.selectCommand);
        this.select.addCommand(this.deleteCommand);
        this.select.addCommand(this.exitCommand);
        this.select.setCommandListener(this);
    }


    public void commandAction(Command c, Displayable d) {
        try {
            if (c == this.listCommand) {
                doList();

            } else if (c == this.newCommand) {
                doNew();

            } else if (c == this.selectCommand) {
                doSelect();

            } else if (c == this.storeCommand) {
                doStore();

            } else if (c == this.deleteCommand) {
                doDelete();

            } else if (c == this.exitCommand) {
                doExit();
            }

        } catch (Exception ex) {
            handleError(ex, d);
        }
    }

    public void commandAction(Command c, Item item) {
        try {
            if (c == this.listCommand) {
                doList();

            } else if (c == this.storeCommand) {
                doStore();
            }

        } catch (Exception ex) {
            handleError(ex, this.input);
        }
    }

    private void doExit() {
        this.application.destroyApp(true);
        this.application.notifyDestroyed();
    }

    private void doNew() {
        this.record = new Record();
        this.input.setData(this.record);
        this.display.setCurrent(this.input);
    }

    private void doSelect() throws RecordStoreException, IOException {
        this.record = this.rpm.read(this.select.getSelectedRecordId());
        this.input.setData(this.record);
        this.display.setCurrent(this.input);
    }

    private void doDelete() throws RecordStoreException, IOException {
        this.rpm.delete(this.select.getSelectedRecordId());
        doList();
    }

    private void doStore() throws RecordStoreException, IOException {
        this.record = this.input.getData();
        this.rpm.save(this.record);
        doList();
    }

    private void doList() throws RecordStoreNotOpenException, IOException
            , RecordStoreException {
        this.select.reload(this.rpm);
        this.display.setCurrent(this.select);
    }

    private void handleError(Exception ex, Displayable nextScreen) {
        Alert a = new Alert("Error occurred: "
                , ex.getClass().getName() + ": " + ex.getMessage(), null, AlertType.ERROR);
        this.display.setCurrent(a, nextScreen);
    }
}
