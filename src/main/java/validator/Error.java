package validator;

import java.util.Objects;

public class Error extends java.lang.Error {
    String code;
    String message;

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Error of(String code, String message){
        return new Error(code, message);
    }


}
