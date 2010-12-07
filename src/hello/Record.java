package hello;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author jkindler
 */
public class Record {

    private final static String TYPE = "REC";
    private int id;
    private Date appointment;
    private boolean isDeleted;
    private short length;
    private String info;
    private boolean isValid;
    private Date created;
    private Date updated;

    public Record() {
        super();
        id = 0;
        appointment = new Date();
        length = 0;
        info = "";
        isDeleted = false;
        isValid = false;
        created = new Date();
        updated = new Date(0);
    }

    public Record(int id) {
        super();
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getAppointment() {
        return appointment;
    }

    public void setAppointment(Date appointment) {
        this.appointment = appointment;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        if (!isSame(this.info, info)) {
            updated = new Date();
        }

        this.info = info;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        if (isValid != this.isValid) {
            updated = new Date();
        }

        this.isValid = isValid;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        if (length != this.length) {
            updated = new Date();
        }
        this.length = length;
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


    public static Record deserialize(byte[] data) throws IOException {
        DataInputStream di = new DataInputStream(new ByteArrayInputStream(data));
        String type = di.readUTF();

        if (!TYPE.equals(type)) {
            throw new IOException("Invalid type '" + type + "' expected '" + TYPE + "'");
        }

        Record rec = new Record();
        try {
            rec.setId(di.readInt());
            rec.setAppointment(new Date(di.readLong()));
            rec.setDeleted(di.readBoolean());
            rec.setLength(di.readShort());
            rec.setInfo(di.readUTF());
            rec.setValid(di.readBoolean());
            rec.setCreated(new Date(di.readLong()));
            rec.setUpdated(new Date(di.readLong()));
        } finally {
            di.close();
        }
        return rec;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            dos.writeUTF(TYPE);
            dos.writeInt(id);
            dos.writeLong(appointment.getTime());
            dos.writeBoolean(isDeleted);
            dos.writeShort(length);
            dos.writeUTF(info);
            dos.writeBoolean(isValid);
            dos.writeLong(created.getTime());
            dos.writeLong(updated.getTime());
        } finally {
            dos.close();
            bos.close();
        }
        return bos.toByteArray();
    }

    public String toString() {
        return id + " " + info + " " + length;
    }
}
