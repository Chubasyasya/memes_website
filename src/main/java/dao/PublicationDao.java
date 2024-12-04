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
    //language = sql
    private final String FIND_BY_ACCOUNT_ID = """
        select *
        from publication
        where account_id = ?;
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
    @Override
    public Publication save(Publication publication) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(publication.date()));
            statement.setString(2, publication.content());
            statement.setInt(3, publication.commentsAmount());
            statement.setInt(4, publication.likesAmount());
            statement.setLong(5, publication.accountId());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                return new Publication(generatedId,
                        publication.date(),
                        publication.content(),
                        publication.commentsAmount(),
                        publication.likesAmount(),
                        publication.accountId(), null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Publication find(long e) {
        return null;
    }

    @Override
    public boolean update(Publication publication) {
        return false;
    }

    @Override
    public int delete(long e) {
        return 0;
    }

    public List<Publication> findByAccountId(long accountId){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
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
}
