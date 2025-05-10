package service;

import dao.AccountDao;
import entity.Account;
import exception.ValidationException;
import entity.filter.AccountFilter;
import jakarta.servlet.http.HttpServletRequest;
import util.PasswordHasher;
import validator.AccountFilterValidator;
import validator.CreateUserValidator;
import validator.ValidationResult;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class AccountService {
    AccountDao accountDao = AccountDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public AccountService() {
    }

    public Account find(String login, String password){
        // Хэширование паролей
        String salt = accountDao.findSalt(login);
        String hashedPassword = PasswordHasher.hashPassword(password, salt);

        List<Account> accountsList = accountDao.findByFilter(new AccountFilter.AccountFilterBuilder().setEmail(login).setPassword(hashedPassword).build());
        return accountsList.size() == 1 ? accountsList.getFirst() : null;
    }

    public Account find(String login){
        List<Account> accountsList = accountDao.findByFilter(new AccountFilter.AccountFilterBuilder().setEmail(login).build());

        return accountsList.size() == 1 ? accountsList.getFirst() : null;
    }

    public Account find(long id){
        return accountDao.find(id);
    }

    public void save(String login, String password, String name){
        // Хэширование паролей
        String salt = PasswordHasher.generateSalt();
        String hashedPassword = PasswordHasher.hashPassword(password, salt);

        Account account = new Account(-1, name, login, hashedPassword, null, null, null, salt);
        ValidationResult validationResult = createUserValidator.isValid(account);

        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        accountDao.save(account);
    }

    public void update(HttpServletRequest req) {
        long id = ((Account) req.getSession().getAttribute("currentAccount")).getId();

        Function<String, String> getParameterOrNull = param -> {
            String value = req.getParameter(param);
            return (value == null || value.isEmpty()) ? null : value;
        };

        LocalDate birthday = req.getParameter("birthday").isEmpty() ? null : LocalDate.parse(req.getParameter("birthday"));
        String newPassword = getParameterOrNull.apply("password");
        String salt = null;
        String hashedPassword = null;


        // Хэширование паролей
        if (newPassword != null) {
            salt = PasswordHasher.generateSalt();
            hashedPassword = PasswordHasher.hashPassword(newPassword, salt);
        }

        AccountFilter filter = new AccountFilter.AccountFilterBuilder()
                .setId(id)
                .setName(getParameterOrNull.apply("name"))
                .setEmail(getParameterOrNull.apply("email"))
                .setPhoneNumber(getParameterOrNull.apply("phoneNumber"))
                .setPassword(hashedPassword)
                .setStatus(getParameterOrNull.apply("status"))
                .setBirthday(birthday)
                .setSalt(salt)
                .build();


        AccountFilterValidator validator = new AccountFilterValidator();
        ValidationResult validationResult = validator.isValid(filter);
        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        accountDao.update(filter);
    }

    public Account findByIdentifier(String accountIdentifier) {
        return accountDao.findByIdentifier(accountIdentifier);
    }

    public void saveIdentifier(long id, String identifier) {
        accountDao.saveIdentifier(id, identifier);
    }

    public void deleteIdentifier(String accountIdentifier) {
        accountDao.deleteIdentifier(accountIdentifier);
    }
}
