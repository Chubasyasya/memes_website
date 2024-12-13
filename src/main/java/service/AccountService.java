package service;

import dao.AccountDao;
import entity.Account;
import exception.ValidationException;
import filter.AccountFilter;
import jakarta.servlet.http.HttpServletRequest;
import validator.AccountFilterValidator;
import validator.CreateUserValidator;
import validator.ValidationResult;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class AccountService {
    AccountDao accountDao = AccountDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public AccountService() {
    }

    public Account find(String login, String password){
        List<Account> accountsList = accountDao.findByFilter(new AccountFilter.AccountFilterBuilder().setEmail(login).setPassword(password).build());
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
        Account account = new Account(-1, name, login, password, null, null, null, null, null);
        ValidationResult validationResult = createUserValidator.isValid(account);

        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        accountDao.save(account);
    }

    public void update(HttpServletRequest req) {
        long id = ((Account) req.getSession().getAttribute("currentAccount")).id();

        Function<String, String> getParameterOrNull = param -> {
            String value = req.getParameter(param);
            return (value == null || value.isEmpty()) ? null : value;
        };

        LocalDate birthday = req.getParameter("birthday").isEmpty() ? null : LocalDate.parse(req.getParameter("birthday"));
        AccountFilter filter = new AccountFilter.AccountFilterBuilder().setId(id)
                .setName(getParameterOrNull.apply("name"))
                .setEmail(getParameterOrNull.apply("email"))
                .setPhoneNumber(getParameterOrNull.apply("phoneNumber"))
                .setPassword(getParameterOrNull.apply("password"))
                .setStatus(getParameterOrNull.apply("status"))
                .setBirthday(birthday).setId(id).build();

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
