package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataModel.Building.Cell;
import DataModel.Building.IntrestPoint;

public class IntrestPointHome extends EntityHome<IntrestPoint> {
	
	public static final String[] TableCols = {"isUp", "startX", "startY", "startZ", "endX", "endY", "endZ"};
    public static final String TableName = "intrestPoints";
    private static final String TableIdCol = "intrestPointId";
    
    public IntrestPointHome() {
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
    
    public List<IntrestPoint> getByBuildingId(Integer id) {
        if (id == null || id == -1) {
            return null;
        }
        String constraint = " buildingId = " + id;
        List<IntrestPoint> list = get(constraint);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

	@Override
	public IntrestPoint parseResultRow(ResultSet rs, int fromIndex)
			throws SQLException {
		IntrestPoint point = null;
		
		try {
			Cell src = new Cell(rs.getInt(fromIndex + 2), rs.getInt(fromIndex + 3), 
					rs.getInt(fromIndex + 4));
			Cell dest = new Cell(rs.getInt(fromIndex + 5), rs.getInt(fromIndex + 6),
					rs.getInt(fromIndex + 7));
			
			point = new IntrestPoint(src, dest, rs.getInt(fromIndex + 1) != 0);

        } catch (SQLException e) {
            throw e;
        }

		return point;
	}

	@Override
	protected int fillInStatement(PreparedStatement ps, IntrestPoint e,
			int fromIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
