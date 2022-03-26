package org.tayyab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static junit.framework.TestCase.assertTrue;

public class ServletTest{
    private ServletTestModule tester;
    private WebMockObjectFactory factory;
    static String id="0";
    public static final DockerImageName MYSQL_80_IMAGE = DockerImageName.parse("mysql:8.0.24");
    @Before
    public void setup() {
        factory = new WebMockObjectFactory();
        tester = new ServletTestModule(factory);

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
    public void doPostAddTest() {
        tester.addRequestParameter("action", "no");
        tester.addRequestParameter("personsname","Person from Servlet post test");

        // instantiate the servlet
        tester.createServlet(PersonDataServlet.class);

        // call doGet
        tester.doPost();
        String msg=factory.getMockResponse().getHeader("msg");
        Gson jsonConverter = new GsonBuilder().create();
        String data= jsonConverter.fromJson(msg,String.class);
        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
        id= jsonObject.get("id").getAsString();
        msg= jsonObject.get("msg").getAsString();
        Assert.assertEquals("Data should save successfully", "Data saved Successfully", msg);

    }
//    @Test
//    public void doPostAddTestFail() {
//        tester.addRequestParameter("action", "no");
//        tester.addRequestParameter("personsname","");
//
//        // instantiate the servlet
//        tester.createServlet(PersonDataServlet.class);
//
//        // call doGet
//        tester.doPost();
//        String msg=factory.getMockResponse().getHeader("msg");
//        Gson jsonConverter = new GsonBuilder().create();
//        String data= jsonConverter.fromJson(msg,String.class);
//        JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
//        id= jsonObject.get("id").getAsString();
//        msg= jsonObject.get("msg").getAsString();
//        Assert.assertEquals("Data should save successfully", "Data saved Successfully", msg);
//
//    }
    @Test
    public void doGetDataTest() {
     tester.createServlet(PersonDataServlet.class);

        // call doGet
        tester.doGet();

        Gson jsonConverter = new GsonBuilder().create();

        String data=jsonConverter.toJson(factory.getMockRequest().getAttribute("data"));
        String resp= "[{\"id\":"+id+",\"name\":\"Person from Servlet post test\"}]";
       // assertion: status code should be 200
        Assert.assertEquals("Data",resp, data);
    }
    @Test
    public void doPostDeleteTest() {
        tester.addRequestParameter("action", "delete");
        tester.addRequestParameter("deleteid",id);
        // instantiate the servlet
        tester.createServlet(PersonDataServlet.class);

        // call doGet
        tester.doPost();

    // assertion: status code should be 200
        Assert.assertEquals("Data","Data deleted Successfully", factory.getMockResponse().getHeader("msg"));
    }
}

