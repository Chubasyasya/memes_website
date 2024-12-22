package dao;

import entity.Publication;
import enums.SortType;
import mapper.PublicationRowMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationDao extends Dao<Publication> {
    private volatile static PublicationDao INSTANCE;
    //language=sql
    private static final String FIND_SQL = """
        select *
        from publication
        where id = ?;
    """;
    private final String SAVE_SQL = """
            insert into publication (date, content, comments_amount, likes_amount, account_id)
            values(?, ?, ?, ?, ?);
            """;

    private final String FIND_BY_ACCOUNT_ID = """
        select *
        from publication
        where account_id = ?
        order by date desc
        offset ?
        limit ?;
        """;
    private final String FIND_ALL_SQL = """
            select *
            from publication
            order by %s %s
            offset ?
            limit ?;
            """;
    private final String ADD_LIKE_SQL = """
            update publication
            set likes_amount = likes_amount+1
            where id = ?;
            """;
    private static final String DELETE_LIKE_SQL = """
            update publication
           set likes_amount = likes_amount-1
            where id = ?;
            """;
    private static final String GET_RANDOM_SQL = """
                SELECT * 
                FROM publication
                ORDER BY RANDOM() 
                LIMIT 1;
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
            statement.setDate(1, Date.valueOf(publication.getDate()));
            statement.setString(2, publication.getContent());
            statement.setInt(3, publication.getCommentsAmount());
            statement.setInt(4, publication.getLikesAmount());
            statement.setLong(5, publication.getAccountId());

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
    public Publication find(long id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement statement = connection.prepareStatement(FIND_SQL)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return (Publication) mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Publication publication) {
    }
    public void addLike(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(ADD_LIKE_SQL)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLike(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_LIKE_SQL)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
    public void delete(long e) {
    }

    public List<Publication> findByAccountId(long accountId, int offset, int limit){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ACCOUNT_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, accountId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);

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

    public List<Publication> findAll(int offset, int limit, SortType sortType) {
        String findSql = FIND_ALL_SQL.formatted(sortType.getField(), sortType.getDirection());

        try(Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(findSql)) {
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

    public Publication getRandom() {
        try(Connection connection = ConnectionManager.get();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_RANDOM_SQL);

            if(resultSet.next()){
                return (Publication) mapper.mapRow(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
