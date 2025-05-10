package mapper;

import dao.FavoriteDao;
import dao.ImageDao;
import entity.Publication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicationRowMapper implements RowMapper<Publication> {
    protected ImageDao imageDao = ImageDao.getInstance();
    protected FavoriteDao favoriteDao = FavoriteDao.getInstance();
    @Override
    public Publication mapRow(ResultSet resultSet) throws SQLException {
        System.out.println("Liked current user");
        return new Publication(resultSet.getLong(1),
                resultSet.getDate(2).toLocalDate(),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                resultSet.getLong(6),
                imageDao.findByPublicationId(resultSet.getLong(1)), false
                );
    }
}
