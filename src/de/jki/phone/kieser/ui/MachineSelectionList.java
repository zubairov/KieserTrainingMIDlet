package de.jki.phone.kieser.ui;

import de.jki.phone.kieser.data.Machine;
import java.util.Vector;
import javax.microedition.lcdui.List;
import de.jki.phone.kieser.data.MachineRegistry;

/**
 *
 * @author jkindler
 */
public class MachineSelectionList 
        extends List
        implements MachineSelectionProvider {
    private MachineRegistry mRegistry;
    private Vector ids;

    public MachineSelectionList(String title, MachineRegistry mReg, int listType) {
        super(title, listType);
        this.mRegistry = mReg;
        ids = new Vector();
        load();
    }


    private void load() {
        String[] idList = mRegistry.getAvailable();

        for (int i = 0; i < idList.length; i++) {
            Machine m = mRegistry.getMachine(idList[i]);
            this.append(m.getId() + " " + m.getDescription(), null);
            this.ids.addElement(idList[i]);
        }
    }


    public Machine getMachine() {
        String mId = (String) this.ids.elementAt(this.getSelectedIndex());
        return mRegistry.getMachine(mId);
    }
}
