package org.tayyab;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ContTest.class);
    @Test
    public void testSpecificVersion() throws SQLException {
        try (MySQLContainer<?> mysqlOldVersion = new MySQLContainer<>(MYSQL_80_IMAGE)
                .withConfigurationOverride("mysql_conf_override")
                .withDatabaseName("jersey_db_test")
                .withUsername("root")
                .withPassword("root")
                .withEnv("MYSQL_ROOT_PASSWORD", "root")
             .withInitScript("jersey.sql")
//                .withExposedPorts(34343)
//                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
//                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(34343), new ExposedPort(3306)))
//                ));
//                .withConfigurationOverride("somepath/mysql_conf_override")
              //  .withLogConsumer(new Slf4jLogConsumer(logger))
        )
        {

            mysqlOldVersion.start();
            logger.info("MySQL JBDC: {}",mysqlOldVersion.getJdbcUrl());

            ResultSet resultSet = performQuery(mysqlOldVersion, "SELECT VERSION()");
            String resultSetString = resultSet.getString(1);
            logger.info("MySQL result: {}",resultSetString);
            assertTrue("The database version can be set using a container rule parameter", resultSetString.startsWith("8.0.24"));
        }catch (Exception ex){
            logger.error(ex.getMessage());
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
