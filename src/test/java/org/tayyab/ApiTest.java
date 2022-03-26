package org.tayyab;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ApiTest extends JerseyTest {

    static String id="0";
    public static final DockerImageName MYSQL_80_IMAGE = DockerImageName.parse("mysql:8.0.24");
    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(Api.class);
    }
    @BeforeClass
    public static void initDb(){
        try (MySQLContainer<?> mysqlOldVersion = new MySQLContainer<>(MYSQL_80_IMAGE)
//                .withConfigurationOverride("mysql_conf_override")
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Test
    public void testAddData() {

        Response response = target("api/adddata").request().post(Entity.json("{\"id\":1,\"name\":\"Test Person 2\"}"));
        Gson jsonConverter = new GsonBuilder().create();
        String msg= response.readEntity(String.class).trim();
        String data= jsonConverter.fromJson(msg,String.class);
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
        id= jsonObject.get("id").getAsString();
        msg= jsonObject.get("msg").getAsString();

        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

      //  String content = response.readEntity(String.class);
        assertEquals("Data should save successfully", "Data saved Successfully", msg);
    }
    @Test
    public void testGetData() {
        Response response = target("api/getdata").request().get(Response.class);
      //  Response response = target("/greetings/hi").request()
                //.get();

        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        String content = response.readEntity(String.class);

        String resp= "[{\"id\":"+id+",\"name\":\"Test Person 2\"}]";
        assertEquals("Should be same", resp, content);
    }

    @Test
    public void testDeleteData() {
        String resp= "{\"id\":"+id+",\"name\":\"Test Person 2\"}";
        Response response = target("api/deletedata").request().post(Entity.json(resp));
        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        String content = response.readEntity(String.class);
        System.out.println(content);
        assertEquals("Data should be delete successfully", "Data deleted Successfully", content);
    }
}