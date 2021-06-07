package org.tayyab;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mockrunner.mock.web.WebMockObjectFactory;
import com.mockrunner.servlet.ServletTestModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServletTest{
    private ServletTestModule tester;
    private WebMockObjectFactory factory;
    static String id="0";
    @Before
    public void setup() {
        factory = new WebMockObjectFactory();
        tester = new ServletTestModule(factory);
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

