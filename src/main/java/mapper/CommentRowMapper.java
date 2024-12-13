package mapper;

import dao.PublicationDao;
import entity.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper{
    private static CommentRowMapper INSTANCE;

    private CommentRowMapper() {}

    public static synchronized CommentRowMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommentRowMapper();
        }
        return INSTANCE;
    }
    @Override
    public Object mapRow(ResultSet resultSet) throws SQLException {
        return new Comment(resultSet.getLong("id"),
                resultSet.getString("content"),
                resultSet.getInt("likes_amount"),
                resultSet.getInt("dislikes_amount"),
                resultSet.getLong("publication_id"),
                resultSet.getLong("account_id"));
    }
}
