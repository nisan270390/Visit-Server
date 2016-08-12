package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import DataModel.measure.Bluetooth;

public class BluetoothReadingHome extends EntityHome<Bluetooth> {

    private static final String[] TableCols = {"friendlyName", "bluetoothAddress", "majorDeviceClass", "minorDeviceClass"};
    private static final String TableName = "bluetoothreading";
    private static final String TableIdCol = "bluetoothReadingId";

    public BluetoothReadingHome() {
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
    public Bluetooth parseResultRow(final ResultSet rs, int fromIndex) throws SQLException {
    	Bluetooth reading = new Bluetooth();

        try {
            reading.setId(rs.getInt(fromIndex));
            reading.setFriendlyName(rs.getString(fromIndex + 1));
            reading.setBluetoothAddress(rs.getString(fromIndex + 2));
            reading.setMajorDeviceClass(rs.getString(fromIndex + 3));
            reading.setMinorDeviceClass(rs.getString(fromIndex + 4));

        } catch (SQLException e) {
            throw e;
        }

        return reading;
    }

    @Override
    public int fillInStatement(PreparedStatement ps, Bluetooth t, int fromIndex)
            throws SQLException {
        return fillInStatement(ps, new Object[]{t.getFriendlyName(), t.getBluetoothAddress(), t.getMajorDeviceClass(), t.getMinorDeviceClass()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR},
                fromIndex);

    }

}
