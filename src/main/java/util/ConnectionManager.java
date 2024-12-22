package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConnectionManager {
    private static final HikariDataSource dataSource;
    private static final MyProperties properties = new MyProperties();

    static {
        try {
            properties.load(new FileInputStream("C:\\JavaProjects\\oris\\semesterwork\\memesWebApp\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.name"));
        config.setPassword(properties.getProperty("db.password"));
        config.setDriverClassName("org.postgresql.Driver");

        config.setMaximumPoolSize(
                Integer.parseInt(properties.getProperty("db.poolsize", "10")));
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);

        dataSource = new HikariDataSource(config);
    }

    public static java.sql.Connection get() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get a database connection", e);
        }
    }

    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    private ConnectionManager() {
    }
}
