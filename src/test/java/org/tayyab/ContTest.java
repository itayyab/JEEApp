package org.tayyab;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.TestCase.assertTrue;

public class ContTest {
    public static final DockerImageName MYSQL_56_IMAGE = DockerImageName.parse("mysql:5.6.51");
    public static final DockerImageName MYSQL_57_IMAGE = DockerImageName.parse("mysql:5.7.34");
    public static final DockerImageName MYSQL_80_IMAGE = DockerImageName.parse("mysql:8.0.24");
    @Test
    public void testSpecificVersion() throws SQLException {
        try (MySQLContainer<?> mysqlOldVersion = new MySQLContainer<>(MYSQL_56_IMAGE)
//                .withConfigurationOverride("somepath/mysql_conf_override")
              //  .withLogConsumer(new Slf4jLogConsumer(logger))
        )
        {

            mysqlOldVersion.start();

            ResultSet resultSet = performQuery(mysqlOldVersion, "SELECT VERSION()");
            String resultSetString = resultSet.getString(1);

            assertTrue("The database version can be set using a container rule parameter", resultSetString.startsWith("5.6"));
        }
    }
    protected ResultSet performQuery(JdbcDatabaseContainer<?> container, String sql) throws SQLException {
        DataSource ds = getDataSource(container);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }

    protected DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }
}
