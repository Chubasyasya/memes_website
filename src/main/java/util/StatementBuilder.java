package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementBuilder {
    public PreparedStatement createStatement(String sql) throws SQLException {
        Connection connection = ConnectionManager.get();
        return connection.prepareStatement(sql);
    }
}
