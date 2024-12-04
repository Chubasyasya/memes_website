package dao;

import entity.Account;
import filter.AccountFilter;
import mapper.AccountRowMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDao extends Dao<Account>{
    private volatile static AccountDao accountDao;
    private static final String FIND_ALL_SQL = """
            select *
            from account
            """;
    private static final String SAVE_SQL = """
            insert into account(name, username, email, password, phone_number, status, birthday) 
            values (?, ?, ?, ?, ?, ?, ?);
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

    private AccountDao() {
        mapper = AccountRowMapper.getInstance();
    }

    public static AccountDao getInstance(){
        if(accountDao == null){
            synchronized (AccountDao.class){
                if(accountDao == null){
                    accountDao = new AccountDao();
                }
            }
        }
        return accountDao;
    }

    @Override
    public Account save(Account account) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.name());
            statement.setString(2, account.username());
            statement.setString(3, account.email());
            statement.setString(4, account.password());
            statement.setString(5, account.phoneNumber());
            statement.setString(6, account.status());
            if (account.birthday() != null) {
                statement.setDate(7, Date.valueOf(account.birthday()));
            } else {
                statement.setNull(7, java.sql.Types.DATE);
            }

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                return new Account(generatedId,
                        account.name(),
                        account.username(),
                        account.email(),
                        account.password(),
                        account.phoneNumber(),
                        account.status(),
                        account.birthday(), null);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account find(long e) {
        return null;
    }

    public List<Account> findByFilter(AccountFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.getPassword() != null) {
            parameters.add(filter.getPassword());
            whereSql.add("password = ?");
        }
        if (filter.getEmail() != null) {
            parameters.add(filter.getEmail());
            whereSql.add("email = ?");
        }

        String where = whereSql.stream().collect(Collectors.joining(" AND ", parameters.size() > 0 ? "WHERE " : " ", ";"));
        String findAll = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(findAll)) {

            List<Account> userList = new LinkedList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add((Account) mapper.mapRow(resultSet));
            }
            return userList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        @Override
    public boolean update(Account account) {
        return false;
    }

    @Override
    public int delete(long e) {
        return 0;
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
}
