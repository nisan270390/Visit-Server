package Handler;

import Handler.RequestHandler.RequestType;

public class HandlerFactory {

    private static UnknownHandler unkownHandler;

    private static SetFingerprintHandler setFingerprintHandler;
    
    private static SetLocationHandler setLocationHandler;

    private static GetMapListHandler getMapListHandler;

    private static GetLocationHandler getLocationHandler;

    private static GetLocationListHandler getLocationListHandler;
    
    private static GetPathHandler getGetPathHandler;
    
    private static GetBuildingMaps getGetBuildingMaps;
    
    private static GetBuildingHandler getGetBuildingHandler;
    
    private static GetFingerprintListHandler getGetFingerprintListHandler;
    
    private static DeleteAllFingerprintsHandler deleteDeleteAllFingerprint;
    
    private static DeleteFingerprintHandler deleteDeleteFingerprint;

    /**
     * Finds the correct {@link IHandler} for the request type. if no
     * {@link IHandler} can be found, the {@link UnknownHandler} is returned
     *
     * @param type Request type
     * @return appropriate handler
     */
    public static IHandler findHandler(RequestType type) {

        switch (type) {
            case setFingerprint:
                return getSetFingerprintHandler();
            case setLocation:
                return getSetLocationHandler();
            case getMapList:
                return getGetMapHandler();
            case getLocation:
                return getGetLocationHandler();
            case getLocationList:
                return getGetLocationListHandler();
            case getPath:
                return getGetPathHandler();
            case getBuildingMaps:
                return getGetBuildingMaps();
            case getBuildings:
                return getGetBuildingHandler();
            case getFingerprintList:
                return getGetFingerprintListHandler();
            case deleteAllFingerprint:
                return deleteDeleteAllFingerprint();
            case deleteFingerprint:
                return deleteDeleteFingerprint();
            default:
                return getUnknownHandler();
        }
    }
    
    private static SetLocationHandler getSetLocationHandler() {
        if (setLocationHandler == null) {
        	setLocationHandler = new SetLocationHandler();
        }
        return setLocationHandler;
    }
    
    private static DeleteFingerprintHandler deleteDeleteFingerprint() {
        if (deleteDeleteFingerprint == null) {
        	deleteDeleteFingerprint = new DeleteFingerprintHandler();
        }
        return deleteDeleteFingerprint;
    }
    
    private static DeleteAllFingerprintsHandler deleteDeleteAllFingerprint() {
        if (deleteDeleteAllFingerprint == null) {
        	deleteDeleteAllFingerprint = new DeleteAllFingerprintsHandler();
        }
        return deleteDeleteAllFingerprint;
    }
    
    private static GetFingerprintListHandler getGetFingerprintListHandler() {
        if (getGetFingerprintListHandler == null) {
        	getGetFingerprintListHandler = new GetFingerprintListHandler();
        }
        return getGetFingerprintListHandler;
    }

    private static GetBuildingHandler getGetBuildingHandler() {
        if (getGetBuildingHandler == null) {
        	getGetBuildingHandler = new GetBuildingHandler();
        }
        return getGetBuildingHandler;
    }
    
    private static GetBuildingMaps getGetBuildingMaps() {
        if (getGetBuildingMaps == null) {
        	getGetBuildingMaps = new GetBuildingMaps();
        }
        return getGetBuildingMaps;
    }
    
    private static UnknownHandler getUnknownHandler() {
        if (unkownHandler == null) {
            unkownHandler = new UnknownHandler();
        }
        return unkownHandler;
    }
    
    private static GetPathHandler getGetPathHandler() {
        if (getGetPathHandler == null) {
        	getGetPathHandler = new GetPathHandler();
        }
        return getGetPathHandler;
    }


    private static SetFingerprintHandler getSetFingerprintHandler() {
        if (setFingerprintHandler == null) {
            setFingerprintHandler = new SetFingerprintHandler();
        }

        return setFingerprintHandler;
    }

    public static GetMapListHandler getGetMapHandler() {
        if (getMapListHandler == null) {
            getMapListHandler = new GetMapListHandler();
        }

        return getMapListHandler;
    }

    public static GetLocationHandler getGetLocationHandler() {
        if (getLocationHandler == null) {
            getLocationHandler = new GetLocationHandler();
        }

        return getLocationHandler;
    }

    public static GetLocationListHandler getGetLocationListHandler() {
        if (getLocationListHandler == null) {
            getLocationListHandler = new GetLocationListHandler();
        }

        return getLocationListHandler;
    }

    private HandlerFactory() {
    }

}
