package db.Vector;

import db.EntityHome;
import db.HomeFactory;
import DataModel.measure.Bluetooth;

public class BluetoothReadingVectorHome extends VectorHome<Bluetooth> {

    private static final String className = Bluetooth.class.getSimpleName();

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
    public EntityHome<Bluetooth> getObjectHome() {
        return HomeFactory.getBluetoothReadingHome();
    }

}