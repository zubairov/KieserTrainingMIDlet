package de.jki.phone.kieser.persistence.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author jkindler
 */
public abstract class BasicPersistent {
    protected String persistentType;
    protected int id;
    protected boolean isDeleted;
    protected Date created;
    protected Date updated;

    public BasicPersistent() {
        super();
        id = 0;
        isDeleted = false;
        created = new Date();
        updated = new Date(0);
    }

    public BasicPersistent(int id) {
        super();
        this.id = id;
    }

    public String getType() {
        return this.persistentType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }

    private void setCreated(Date created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id != id) {
            updated = new Date();
        }
        this.id = id;
    }

    public Date getUpdated() {
        return new Date(updated.getTime());
    }

    private void setUpdated(Date updated) {
        this.updated = updated;
    }

    private boolean isSame(String o, String n) {
        if (o != null) {
            return o.equals(n);
        } else if (n != null) {
            return n.equals(o);
        }
        return true;
    }

    public boolean isDeserializable(DataInputStream di) {
        boolean isDeserializable = false;
        try {
            String recType = di.readUTF();

            if (this.persistentType.equals(recType)) {
                isDeserializable = true;
            }
        } catch(IOException iox) {
            // ignore - return false!
        }

        return isDeserializable;
    }

    public abstract BasicPersistent deserialize(DataInputStream is) throws IOException;

    public abstract byte[] serialize() throws IOException;

}
