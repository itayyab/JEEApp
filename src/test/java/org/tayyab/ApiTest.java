package org.tayyab;


import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.applet.Main;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ApiTest extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(Api.class);
    }

   // private HttpServer server;
    //private WebTarget target;

   /* @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }



    @After
    public void tearDown() throws Exception {
        server.stop();
    }*/

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetData() {
        Response response = target("api/getdata").request().get(Response.class);
      //  Response response = target("/greetings/hi").request()
                //.get();

        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        String content = response.readEntity(String.class);
        assertEquals("[{\"id\":1,\"name\":\"Tayyab\"},{\"id\":2,\"name\":\"Tayyab 2\"}]", "[{\"id\":1,\"name\":\"Tayyab\"},{\"id\":2,\"name\":\"Tayyab 2\"}]", content);
    }
}