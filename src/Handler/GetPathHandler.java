package Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import DataModel.Building.Building;
import DataModel.Building.Cell;
import Handler.Response.Status;
import PathBuilder.MultyFloorPathPlanner;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import com.google.gson.reflect.TypeToken;

import db.HomeFactory;

public class GetPathHandler implements IHandler {
	
	public static final String CELL_DATA = "cells";
    public static final String BUILDING_ID = "BuildingId";
	
	public GetPathHandler() {
	}
	
    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
    	Response res;

        JsonElement cellData = data.getAsJsonObject().get(CELL_DATA);
        JsonElement buildingId = data.getAsJsonObject().get(BUILDING_ID);
    	
    	Type listType = new TypeToken<ArrayList<Cell>>() {}.getType();
        List<Cell> cells = new Gson().fromJson(cellData, listType);

    	Building m = HomeFactory.getBuildingHome().getById(buildingId.getAsInt());
		
		MultyFloorPathPlanner mp = new MultyFloorPathPlanner(m);
		Vector<Cell> path = mp.FindPath(cells.get(0), cells.get(1));
		
		if (path.contains(null)) {
            res = new Response(Status.failed, "could not fetch path", null);
        } else {
            res = new Response(Status.ok, null, path);
        }

        return res;
    
    }

}
