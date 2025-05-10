package mapper;

import entity.Publication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialPublicationRowMapper extends PublicationRowMapper{
    public Object mapRow(ResultSet resultSet, long currentAccountId) throws SQLException {
        System.out.println("Liked current user");
        return new Publication(resultSet.getLong(1),
                resultSet.getDate(2).toLocalDate(),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getLong(6),
                imageDao.findByPublicationId(resultSet.getLong(1)),
                favoriteDao.findByAccountIdPublicationId(currentAccountId, resultSet.getLong(1))
        );
    }
}
