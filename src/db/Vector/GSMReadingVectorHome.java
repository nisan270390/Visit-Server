package db.Vector;

import DataModel.measure.GSM;
import db.EntityHome;
import db.HomeFactory;

public class GSMReadingVectorHome extends VectorHome<GSM> {

    private static final String className = GSM.class.getSimpleName();

    @Override
    public String getContainedObjectClassName() {
        return className;
    }

    /**
     * @see VectorHome#getObjectHome()
     */
    @Override
    public EntityHome<GSM> getObjectHome() {
        return HomeFactory.getGSMReadingHome();
    }

}
