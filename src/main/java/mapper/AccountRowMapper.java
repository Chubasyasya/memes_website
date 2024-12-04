package mapper;

import entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    private static AccountRowMapper instance;

    private AccountRowMapper() {}

    public static synchronized AccountRowMapper getInstance() {
        if (instance == null) {
            instance = new AccountRowMapper();
        }
        return instance;
    }
    @Override
    public Account mapRow(ResultSet resultSet) throws SQLException {
        return new Account(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("phone_number"),
                resultSet.getString("status"),
                resultSet.getDate("birthday") != null ? resultSet.getDate("birthday").toLocalDate() : null,
                null);
    }
}
