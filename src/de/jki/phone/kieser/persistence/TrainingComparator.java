package de.jki.phone.kieser.persistence;

import de.jki.phone.kieser.persistence.data.Training;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.rms.RecordComparator;

/**
 *
 * @author jkindler
 */
public class TrainingComparator implements RecordComparator {
    private Training type;

    public TrainingComparator() {
        super();
        this.type = new Training();
    }

    private static DataInputStream getStream(byte[] data) {
        return new DataInputStream(new ByteArrayInputStream(data));
    }

    /**
     * Calculate score for sorting.
     * Trainings that have been finished sort according to their time value.
     * Unfinished sort reversed. So the newest unfinished training will be
     * the first shown.
     */
    private long getScore(Training t) {
        long score = t.isFinished()
                ? RecordComparator.FOLLOWS
                : RecordComparator.PRECEDES;
        score = score * t.getDate().getTime();
        return score;
    }


    public int compare(byte[] rec1, byte[] rec2) {
        int result = RecordComparator.EQUIVALENT;

        try {
            long score1 = getScore((Training) type.deserialize(getStream(rec1)));
            long score2 = getScore((Training) type.deserialize(getStream(rec2)));

            if (score1 > score2) {
                result = RecordComparator.FOLLOWS;

            } else if (score1 < score2) {
                result = RecordComparator.PRECEDES;
            }

        } catch(IOException iox) {
            // ignore
        }

        return result;
    }
}
