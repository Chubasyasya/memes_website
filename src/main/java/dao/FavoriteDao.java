package dao;

import com.oracle.wls.shaded.org.apache.bcel.generic.INSTANCEOF;
import entity.Favorite;
import jakarta.ws.rs.DELETE;
import mapper.FavoriteRowMapper;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FavoriteDao extends Dao<Favorite> {
    private static final String DELETE_BY_ACCOUNT_AND_PUBLICATION_ID_SQL = """
        delete from favorite
        where account_id = ? and publication_id = ?;
    """;
    private volatile static FavoriteDao INSTANCE;

    private final String SAVE_SQL = """
            insert into favorite
            values(?, ?)
            """;
    private final String FIND_BY_ACCOUNT_PUBLICATION_ID_SQL = """
            select count(*)
            from favorite
            where account_id = ? and publication_id = ?;
        """;
    private FavoriteDao() {
        mapper = FavoriteRowMapper.getInstance();
    }

    public static FavoriteDao getInstance() {
        if (INSTANCE == null) {
            synchronized (FavoriteDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Favorite favorite) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setLong(1, favorite.getAccountId());
            statement.setLong(2, favorite.getPublicationId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Favorite find(long e) {
        return null;
    }

    public boolean findByAccountIdPublicationId(long accountId, long publicationId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ACCOUNT_PUBLICATION_ID_SQL)) {
            statement.setLong(1, accountId);
            statement.setLong(2, publicationId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


        @Override
    public void update(Favorite favorite) {
    }

    @Override
    public void delete(long e) {
    }

    public void delete(long accountId, long publicationId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ACCOUNT_AND_PUBLICATION_ID_SQL)) {
            statement.setLong(1, accountId);
            statement.setLong(2, publicationId);

            statement.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
