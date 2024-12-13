package mapper;

import entity.Favorite;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteRowMapper implements RowMapper<Favorite> {
    private static FavoriteRowMapper INSTANCE;

    private FavoriteRowMapper() {}

    public static synchronized FavoriteRowMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavoriteRowMapper();
        }
        return INSTANCE;
    }

    @Override
    public Favorite mapRow(ResultSet resultSet) throws SQLException {
        return new Favorite(resultSet.getLong("accountId"), resultSet.getLong("publicationId"));
    }
}
