package db.Vector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import DataModel.ReadingInMeasurement;

import db.EntityHome;
import db.HomeFactory;
import db.IEntity;

abstract public class VectorHome<E extends IEntity<Integer>> {

    /**
     * Gets the contained object class name. this is needed for backwards
     * compability
     *
     * @return The contained object class name
     */
    abstract public String getContainedObjectClassName();

    /**
     * Gets the contained object entity home
     *
     * @return Contained object entity home
     */
    abstract public EntityHome<E> getObjectHome();

    /**
     * @see EntityHome#parseResultRow(ResultSet)
     */
    public Vector<E> parseResultRow(ResultSet rs, int fromIndex) throws SQLException {
        Vector<E> v = new Vector<>();

        try {
            int measurementId = rs.getInt("measurementId");
            v.add(getObjectHome().parseResultRow(rs, fromIndex));
            String readingClassName = rs.getString("readingClassName");
            while (rs.next() && measurementId == rs.getInt("measurementId") && readingClassName.equals(rs.getString("readingClassName"))) {
                v.add(getObjectHome().parseResultRow(rs, fromIndex));
            }
        } catch (SQLException e) {
            throw e;
        }

        return v;
    }

    /**
     * execute an insert for all readings in the given vector
     *
     * @param vps {@link PreparedStatement} {@link Vector}
     * @param v entity {@link Vector}
     * @param foreignKeyId foreign key
     * @throws SQLException
     */
    public void executeUpdate(Vector<PreparedStatement> vps, Vector<E> v, int foreignKeyId) throws SQLException {
        for (E reading : v) {
            int readingId = getObjectHome().executeInsertUpdate(vps, reading);
            HomeFactory.getReadingInMeasurementHome().executeInsertUpdate(vps, new ReadingInMeasurement(foreignKeyId, readingId, getContainedObjectClassName()));
        }
    }

}