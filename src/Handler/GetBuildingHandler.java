package Handler;

import java.util.ArrayList;
import java.util.List;

import DataModel.Building.Building;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.HomeFactory;

public class GetBuildingHandler implements IHandler {

	@Override
	public Response handle(JsonElement data) {
		
		Response res;
		
		List<Building> buildings = HomeFactory.getBuildingHome().getAll();
		List<BuildingTemp> temp = new ArrayList<BuildingTemp>();
		
		for (Building curr : buildings) {
			BuildingTemp currTemp = new BuildingTemp();
			currTemp.setId(curr.getId());
			currTemp.setBuildingName(curr.getBuildingName());
			
			temp.add(currTemp);
		}
		
		if (temp.contains(null)) {
            res = new Response(Status.failed, "could not fetch all buildings", null);
        } else {
            res = new Response(Status.ok, null, temp);
        }

        return res;
	}
	
	public class BuildingTemp {
		private Integer id;
		String buildingName;
		
		public String getBuildingName() {
	        return buildingName;
	    }

		public void setBuildingName(String name) {
	        this.buildingName = name;
	    }
		
		public Integer getId() {
	        return id;
	    }

		public void setId(Integer id) {
	        this.id = id;
	    }
	}

}
