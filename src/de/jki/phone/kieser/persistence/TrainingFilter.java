package de.jki.phone.kieser.persistence;

import de.jki.phone.kieser.persistence.data.Training;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import javax.microedition.rms.RecordFilter;

/**
 *
 * @author jkindler
 */
public class TrainingFilter implements RecordFilter {
    private Training type;

    public TrainingFilter() {
        super();
        this.type = new Training();
    }

    public boolean matches(byte[] candidate) {
        return this.type.isDeserializable(getStream(candidate));
    }


    private final DataInputStream getStream(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }
}
