package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import DataModel.Map;
import DataModel.Building.Building;
import DataModel.Building.IntrestPoint;

public class BuildingHome extends EntityHome<Building> {
	
	public static final String[] TableCols = {"buildingName"};
    public static final String TableName = "building";
    private static final String TableIdCol = "buildingId";
    
    private static final String selectBuildings = " SELECT " + HomeFactory.getBuildingHome().getTableColNames() + ", " +
    		HomeFactory.getMapHome().getTableColNames() + ", map.mapURLAstar" +
    		" FROM building LEFT OUTER JOIN map ON map.buildingId = building.buildingId ";
    private static final String orderMeasurements = " building.buildingId, map.mapId ";
    
    public BuildingHome() {
        super();
    }

    /**
     * @see EntityHome#getTableName()
     */
    @Override
    protected String getTableName() {
        return TableName;
    }

    /**
     * @see EntityHome#getTableIdCol()
     */
    @Override
    protected String getTableIdCol() {
        return TableIdCol;
    }

    /**
     * @see EntityHome#getTableCols()
     */
    @Override
    protected String[] getTableCols() {
        return TableCols;
    }
    
    @Override
    protected String getSelectSQL() {
        return selectBuildings;
    }
    
    @Override
    protected String getOrder() {
        return orderMeasurements;
    }
    
    @Override
    public Building getById(Integer id) {
        String constraint = getTableName() + "." + getTableIdCol() + " = " + id;
        List<Building> list = get(constraint);
        if (list.size() == 0) {
            return null;
        }
        
        List<IntrestPoint> points = HomeFactory.getIntrestPointHome().getByBuildingId(id);
        Building b = list.get(0);
        b.setIntrestPoint(points);
        
        return b;

    }


	@Override
	public Building parseResultRow(ResultSet rs, int fromIndex)
			throws SQLException {
		Building building = new Building();
		Vector<Map> maps = new Vector<>();
		List<String> AstarMaps = new ArrayList<String>();
		
		try {
            if (!rs.isAfterLast()) {
            	int mId = rs.getInt(fromIndex);
            	building.setBuildingName(rs.getString(fromIndex + 1));
            	String mapName = rs.getString(fromIndex + 3);
            	
            	if (mapName == null) {
                    // there are no maps in building
                    rs.next();
                } else {
                	
                	while (!rs.isAfterLast() && mId == rs.getInt(fromIndex)) {
                		maps.add(HomeFactory.getMapHome().getById(rs.getInt(fromIndex + 2)));
                		AstarMaps.add(rs.getString(fromIndex + 6));
                		rs.next();
                	}
                }
            	
            	building.initFloorGrid(maps.size());
            	int i = 0;
            	for (Map currMap : maps)
            	{
            		building.addFloorMap(AstarMaps.get(i), currMap.getMapFloorNumber());
            		i++;
            	}
            }
		} catch (SQLException e) {
            throw e;
        }
		
		
		return building;
	}

	@Override
	protected int fillInStatement(PreparedStatement ps, Building e,
			int fromIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
