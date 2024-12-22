package mapper;

import entity.Folder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FolderRowMapper implements RowMapper<Folder>{
    private static FolderRowMapper INSTANCE;

    private FolderRowMapper() {}

    public static synchronized FolderRowMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FolderRowMapper();
        }
        return INSTANCE;
    }

    @Override
    public Folder mapRow(ResultSet resultSet) throws SQLException {
        return new Folder(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("image_amount"),
                resultSet.getDate("date_created").toLocalDate(),
                resultSet.getLong("account_id"));
    }
}
