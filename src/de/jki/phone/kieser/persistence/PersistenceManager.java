package de.jki.phone.kieser.persistence;

import de.jki.phone.kieser.persistence.data.BasicPersistent;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author jkindler
 */
public class PersistenceManager {
    private String storeName;
    private RecordStore rs;

    private PersistenceManager() {
        super();
    }


    public PersistenceManager(String storeName) {
        this();
        this.storeName = storeName;
    }


    private final void assertOpen() {
        if (this.rs == null) {
            throw new IllegalStateException("Record store '" + this.storeName + "' not open!");
        }
    }

    
    public void open(boolean createIfNecessary) throws RecordStoreException {
        this.rs = RecordStore.openRecordStore(this.storeName, createIfNecessary);
    }


    public void open() throws RecordStoreException {
        open(true);
    }


    public void close() throws RecordStoreException {
        if (this.rs != null) {
            this.rs.closeRecordStore();
        }
    }


    public void delete() throws RecordStoreException {
        try {
            this.close();
        } catch (RecordStoreException ex) {
            // don't care
        }

        try {
            RecordStore.deleteRecordStore(storeName);
        } catch (RecordStoreNotFoundException rsnfe) {
            // don't care
        }
        this.rs = null;
    }


    public BasicPersistent save(BasicPersistent rec) throws RecordStoreException, IOException {
        assertOpen();

        if (rec.getId() == 0) {
            synchronized(this.rs) {
                rec.setId(this.rs.getNextRecordID());
                byte[] data = rec.serialize();
                this.rs.addRecord(data, 0, data.length);
            }
        } else {
            byte[] data = rec.serialize();
            this.rs.setRecord(rec.getId(), data, 0, data.length);
        }
        return rec;
    }


    public BasicPersistent read(int id, BasicPersistent rec)
            throws RecordStoreException, IOException {
        assertOpen();
        byte[] data = this.rs.getRecord(id);
        DataInputStream input = createStream(data);
        BasicPersistent res = rec.deserialize(input);
        return res;
    }


    public void delete(int id) throws RecordStoreException {
        assertOpen();
        this.rs.deleteRecord(id);
    }


    public RecordEnumeration query(RecordFilter filter, RecordComparator comp)
            throws RecordStoreNotOpenException {
        assertOpen();
        return this.rs.enumerateRecords(filter, comp, true);
    }

    public DataInputStream createStream(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }
}
