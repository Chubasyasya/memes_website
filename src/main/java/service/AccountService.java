package service;

import dao.AccountDao;
import entity.Account;
import exception.ValidationException;
import filter.AccountFilter;
import jakarta.servlet.http.HttpServletRequest;
import validator.CreateUserValidator;
import validator.ValidationResult;

import java.util.List;

public class AccountService {
    AccountDao accountDao = AccountDao.getInstance();
    private static final AccountService INSTANCE = new AccountService();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    private AccountService() {
    }

    public static AccountService getInstance(){
        return INSTANCE;
    }

    public Account find(String login, String password){
        List<Account> accountsList = accountDao.findByFilter(new AccountFilter.AccountFilterBuilder().setEmail(login).setPassword(password).build());
        return accountsList.size() == 1 ? accountsList.getFirst() : null;
    }

    public Account find(String login){
        List<Account> accountsList = accountDao.findByFilter(new AccountFilter.AccountFilterBuilder().setEmail(login).build());

        return accountsList.size() == 1 ? accountsList.getFirst() : null;
    }

    public Account save(HttpServletRequest req){
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        Account account = new Account(-1, name, null, login, password, null, null, null, null);
        ValidationResult validationResult = createUserValidator.isValid(account);

        if(!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors());
        }
        return accountDao.save(account);
    }



}
