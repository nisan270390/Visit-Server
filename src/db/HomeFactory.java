package db;

import db.Vector.BluetoothReadingVectorHome;
import db.Vector.GSMReadingVectorHome;
import db.Vector.WiFiReadingVectorHome;

public class HomeFactory {
	
	private static MapHome mapHome = null;
	
	private static IntrestPointHome intresPointHome = null;

    private static LocationHome locHome = null;

    private static FingerprintHome fpHome = null;

    private static MeasurementHome mHome = null;
    
    private static BuildingHome buildingHome = null;

    private static ReadingInMeasurementHome rinmHome = null;

    private static WiFiReadingHome wrHome = null;

    private static WiFiReadingVectorHome wrvHome = null;

    private static GSMReadingHome grHome = null;

    private static GSMReadingVectorHome grvHome = null;

    private static BluetoothReadingHome brHome = null;

    private static BluetoothReadingVectorHome brvHome = null;

    public synchronized static MapHome getMapHome() {
        if (mapHome == null) {
            mapHome = new MapHome();
        }
        return mapHome;
    }
    
    public synchronized static IntrestPointHome getIntrestPointHome() {
        if (intresPointHome == null) {
        	intresPointHome = new IntrestPointHome();
        }
        return intresPointHome;
    }

    public static synchronized LocationHome getLocationHome() {
        if (locHome == null) {
            locHome = new LocationHome();
        }
        return locHome;
    }

    public static synchronized FingerprintHome getFingerprintHome() {
        if (fpHome == null) {
            fpHome = new FingerprintHome();
        }
        return fpHome;
    }

    public static synchronized MeasurementHome getMeasurementHome() {
        if (mHome == null) {
            mHome = new MeasurementHome();
        }
        return mHome;
    }
    
    public static synchronized BuildingHome getBuildingHome() {
        if (buildingHome == null) {
        	buildingHome = new BuildingHome();
        }
        return buildingHome;
    }

    public static synchronized ReadingInMeasurementHome getReadingInMeasurementHome() {
        if (rinmHome == null) {
            rinmHome = new ReadingInMeasurementHome();
        }
        return rinmHome;
    }

    public static synchronized WiFiReadingHome getWiFiReadingHome() {
        if (wrHome == null) {
            wrHome = new WiFiReadingHome();
        }
        return wrHome;
    }

    public static synchronized WiFiReadingVectorHome getWiFiReadingVectorHome() {
        if (wrvHome == null) {
            wrvHome = new WiFiReadingVectorHome();
        }
        return wrvHome;
    }

    public static synchronized GSMReadingHome getGSMReadingHome() {
        if (grHome == null) {
            grHome = new GSMReadingHome();
        }
        return grHome;
    }

    public static synchronized GSMReadingVectorHome getGSMReadingVectorHome() {
        if (grvHome == null) {
            grvHome = new GSMReadingVectorHome();
        }
        return grvHome;
    }

    public static synchronized BluetoothReadingHome getBluetoothReadingHome() {
        if (brHome == null) {
            brHome = new BluetoothReadingHome();
        }
        return brHome;
    }

    public static synchronized BluetoothReadingVectorHome getBluetoothReadingVectorHome() {
        if (brvHome == null) {
            brvHome = new BluetoothReadingVectorHome();
        }
        return brvHome;
    }

    private HomeFactory() {
    }

}
