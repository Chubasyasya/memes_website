package util;

import filter.AccountFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountSQLStringBuilder {
    List<Object> parameters;
    List<String> whereSql;

    public AccountSQLStringBuilder() {
        this.parameters = new ArrayList<>();
        this.whereSql = new ArrayList<>();
    }

    public void build(AccountFilter filter) {
        Map<String, Object> fieldMappings = new HashMap<>();
        fieldMappings.put("password", filter.getPassword());
        fieldMappings.put("email", filter.getEmail());
        fieldMappings.put("name", filter.getName());
        fieldMappings.put("phone_number", filter.getPhoneNumber());
        fieldMappings.put("\"status\"", filter.getStatus());
        fieldMappings.put("birthday", filter.getBirthday());


        fieldMappings.forEach((field, value) -> {
            if (value != null) {
                parameters.add(value);
                whereSql.add(field + " = ?");
            }
        });
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public List<String> getWhereSql() {
        return whereSql;
    }
}
