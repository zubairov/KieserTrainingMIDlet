package de.jki.phone.kieser.persistence.data;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NamedValue {
    private final static String REC_TYPE = "NV";
    private String name;
    private String value;

    private NamedValue() {
        super();
        name = "";
        value = "";
    }

    public NamedValue(String name) {
        this();
        this.name = name;
    }

    public NamedValue(String name, String value) {
        this(name);
        this.value = value;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static NamedValue deserialize(DataInputStream is) throws IOException {
        NamedValue val;

        try {
            String recType = is.readUTF();

            if (!REC_TYPE.equals(recType)) {
                throw new IOException("Illegal record type '" + recType + "' instead of '" + REC_TYPE + "'");
            }

            val = new NamedValue();
            val.setName(is.readUTF());
            val.setValue(is.readUTF());
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
            os.writeUTF(this.name);
            os.writeUTF(this.value);

        } finally {
            os.close();
        }
        return baos.toByteArray();
    }
}
