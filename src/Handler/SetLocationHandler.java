package Handler;

import json.GsonFactory;

import DataModel.Location;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.HomeFactory;
import db.LocationHome;

public class SetLocationHandler implements IHandler {
	
	LocationHome locationHome;
	
	public SetLocationHandler() {
		locationHome = HomeFactory.getLocationHome();
	}

	@Override
	public Response handle(JsonElement data) {
		Response res;
		
		Location loc = GsonFactory.getGsonInstance().fromJson(data, Location.class);
		
		loc = locationHome.add(loc);
		
		if (loc == null) {
            res = new Response(Status.failed, "could not add to database", null);
        } else {
            res = new Response(Status.ok, null, null);
        }
		
		return res;
	}

}
