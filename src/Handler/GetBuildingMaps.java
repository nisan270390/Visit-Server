package Handler;

import java.util.List;

import DataModel.Map;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.HomeFactory;
import db.MapHome;

public class GetBuildingMaps implements IHandler {
	public static final String BUILDING_ID = "BuildingId";
	MapHome mapHome;
	
	public GetBuildingMaps() {
		mapHome = HomeFactory.getMapHome();
	}
	
    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
    	Response res;
    	
    	JsonElement buildingId = data.getAsJsonObject().get(BUILDING_ID);

        List<Map> maps = mapHome.getByBuildingId(buildingId.getAsInt());

        if (maps.contains(null)) {
            res = new Response(Status.failed, "could not fetch building maps", null);
        } else {
            res = new Response(Status.ok, null, maps);
        }

        return res;
    }

}
