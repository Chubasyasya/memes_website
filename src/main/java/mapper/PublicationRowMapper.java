package mapper;

import dao.FavoriteDao;
import dao.ImageDao;
import entity.Publication;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PublicationRowMapper implements RowMapper<Publication> {
    private ImageDao imageDao = ImageDao.getInstance();
    private FavoriteDao favoriteDao = FavoriteDao.getInstance();
    @Override
    public Publication mapRow(ResultSet resultSet) throws SQLException {
        long accountId = resultSet.getLong(6);
        return new Publication(resultSet.getLong(1),
                resultSet.getDate(2).toLocalDate(),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getInt(5),
                accountId,
                imageDao.findByPublicationId(resultSet.getLong(1)),
                favoriteDao.findByAccountIdPublicationId(accountId, resultSet.getLong(1)));
    }
}
