package validator;

import dao.AccountDao;
import entity.filter.AccountFilter;

import java.util.regex.Pattern;

public class AccountFilterValidator implements Validator<AccountFilter>{
    private final int MAX_STATUS_LEN = 100;
    private static final AccountFilterValidator INSTANCE = new AccountFilterValidator();
    private static final AccountDao accountDao = AccountDao.getInstance();
    @Override
    public ValidationResult isValid(AccountFilter accountFilter) {
        ValidationResult validationResult = new ValidationResult();

        if(accountFilter.getEmail()!=null && !Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", accountFilter.getEmail())){
            if(accountDao.emailExist(accountFilter.getEmail())){
                validationResult.add(Error.of("invalid.email", "email is exist"));
            }
            validationResult.add(Error.of("invalid.email", "email is invalid"));
        }
        if(accountFilter.getEmail()!=null && accountDao.nameExist(accountFilter.getName())){
            validationResult.add(Error.of("invalid.name", "name is exist"));
        }
        if(accountFilter.getStatus()!=null && accountFilter.getStatus().length()>MAX_STATUS_LEN){
            validationResult.add(Error.of("invalid.status", "status length too long"));
        }
        return validationResult;
    }
}
