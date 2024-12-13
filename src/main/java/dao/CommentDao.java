package dao;

import entity.Comment;
import mapper.AccountRowMapper;
import mapper.CommentRowMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao extends Dao<Comment> {
    private volatile static CommentDao INSTANCE;
    private static final String SAVE_SQL = """
            insert into comment(content, likes_amount, dislikes_amount, publication_id, account_id)
            values(?, ?, ?, ?, ?)
            """;
    private static final String FIND_BY_PUBLICATION_ID_SQL = """
            select *
            from comment
            where account_id = ?;
            """;

    private CommentDao() {

        mapper = CommentRowMapper.getInstance();
    }

    public static CommentDao getInstance(){
        if(INSTANCE == null){
            synchronized (CommentDao.class){
                if(INSTANCE == null){
                    INSTANCE = new CommentDao();
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public void save(Comment comment) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setString(1, comment.content());
            statement.setInt(2, comment.likesAmount());
            statement.setInt(3, comment.dislikesAmount());
            statement.setLong(4, comment.publicationId());
            statement.setLong(5, comment.accountId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comment find(long e) {
        return null;
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void delete(long e) {
    }

    public List<Comment> findByPublicationId(long publicationId){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PUBLICATION_ID_SQL)) {
            statement.setLong(1, publicationId);

            ResultSet resultSet = statement.executeQuery();
            List<Comment> comments = new ArrayList<>();
            while(resultSet.next()){
                comments.add((Comment) mapper.mapRow(resultSet));
            }

            return comments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
