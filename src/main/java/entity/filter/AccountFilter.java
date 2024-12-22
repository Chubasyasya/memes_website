package entity.filter;

import java.time.LocalDate;

public class AccountFilter{
    private final long id;
    private final String name;
    private final String username;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final String status;
    private final LocalDate birthday;
    private final String salt;

    private AccountFilter(AccountFilterBuilder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.status = builder.status;
        this.birthday = builder.birthday;
        this.salt = builder.salt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getSalt() {
        return salt;
    }

    public static class AccountFilterBuilder{
        private long id;
        private String name;
        private String username;
        private String email;
        private String password;
        private String phoneNumber;
        private String status;
        private LocalDate birthday;
        private String salt;

        public AccountFilterBuilder() {
        }

        public AccountFilterBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AccountFilterBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AccountFilterBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountFilterBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountFilterBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public AccountFilterBuilder setStatus(String status) {
            this.status = status;
            return this;
        }

        public AccountFilterBuilder setBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public AccountFilterBuilder setSalt(String salt) {
            this.salt = salt;
            return this;
        }

        public  AccountFilter build(){
            return new AccountFilter(this);
        }
    }
}
