package Util;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnectionManager {
    private static BasicDataSource dataSource;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Change this to your database driver
        dataSource.setUrl("jdbc:mysql://localhost:3306/pmc_atm");
        dataSource.setUsername("root");
        dataSource.setPassword("@rpana12#");

        // Other pool configuration settings
        dataSource.setInitialSize(5); // Initial number of connections
        dataSource.setMaxTotal(10);   // Maximum number of connections
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

}
