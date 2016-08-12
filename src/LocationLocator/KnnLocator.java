package LocationLocator;

import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import db.FingerprintHome;
import db.HomeFactory;
import db.MeasurementHome;

import DataModel.Fingerprint;
import DataModel.Location;
import DataModel.Measurement;
import DataModel.measure.Wifi;

public class KnnLocator implements ILocator {

	private static double ID_POS_CONTRIBUTION = 1;
    private static double ID_NEG_CONTRIBUTION = -0.4;

    public static double SIGNAL_CONTRIBUTION = 1;
    public static double SIGNAL_PENALTY_THRESHOLD = 10;
    public static double SIGNAL_GRAPH_LEVELING = 0.2;

    /* accuracy level */
    public static final int LOCATION_KNOWN = 100;
    public static final int LOCATION_UNKNOWN = 0;
    public static int LOCATION_THRESHOLD = 2;
    
    MeasurementHome measurementHome = null;
    FingerprintHome fingerprintHome = null;

    public KnnLocator() {
    	measurementHome = HomeFactory.getMeasurementHome();
        fingerprintHome = HomeFactory.getFingerprintHome();
    }

    @Override
    public Location locate(Measurement currentMeasurement) {

        Location loc = null;

        // get all measurement in database
        List<Measurement> list = measurementHome.getAll();
        
        // check for similarity
        TreeSet<Measurement> hits = new TreeSet<>(new MeasurementComparator(currentMeasurement));

        for (Measurement m : list)
        {
        	int level = measurementSimilarityLevel(m, currentMeasurement);
            if (level > LOCATION_THRESHOLD) {
                hits.add(m);
            }
        }

        if (hits.size() > 0) {

            Measurement bestMatch = hits.first();

            Fingerprint f = fingerprintHome.getByMeasurementId(bestMatch.getId());

            if (f != null) {
                loc = (Location) f.getLocation();
                loc.setAccuracy(measurementSimilarityLevel(bestMatch, currentMeasurement));
            }

        }

        return loc;
    }

    @Override
    public int measurementSimilarityLevel(Measurement t, Measurement o) {

        // total amount of credit that can be achieved
        double totalCredit = 0;

        // achieved credit 
        double currCredit = 0;

        // counts the routers of positive matches of reading ID's
        int matches = 0;

        Vector<Wifi> this_vect = t.getWiFiReadings();
        Vector<Wifi> other_vect = o.getWiFiReadings();

        // check WiFiReadings 
        for (int i = 0; i < this_vect.size(); i++) {
        	Wifi this_wifi = this_vect.elementAt(i);
            for (int j = 0; j < other_vect.size(); j++) {
            	Wifi other_wifi = other_vect.elementAt(j);

                // bssid match: add ID contribution and signal strength contribution
                if (this_wifi != null && this_wifi.getBssid() != null 
                		&& other_wifi != null && other_wifi.getBssid() != null
                        && this_wifi.getBssid().equals(other_wifi.getBssid())) {

                	currCredit += ID_POS_CONTRIBUTION;
                	currCredit += signalContribution(this_wifi.getRssi(), other_wifi.getRssi());
                    matches++;
                }
            }
        }

        // penalty if for each router that did not match
        int readings = Math.max(this_vect.size(), other_vect.size());
        currCredit += (readings - matches) * ID_NEG_CONTRIBUTION;

        // get the total credit for this measurement.
        totalCredit += this_vect.size()
                * ID_POS_CONTRIBUTION;

        totalCredit += this_vect.size()
                * SIGNAL_CONTRIBUTION;


        // get accuracy level defined by bounds
        int factor = LOCATION_KNOWN - LOCATION_UNKNOWN;

        // a negative account results immediately in accuracy equals zero
        int accuracy = 0;
        if (currCredit > 0) {

            double a = (currCredit / totalCredit) * factor
                    + LOCATION_UNKNOWN;

            // same as Math.round
            accuracy = (int) Math.floor(a + 0.5d);
        }

        return accuracy;
    }

    @Override
    public Boolean measurmentAreSimilar(Measurement t, Measurement o) {

        return measurementSimilarityLevel(t, o) > LOCATION_THRESHOLD;
    }

    /**
     * computes the credit contributed by the received signal strength of any
     * wireless scan
     */
    private double signalContribution(double rssi1, double rssi2) {

        double base = rssi1;

        double diff = Math.abs(rssi1 - rssi2);

        // get percentage of error
        double x = diff / base;

        if (x > 0.0) {
            /*
             * small error should result in a high contribution, big error in a
             * small -> reciprocate (1/x) MIN = 1, MAX = infinity
             */
            double y = 1 / x;

            /*
             * compute percentage of treshold regarding the current base
             */
            double t = SIGNAL_PENALTY_THRESHOLD / base;

            /*
             * shift down the resulting graph. the root (zero) will then be
             * exactly at x = treshold for every base, e.g. measurement, and
             * signal differences above the treshold will result in a negative
             * contribution
             */
            y -= 1 / t;

            /*
             * graph increases fast, so that a difference of 15 still results in
             * a maximal contribution. With this adjustment, the graph gets flat
             * and this has also an impact on the penalty (difference to big)
             */
            y *= SIGNAL_GRAPH_LEVELING;

            if ((-1 * SIGNAL_CONTRIBUTION <= y)
                    && (y <= SIGNAL_CONTRIBUTION)) {

                return y;
            } else {
                return SIGNAL_CONTRIBUTION;
            }
        } else {
            return SIGNAL_CONTRIBUTION;
        }
    }
}
