package mapper;

import entity.Publication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicationRowMapper implements RowMapper<Publication> {
    @Override
    public Publication mapRow(ResultSet resultSet) throws SQLException {
        return new Publication(resultSet.getLong(1),
                resultSet.getDate(2).toLocalDate(),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getLong(6),
                null);
    }
}
