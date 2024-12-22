package dao;

import entity.Image;
import mapper.ImageRowMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao extends Dao<Image> {
    private volatile static ImageDao INSTANCE;
    //language=sql
    private static final String FIND_BY_FOLDER_ID_SQL = """
            select i.*
            from image_in_folder iif
            inner join image i on iif.image_id = i.id
            where iif.folder_id = ?
            offset ?
            limit ?;
            """;
    private final String SAVE_SQL = """
            insert into image(path, name, description, publication_id, date_created)
            values(?, ?, ?, ?, ?);
            """;
    private final String FIND_BY_PUBLICATION_ID = """
            select *
            from image
            where publication_id = ?;
            """;

    private final String FIND_SQL = """
            select  *
            from image
            where id = ?;
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
            statement.setString(1, image.getPath());
            statement.setString(2, image.getName());
            statement.setString(3, image.getDescription());
            statement.setLong(4, image.getPublicationId());
            statement.setDate(5, Date.valueOf(image.getDateCreated()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image find(long id){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_SQL)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return (Image) mapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Image image) {

    }

    @Override
    public void delete(long e) {

    }

    public List<Image> findByPublicationId(long publicationId) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_PUBLICATION_ID, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, publicationId);

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

    public List<Image> findByFolderId(long folderId, int offset, int limit) {
        try(PreparedStatement statement = statementBuilder.createStatement(FIND_BY_FOLDER_ID_SQL)) {
            statement.setLong(1, folderId);
            statement.setInt(2, offset);
            statement.setInt(3, limit);

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

