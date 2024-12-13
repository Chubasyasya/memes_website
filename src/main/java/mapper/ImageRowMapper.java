package mapper;

import entity.Image;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageRowMapper implements RowMapper<Image>{
    private static ImageRowMapper INSTANCE;

    private ImageRowMapper() {}

    public static synchronized ImageRowMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageRowMapper();
        }
        return INSTANCE;
    }
    @Override
    public Image mapRow(ResultSet resultSet) throws SQLException {
        return new Image(resultSet.getLong("id"),
                resultSet.getString("path"),
                resultSet.getString("name"),
                resultSet.getLong("publication_id"));
    }
}
