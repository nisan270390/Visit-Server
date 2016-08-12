package Handler;

import java.util.List;

import DataModel.Map;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.HomeFactory;
import db.MapHome;

/**
 * @see IHandler
 *
 */
public class GetMapListHandler implements IHandler {

    MapHome mapHome;

    public GetMapListHandler() {
        mapHome = HomeFactory.getMapHome();
    }

    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {

        Response res;

        List<Map> maps = mapHome.getAll();

        if (maps.contains(null)) {
            res = new Response(Status.failed, "could not fetch all maps", null);
        } else {
            res = new Response(Status.ok, null, maps);
        }

        return res;
    }

}
