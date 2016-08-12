package Handler;

import json.GsonFactory;
import DataModel.Location;
import DataModel.Measurement;
import Handler.Response.Status;
import LocationLocator.LocatorHome;

import com.google.gson.JsonElement;

/**
 * @see IHandler
 *
 */
public class GetLocationHandler implements IHandler {

    public GetLocationHandler() {
    }

    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
        Response res;
        Location loc;

        Measurement currentMeasurement = GsonFactory.getGsonInstance().fromJson(data, Measurement.class);

        loc = LocatorHome.getLocator().locate(currentMeasurement);

        if (loc == null) {
            res = new Response(Status.failed, "no matching location found", null);

        } else {
            res = new Response(Status.ok, null, loc);
        }

        return res;
    }

}