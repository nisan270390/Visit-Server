package Handler;

import java.util.List;

import DataModel.Location;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.HomeFactory;
import db.LocationHome;

/**
 * @see IHandler
 *
 */
public class GetLocationListHandler implements IHandler {

    LocationHome locHome;

    public GetLocationListHandler() {
        locHome = HomeFactory.getLocationHome();
    }

    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {

        Response res;

        List<Location> locations = locHome.getAllLocations();

        if (locations.contains(null)) {
            res = new Response(Status.failed, "could not fetch all locations", null);
        } else {
            res = new Response(Status.ok, null, locations);
        }

        return res;

    }

}