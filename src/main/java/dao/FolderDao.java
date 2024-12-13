package dao;

import entity.Folder;
import jakarta.ws.rs.DELETE;
import mapper.AccountRowMapper;
import mapper.FolderRowMapper;
import util.ConnectionManager;
import util.StatementBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FolderDao extends Dao<Folder> {
    private volatile static FolderDao INSTANCE;
    private final String SAVE_SQL = """
        insert into folder (name, description, image_amount, date_created, account_id)
        values (?, ?, ?, ?, ?)
    """;

    private final String FIND_BY_ACCOUNT_ID_SQL = """
            select *
            from folder
            where account_id = ?;
            """;
    private final String DELETE_BY_NAME_AND_ACCOUNT_ID_SQL = """
            delete from folder
            where account_id = ? and name = ?;
            """;


    private FolderDao() {
        mapper = FolderRowMapper.getInstance();
    }

    public static FolderDao getInstance(){
        if(INSTANCE == null){
            synchronized (FolderDao.class){
                if(INSTANCE == null){
                    INSTANCE = new FolderDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Folder folder) {
        try (PreparedStatement statement = statementBuilder.createStatement(SAVE_SQL)) {
            statement.setString(1, folder.name());
            statement.setString(2, folder.description());
            statement.setInt(3, folder.imageAmount());
            statement.setDate(4, Date.valueOf(folder.dateCreated()));
            statement.setLong(5, folder.accountId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Folder find(long e) {
        return null;
    }

    public List<Folder> findByAccountId(long accountId){
        try(PreparedStatement statement = statementBuilder.createStatement(FIND_BY_ACCOUNT_ID_SQL)) {
            statement.setLong(1, accountId);

            List<Folder> folders = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                folders.add((Folder) mapper.mapRow(resultSet));
            }
            return folders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Folder folder) {

    }

    @Override
    public void delete(long e) {

    }

    public void deleteByNameAndAccountId(long accountId, String name){
        try(PreparedStatement statement = statementBuilder.createStatement(DELETE_BY_NAME_AND_ACCOUNT_ID_SQL)) {
            statement.setLong(1, accountId);
            statement.setString(2, name);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
