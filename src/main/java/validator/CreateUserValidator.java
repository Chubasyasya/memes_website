package validator;


import dao.AccountDao;
import entity.Account;

import java.util.regex.Pattern;

public class CreateUserValidator implements Validator<Account>{
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    private static final AccountDao accountDao = AccountDao.getInstance();

    private CreateUserValidator() {
    }
    public  ValidationResult isValid(Account account){
        ValidationResult validationResult = new ValidationResult();

        if(account.name()==null){
            validationResult.add(Error.of("invalid.name", "name is invalid"));
        }
        if(account.password()==null){
            validationResult.add(Error.of("invalid.password", "password is invalid"));
        }
        if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", account.email())){
            validationResult.add(Error.of("invalid.email", "email is invalid"));
        }
        if(accountDao.emailExist(account.email())){
            validationResult.add(Error.of("invalid.email", "email is exist"));
        }
        if(accountDao.nameExist(account.name())){
            validationResult.add(Error.of("invalid.name", "name is exist"));
        }
        return validationResult;
    }


    public static CreateUserValidator getInstance(){
        return INSTANCE;
    }
}
