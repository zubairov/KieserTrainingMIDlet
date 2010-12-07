package hello;

import java.io.IOException;
import java.util.Date;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 *
 * @author jkindler
 */
public class RecordPersistenceManager {
    private String storeName;
    private RecordStore rs;
    private RecordFilter deletedFilter;
    private RecordComparator sorter;

    private RecordPersistenceManager() {
        super();
    }

    public RecordPersistenceManager(String storeName) {
        this();
        this.storeName = storeName;
        this.deletedFilter = new RecordFilter() {
            public boolean matches(byte[] candidate) {
                Record candRec;
                try {
                    candRec = Record.deserialize(candidate);
                    return !candRec.isDeleted();
                } catch (IOException ex) {
                    return false;
                }
            }
        };

        this.sorter = new RecordComparator() {
            public int compare(byte[] rec1, byte[] rec2) {
                int result = RecordComparator.EQUIVALENT;
                Date d1 = null;
                Date d2 = null;

                try {
                    d1 = Record.deserialize(rec1).getAppointment();
                    d2 = Record.deserialize(rec2).getAppointment();

                    if ((d1 != null) && (d2 != null)) {
                        if (d1.getTime() > d2.getTime()) {
                            result = RecordComparator.FOLLOWS;
                        } else if (d1.getTime() < d2.getTime()) {
                            result = RecordComparator.PRECEDES;
                        }
                    }

                } catch(IOException iox) {
                    // ignore
                }

                return result;
            }

        };
    }

    private void assertOpen() {
        if (this.rs == null) {
            throw new IllegalStateException("Record store '" + this.storeName + "' not open!");
        }
    }

    public void open(boolean createIfNecessary) throws RecordStoreException {
        this.rs = RecordStore.openRecordStore(this.storeName, createIfNecessary);
    }

    public void close() throws RecordStoreException {
        this.rs.closeRecordStore();
    }

    public void delete() throws RecordStoreException {
        try {
            this.close();
        } catch (RecordStoreException ex) {
            // don't care
        }

        RecordStore.deleteRecordStore(storeName);
        this.rs = null;
    }

    public Record save(Record rec) throws RecordStoreException, IOException {
        assertOpen();

        if (rec.getId() == 0) {
            rec.setId(this.rs.getNextRecordID());
            byte[] data = rec.serialize();
            this.rs.addRecord(data, 0, data.length);
        } else {
            byte[] data = rec.serialize();
            this.rs.setRecord(rec.getId(), data, 0, data.length);
        }
        return rec;
    }

    public Record read(int id) throws RecordStoreException, IOException {
        assertOpen();
        byte[] data = this.rs.getRecord(id);
        Record rec = Record.deserialize(data);
        return rec;
    }

    public void delete(int id) throws RecordStoreException {
        assertOpen();
        this.rs.deleteRecord(id);
    }

    public RecordEnumeration getRecords() throws RecordStoreNotOpenException {
        assertOpen();
        return this.rs.enumerateRecords(this.deletedFilter, this.sorter, true);
    }
}
