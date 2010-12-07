package de.jki.phone.kieser;

import de.jki.phone.kieser.data.MachineRegistry;
import de.jki.phone.kieser.persistence.PersistenceManager;
import de.jki.phone.kieser.ui.ApplicationExitCommand;
import de.jki.phone.kieser.ui.ErrorHandler;
import de.jki.phone.kieser.ui.MachineSelectionCommand;
import de.jki.phone.kieser.ui.MachineSelectionList;
import de.jki.phone.kieser.ui.TrainingSelectionCommand;
import de.jki.phone.kieser.ui.TrainingSelectionList;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStoreException;

/**
 * @author jkindler
 */

/*
 * Dialogs:
 * - Create new Training (plan)
 *  -> a list view showing selected machines in order
 *      -> Create unit (select machine and add time, weight, etc.)
 *      -> Edit unit (change an already created unit)
 *      -> Delete unit (remove a unit from the plan)
 *      -> Move unit (move the position of a unit)
 *
 */
public class KieserTraining extends MIDlet implements ErrorHandler {
    private Display display;     // This midlets' display
    private PersistenceManager pm;
    private ApplicationExitCommand cmdExit;
    private MachineRegistry machineReg;
    private MachineSelectionList machineSelectionList;
    private MachineSelectionCommand cmdSelectMachine;
    private TrainingSelectionList trainingSelectionList;
    private TrainingSelectionCommand cmdSelectTraining;
    private Resources res;
    private String locale = "de-DE";

    public KieserTraining() {
        this.res = new Resources();
        this.pm = new PersistenceManager("KieserTraining");
        this.cmdExit = new ApplicationExitCommand(locale, Command.EXIT, 0, this);
        this.machineReg = new MachineRegistry();
        this.machineSelectionList = new MachineSelectionList(
                getResource(Resources.SELECTION)
                , machineReg
                , List.IMPLICIT);

        this.cmdSelectMachine = new MachineSelectionCommand(
                getResource(Resources.ACTION_SELECT)
                , Command.ITEM, 0, machineSelectionList, null);

        this.trainingSelectionList = new TrainingSelectionList(getResource(Resources.SELECTION)
                , this.pm, List.IMPLICIT, this);

        this.cmdSelectTraining = new TrainingSelectionCommand(
                getResource(Resources.SELECTION)
                , Command.ITEM, 0, trainingSelectionList, null);
    }


    public void startApp() {
        this.display = Display.getDisplay(this);

        try {
            this.pm.open();
            this.display.setCurrent(trainingSelectionList);

        } catch (RecordStoreException ex) {
            handleError(ex, trainingSelectionList);
        }
    }


    public void pauseApp() {
    }


    public void destroyApp(boolean unconditional) {
        try {
            this.pm.close();
            
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
    }


    public void handleError(Exception ex, Displayable nextScreen) {
        Alert a = new Alert("Error occurred: "
                , ex.getClass().getName() + ": " + ex.getMessage(), null, AlertType.ERROR);
        this.display.setCurrent(a, nextScreen);
    }


    private String getResource(String key) {
        return (String) this.res.get(locale, key);
    }
}
