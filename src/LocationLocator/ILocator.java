package LocationLocator;

import DataModel.Location;
import DataModel.Measurement;

public interface ILocator {

	/**
     * Tries to find a location which fingerprint measurement matches the  m
     *  Location or null if no location could be found
     */
    public Location locate(Measurement m);

    /**
     * Returns a similarity level between to measurement. 
     */
    public int measurementSimilarityLevel(Measurement t, Measurement o);

    /**
     * Decides whether to measurements are similar. 
     */
    public Boolean measurmentAreSimilar(Measurement t, Measurement o);
}
