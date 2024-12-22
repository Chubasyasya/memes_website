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
    //language=sql
    private static final String SAVE_SQL = """
            insert into comment(content, date, likes_amount, dislikes_amount, changed, publication_id, account_id, creator_name)
            values(?, ?, ?, ?, ?, ?, ?, ?)
            """;
    //language=sql
    private static final String FIND_BY_PUBLICATION_ID_SQL = """
            select *
            from comment
            where publication_id = ?;
            """;
    //language=sql
    private static final String DELETE_SQL = """
            delete from comment
            where id = ?;
        """;
    //language=sql
    private static final String UPDATE_SQL = """
            update comment
            set content = ?, date = ?, changed = ?;
            """;
    //language=sql
    private  static final String FIND_SQL = """
        select *
        from comment
        where id = ?;
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
            statement.setString(1, comment.getContent());
            statement.setDate(2, Date.valueOf(comment.getDate()));
            statement.setInt(3, comment.getLikesAmount());
            statement.setInt(4, comment.getDislikesAmount());
            statement.setBoolean(5, comment.isChanged());
            statement.setLong(6, comment.getPublicationId());
            statement.setLong(7, comment.getAccountId());
            statement.setString(8, comment.getCreatorName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Comment find(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_SQL)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return (Comment) mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Comment comment) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, comment.getContent());
            statement.setDate(2, Date.valueOf(comment.getDate()));
            statement.setBoolean(3, true);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
