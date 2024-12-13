package dao;

import entity.Publication;
import mapper.PublicationRowMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationDao extends Dao<Publication> {
    private volatile static PublicationDao INSTANCE;
    private final String SAVE_SQL = """
            insert into publication (date, content, comments_amount, likes_amount, account_id)
            values(?, ?, ?, ?, ?);
            """;

    private final String FIND_BY_ACCOUNT_ID = """
        select *
        from publication
        where account_id = ?
        order by date;
        """;
    private final String FIND_ALL_SQL = """
            select *
            from publication
            order by date
            offset ?
            limit ?;
            """;
    private final String UPDATE_LIKES_SQL = """
            update publication
            set likes_amount = likes_amount+1
            where id = ?;
            """;


    private PublicationDao() {
        mapper = new PublicationRowMapper();
    }

    public static PublicationDao getInstance(){
        if(INSTANCE == null){
            synchronized (PublicationDao.class){
                if(INSTANCE == null){
                    INSTANCE = new PublicationDao();
                }
            }
        }
        return INSTANCE;
    }
    public long saveAndGetId(Publication publication) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(publication.date()));
            statement.setString(2, publication.content());
            statement.setInt(3, publication.commentsAmount());
            statement.setInt(4, publication.likesAmount());
            statement.setLong(5, publication.accountId());

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }else {
                    throw new RuntimeException("Can't take generated ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Publication publication) {

    }

    @Override
    public Publication find(long e) {
        return null;
    }

    @Override
    public void update(Publication publication) {
    }
    public void updateLikes(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_LIKES_SQL)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void delete(long e) {
    }

    public List<Publication> findByAccountId(long accountId){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ACCOUNT_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, accountId);

            List<Publication> publications = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                publications.add((Publication) mapper.mapRow(resultSet));
            }
            return publications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Publication> find(int offset, int limit) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            statement.setInt(1, offset);
            statement.setInt(2, limit);

            List<Publication> publications = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                publications.add((Publication) mapper.mapRow(resultSet));
            }

            return publications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
