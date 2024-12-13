package dao;

import entity.Image;
import mapper.ImageRowMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao extends Dao<Image> {
    private volatile static ImageDao INSTANCE;
    private static final String FIND_BY_FOLDER_ID_SQL = """
            select *
            from image_in_folder
            where folder_id = ?;
            """;
    private final String SAVE_SQL = """
            insert into image(path, name, publication_id)
            values(?, ?, ?);
            """;
    private final String FIND_BY_PUBLICATION_ID = """
            select *
            from image
            where publication_id = ?;
            """;

    private ImageDao() {
        mapper = ImageRowMapper.getInstance();
    }

    public static ImageDao getInstance() {
        if (INSTANCE == null) {
            synchronized (PublicationDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Image image) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setString(1, image.path());
            statement.setString(2, image.name());
            statement.setLong(3, image.publicationId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image find(long e) {
        return null;
    }

    @Override
    public void update(Image image) {

    }

    @Override
    public void delete(long e) {

    }

    public List<Image> findByPublicationId(long imageId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PUBLICATION_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, imageId);

            List<Image> images = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                images.add((Image) mapper.mapRow(resultSet));
            }

            return images;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Image> findByFolderId(long folderId) {
        try(PreparedStatement statement = statementBuilder.createStatement(FIND_BY_FOLDER_ID_SQL)) {
            statement.setLong(1, folderId);

            ResultSet resultSet = statement.executeQuery();
            List<Image> images = new ArrayList<>();
            while (resultSet.next()){
                images.add((Image) mapper.mapRow(resultSet));
            }
            return images;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

