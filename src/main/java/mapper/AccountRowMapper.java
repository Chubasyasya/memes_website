package mapper;

import dao.FolderDao;
import dao.PublicationDao;
import entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    private static AccountRowMapper instance;
    private final PublicationDao publicationDao = PublicationDao.getInstance();
    private final FolderDao folderDao = FolderDao.getInstance();

    private AccountRowMapper() {}

    public static synchronized AccountRowMapper getInstance() {
        if (instance == null) {
            instance = new AccountRowMapper();
        }
        return instance;
    }
    @Override
    public Account mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Account(id,
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("phone_number"),
                resultSet.getString("status"),
                resultSet.getDate("birthday") != null ? resultSet.getDate("birthday").toLocalDate() : null,
                publicationDao.findByAccountId(id),
                folderDao.findByAccountId(id));
    }
}
