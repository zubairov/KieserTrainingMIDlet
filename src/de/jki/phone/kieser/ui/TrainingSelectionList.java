package de.jki.phone.kieser.ui;

import de.jki.phone.kieser.persistence.PersistenceManager;
import de.jki.phone.kieser.persistence.TrainingComparator;
import de.jki.phone.kieser.persistence.TrainingFilter;
import de.jki.phone.kieser.persistence.data.Training;
import de.jki.phone.util.ParsedDate;
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
public class TrainingSelectionList 
        extends List
        implements TraininSelectionProvider {
    private PersistenceManager pm;
    private Vector ids;
    private TrainingComparator tc;
    private TrainingFilter tf;
    private Training t;
    private ErrorHandler errHandler;

    public TrainingSelectionList(String title, PersistenceManager pm, int listType, ErrorHandler errHandler) {
        super(title, listType);
        this.pm = pm;
        this.tc = new TrainingComparator();
        this.tf = new TrainingFilter();
        this.ids = new Vector();
        this.t = new Training();
        this.errHandler = errHandler;
    }



    public void reload() throws RecordStoreNotOpenException
                                , IOException, RecordStoreException {
        clear();
        for (RecordEnumeration rEnum = this.pm.query(this.tf, this.tc); rEnum.hasNextElement();) {
            Training rec = (Training) t.deserialize(this.pm.createStream(rEnum.nextRecord()));
            this.append(new ParsedDate(rec.getDate()).getDmyHm(), null);
            this.ids.addElement(new Integer(rec.getId()));
        }
    }


    private void clear() {
        this.ids.removeAllElements();
        this.deleteAll();
    }

    
    public Training getTraining() {
        int recId = ((Integer) this.ids.elementAt(this.getSelectedIndex())).intValue();
        Training res = null;
        try {
            res = (Training) this.pm.read(recId, t);
        } catch (Exception ex) {
            this.errHandler.handleError(ex, this);
        }
        
        return res;
    }
}
