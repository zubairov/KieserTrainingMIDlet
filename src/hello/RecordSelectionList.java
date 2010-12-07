package hello;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.List;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author jkindler
 */
public class RecordSelectionList extends List {
    private Vector ids;

    public RecordSelectionList(String title, int listType) {
        super(title, listType);
        ids = new Vector();
    }


    public void reload(RecordPersistenceManager rpm)
            throws RecordStoreNotOpenException
                , IOException, RecordStoreException {
        clear();
        int i = 0;
        for (RecordEnumeration rEnum = rpm.getRecords(); rEnum.hasNextElement(); i++) {
            Record r = Record.deserialize(rEnum.nextRecord());
            ParsedDate d = new ParsedDate(r.getAppointment());
            this.append(d.getDmyHm() + " " + r.getInfo(), null);
            this.ids.addElement(new Integer(r.getId()));
        }
    }

    private void clear() {
        this.ids.removeAllElements();
        this.deleteAll();
    }
    
    public int getSelectedRecordId() {
        Integer recId = (Integer) this.ids.elementAt(this.getSelectedIndex());
        return recId.intValue();
    }
}
