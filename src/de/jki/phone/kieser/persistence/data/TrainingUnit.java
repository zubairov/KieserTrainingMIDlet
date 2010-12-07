package de.jki.phone.kieser.persistence.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TrainingUnit {

    private final static String REC_TYPE = "TU";
    private String machineId;
    private short rom;
    private short pounds;
    private short secondsPlanned;
    private short secondsDone;
    private String[] alertTypes;
    private NamedValue[] settings;

    private TrainingUnit() {
        super();
        rom = 0;
        pounds = 0;
        reset();
        alertTypes = new String[0];
        settings = new NamedValue[0];
    }

    public TrainingUnit(String machineId) {
        this();
        this.machineId = machineId;
    }

    public void reset() {
        secondsPlanned = 90;
        secondsDone = -1;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public short getRom() {
        return rom;
    }

    public void setRom(short rom) {
        this.rom = rom;
    }

    public short getPounds() {
        return pounds;
    }

    public void setPounds(short pounds) {
        this.pounds = pounds;
    }

    public short getSecondsPlanned() {
        return secondsPlanned;
    }

    public void setSecondsPlanned(short seconds) {
        this.secondsPlanned = seconds;
    }

    public short getSecondsDone() {
        return secondsDone;
    }

    public void setSecondsDone(short seconds) {
        this.secondsDone = seconds;
    }

    public boolean isFinished() {
        return (this.secondsDone >= 0);
    }

    public String[] getAlertTypes() {
        return alertTypes;
    }

    public void setAlertTypes(String alertTypes[]) {
        this.alertTypes = alertTypes;
    }

    public NamedValue[] getSettings() {
        return settings;
    }

    public void setSettings(NamedValue[] settings) {
        this.settings = settings;
    }

    public static TrainingUnit deserialize(byte[] bytes) throws IOException {
        return deserialize(new DataInputStream(new ByteArrayInputStream(bytes)));
    }

    public static TrainingUnit deserialize(DataInputStream is) throws IOException {
        TrainingUnit val;

        try {
            String recType = is.readUTF();

            if (!REC_TYPE.equals(recType)) {
                throw new IOException("Illegal record type '" + recType + "' instead of '" + REC_TYPE + "'");
            }

            val = new TrainingUnit();
            val.setMachineId(is.readUTF());
            val.setRom(is.readShort());
            val.setPounds(is.readShort());
            val.setSecondsPlanned(is.readShort());
            val.setSecondsDone(is.readShort());

            short aLen = is.readShort();
            NamedValue[] settings = new NamedValue[aLen];

            for (int i = 0; i < aLen; i++) {
                settings[i] = NamedValue.deserialize(is);
            }
            val.setSettings(settings);

            aLen = is.readShort();
            String[] alertTypes = new String[aLen];

            for (int i = 0; i < aLen; i++) {
                alertTypes[i] = is.readUTF();
            }
            val.setAlertTypes(alertTypes);

        } finally {
            is.close();
        }
        return val;
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream os = new DataOutputStream(baos);

        try {
            os.writeUTF(REC_TYPE);
            os.writeUTF(this.getMachineId());
            os.writeShort(this.getRom());
            os.writeShort(this.getPounds());
            os.writeShort(this.getSecondsPlanned());
            os.writeShort(this.getSecondsDone());

            NamedValue[] settings = getSettings();
            short aLen = getNonNullCount(settings);
            os.writeShort(aLen);
            for (short i = 0; i < aLen; i++) {
                if (settings[i] != null) {
                    os.write(settings[i].serialize());
                }
            }

            String[] alertTypes = getAlertTypes();
            aLen = getNonNullCount(alertTypes);
            os.writeShort(aLen);
            for (short i = 0; i < aLen; i++) {
                if (alertTypes[i] != null) {
                    os.writeUTF(alertTypes[i]);
                }
            }

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
