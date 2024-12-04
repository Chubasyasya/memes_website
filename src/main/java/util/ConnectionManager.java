package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {
    private static final int DEFAULT_POOL_SIZE = 20;
    private static BlockingQueue<Connection> pool;
    private static final Properties properties = new Properties();

    static {

        try {
            properties.load(new FileInputStream("C:\\JavaProjects\\oris\\semesterwork\\memesWebApp\\src\\main\\resources\\db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initConnectionPool();
    }

    private static void initConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Integer poolSize = (Integer) properties.get("db.poolsize");
        int size = poolSize == null ? DEFAULT_POOL_SIZE : poolSize;
        pool = new ArrayBlockingQueue<>(size);

        for(int i = 0; i < size; i++){

            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args)->method.getName().equals("close")?
                            pool.add((Connection) proxy):method.invoke(connection, args));
            pool.add(proxyConnection);
        }
    }
    public static Connection get(){
        try {
            return (Connection) pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection open(){
        try {
            return DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.name"),
                    properties.getProperty("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {

    }

}

