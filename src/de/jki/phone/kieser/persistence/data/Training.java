package de.jki.phone.kieser.persistence.data;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

public class Training extends BasicPersistent {
    private Date date;
    private TrainingUnit[] units;
    private int currentUnit;

    public Training() {
        super();
        this.persistentType = "TR";
        date = new Date(0);
        units = new TrainingUnit[0];
        currentUnit = 0;
    }

    public Date getDate() {
        return date;
    }

    public void doStart() {
        if (this.date.getTime() == 0) {
            this.date = new Date();
        }
    }

    private void setDate(Date date) {
        this.date = date;
    }

    public TrainingUnit[] getUnits() {
        return units;
    }

    public void setUnits(TrainingUnit[] units) {
        this.units = units;
    }

    public int getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(int currentUnit) {
        this.currentUnit = currentUnit;
    }

    public boolean isFinished() {
        boolean finished = true;
        for (int i = 0; i < this.units.length && finished; i++) {
            if ((this.units[i] != null) && (!this.units[i].isFinished())) {
                finished = false;
            }
        }
        return finished;
    }

    public void doFinish() {
        for (int i = 0; i < this.units.length; i++) {
            if ((this.units[i] != null) && (this.units[i].getSecondsDone() < 0)) {
                this.units[i].setSecondsDone((short) 0);
            }
        }
    }

    public BasicPersistent deserialize(DataInputStream is) throws IOException {
        Training val;

        try {
            if (!isDeserializable(is)) {
                throw new IOException("Illegal record type");
            }

            val = new Training();
            val.setId(is.readInt());
            val.setDate(new Date(is.readLong()));

            short aLen = is.readShort();
            TrainingUnit[] units = new TrainingUnit[aLen];

            for (int i = 0; i < aLen; i++) {
                units[i] = TrainingUnit.deserialize(is);
            }
            val.setUnits(units);
            val.setCurrentUnit(is.readShort());

        } finally {
            is.close();
        }
        return val;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(baos);

        try {
            os.writeUTF(this.persistentType);
            os.writeInt(this.id);
            os.writeLong(this.getDate().getTime());

            TrainingUnit[] units = getUnits();
            short aLen = getNonNullCount(units);
            os.writeShort(aLen);

            for (short i = 0; i < aLen; i++) {
                if (units[i] != null) {
                    os.write(units[i].serialize());
                }
            }

            os.writeInt(getCurrentUnit());

        } finally {
            os.close();
        }
        return baos.toByteArray();
    }

    private static short getNonNullCount(Object[] o) {
        short res = 0;

        if (o != null) {
            for (int i = 0; i < o.length; i++) {
                res++;
            }
        }
        return res;
    }
}
