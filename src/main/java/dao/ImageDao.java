package dao;

import entity.Image;
import util.ConnectionManager;

import java.sql.*;

public class ImageDao extends Dao<Image> {
    private volatile static ImageDao INSTANCE;
    private final String SAVE_SQL = """
            insert into image(path, name, publication_id)
            values(?, ?, ?);
            """;

    private ImageDao() {
    }

    public static ImageDao getInstance(){
        if(INSTANCE == null){
            synchronized (PublicationDao.class){
                if(INSTANCE == null){
                    INSTANCE = new ImageDao();
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public Image save(Image image) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, image.path());
            statement.setString(2, image.name());
            statement.setLong(3, image.publicationId());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                long generatedId = generatedKeys.getLong(1);
                return new Image(generatedId, image.path(), image.name(), image.publicationId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Image find(long e) {
        return null;
    }

    @Override
    public boolean update(Image image) {
        return false;
    }

    @Override
    public int delete(long e) {
        return 0;
    }
}
