package db.Vector;

import db.EntityHome;
import db.HomeFactory;
import DataModel.measure.Wifi;

public class WiFiReadingVectorHome extends VectorHome<Wifi> {

    private static final String className = Wifi.class.getSimpleName();

    /**
     * @see VectorHome#getContainedObjectClassName()
     */
    @Override
    public String getContainedObjectClassName() {
        return className;
    }

    /**
     * @see VectorHome#getObjectHome()
     */
    @Override
    public EntityHome<Wifi> getObjectHome() {
        return HomeFactory.getWiFiReadingHome();
    }

}
