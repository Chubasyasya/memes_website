package dao;

import entity.Account;
import filter.AccountFilter;
import mapper.AccountRowMapper;
import util.AccountSQLStringBuilder;
import util.ConnectionManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDao extends Dao<Account>{
    private static final String FIND_BY_IDENTIFIER_SQL = """
        select a.id, a.name, a.email, a.password, a.phone_number, a.status, a.birthday
        from account_identifier ai
        join account a on a.id = ai.account_id
        where ai.identifier = ?;
    """;
    private static final String SAVE_IDENTIFIER_SQL = """
    insert into account_identifier(account_id, identifier)
    values (?, ?);
    """;
    private static final String DELETE_IDENTIFIER_SQL = """
        delete from account_identifier
        where identifier = ?;
    """;
    private volatile static AccountDao INSTANCE;
    private static final String FIND_ALL_SQL = """
            select *
            from account
            """;
    private static final String SAVE_SQL = """
            insert into account(name, email, password, phone_number, status, birthday) 
            values (?, ?, ?, ?, ?, ?);
            """;
    private static final String EMAIL_EXIST_SQL = """
            select count(*)
            from account
            where email = ?;
            """;

    private static final String NAME_EXIST_SQL = """
            select count(*)
            from account
            where name=?;
            """;
    private final String FIND_BY_ID_SQL = """
            select *
            from account
            where id = ?
            """;
    private final String UPDATE_BY_FILTER_SQL = """
            update account
            set
            """;
    private AccountDao() {
        mapper = AccountRowMapper.getInstance();
    }

    public static AccountDao getInstance(){
        if(INSTANCE == null){
            synchronized (AccountDao.class){
                if(INSTANCE == null){
                    INSTANCE = new AccountDao();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void save(Account account) {
         try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setString(1, account.name());
            statement.setString(2, account.email());
            statement.setString(3, account.password());
            statement.setString(4, account.phoneNumber());
            statement.setString(5, account.status());
            if (account.birthday() != null) {
                statement.setDate(6, Date.valueOf(account.birthday()));
            } else {
                statement.setNull(6, java.sql.Types.DATE);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account find(long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return (Account) mapper.mapRow(resultSet);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public List<Account> findByFilter(AccountFilter filter) {
        AccountSQLStringBuilder sqlStringBuilder = new AccountSQLStringBuilder();
        sqlStringBuilder.build(filter);
        List<String> whereSql = sqlStringBuilder.getWhereSql();
        List<Object> parameters = sqlStringBuilder.getParameters();
        String where = whereSql.stream().collect(Collectors.joining(" AND ", parameters.size() > 0 ? "WHERE " : " ", ";"));

        String findAll = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(findAll)) {

            List<Account> accountList = new LinkedList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account account = (Account) mapper.mapRow(resultSet);
                accountList.add(account);
            }
            return accountList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
    public void update(Account account) {

    }

    public void update(AccountFilter filter) {
        AccountSQLStringBuilder sqlStringBuilder = new AccountSQLStringBuilder();
        sqlStringBuilder.build(filter);
        List<String> whereSql = sqlStringBuilder.getWhereSql();
        List<Object> parameters = sqlStringBuilder.getParameters();
        String where = whereSql.stream().collect(Collectors.joining(", ", "", " WHERE id = ?;"));

        String update = UPDATE_BY_FILTER_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(update)) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            statement.setLong(parameters.size()+1, filter.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long e) {

    }

    public boolean emailExist(String email){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(EMAIL_EXIST_SQL)) {
            statement.setString(1, email);

            ResultSet amount = statement.executeQuery();
            if(amount.next()){
                return amount.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean nameExist(String name){
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(NAME_EXIST_SQL)) {
            statement.setString(1, name);

            ResultSet amount = statement.executeQuery();
            if(amount.next()){
                return amount.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public Account findByIdentifier(String accountIdentifier) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_IDENTIFIER_SQL)) {
            statement.setString(1, accountIdentifier);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return (Account) mapper.mapRow(resultSet);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public void saveIdentifier(long id, String identifier) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_IDENTIFIER_SQL)) {
            statement.setLong(1, id);
            statement.setString(2, identifier);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteIdentifier(String accountIdentifier) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_IDENTIFIER_SQL)) {
            statement.setString(1, accountIdentifier);

            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
