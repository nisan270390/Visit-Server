package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import DataModel.measure.Wifi;

public class WiFiReadingHome extends EntityHome<Wifi> {

    private static final String[] TableCols = {"bssid", "ssid", "rssi", "wepEnabled", "isInfrastructure"};
    private static final String TableName = "wifireading";
    private static final String TableIdCol = "wifiReadingId";

    public WiFiReadingHome() {
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

    /**
     * @see EntityHome#parseResultRow(ResultSet)
     */
    @Override
    public Wifi parseResultRow(final ResultSet rs, int fromIndex) throws SQLException {
    	Wifi reading = new Wifi();

        try {
            reading.setId(rs.getInt(fromIndex));
            reading.setBssid(rs.getString(fromIndex + 1));
            reading.setSsid(rs.getString(fromIndex + 2));
            reading.setRssi(rs.getInt(fromIndex + 3));
            reading.setWepEnabled(rs.getBoolean(fromIndex + 4));
            reading.setInfrastructure(rs.getBoolean(fromIndex + 5));

        } catch (SQLException e) {
            throw e;
        }

        return reading;
    }

    /**
     * @see EntityHome#fillInStatement(PreparedStatement,
     * org.redpin.server.standalone.db.IEntity, int)
     */
    @Override
    public int fillInStatement(PreparedStatement ps, Wifi t, int fromIndex)
            throws SQLException {
        return fillInStatement(ps, new Object[]{t.getBssid(), t.getSsid(), t.getRssi(), t.isWepEnabled(), t.isInfrastructure()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.TINYINT, Types.TINYINT}, fromIndex);
    }

}
