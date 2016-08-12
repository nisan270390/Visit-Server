package LocationLocator;

import java.util.Comparator;

import DataModel.Measurement;

public class MeasurementComparator implements Comparator<Measurement> {

    Measurement basisMeasurement;

    public MeasurementComparator(Measurement m) {
        basisMeasurement = m;
    }

    public Measurement getBasisMeasurement() {
        return basisMeasurement;
    }

    public void setBasisMeasurement(Measurement basisMeasurement) {
        this.basisMeasurement = basisMeasurement;
    }

    /**
     * Compares two measurement to the basisMeasurement and returns which one is
     * more similar to the basisMeasurement
     */
    @Override
    public int compare(Measurement arg0, Measurement arg1) {
        int a1 = basisMeasurement.similarityLevel(arg0);
        int a2 = basisMeasurement.similarityLevel(arg1);

        if (a1 == a2) {
            long t1 = arg0.getTimestamp();
            long t2 = arg1.getTimestamp();
            if (t1 == t2) {
                return 0;
            } else {
                if (t1 < t2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        } else {
            if (a1 < a2) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
