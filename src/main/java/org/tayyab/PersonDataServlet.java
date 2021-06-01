package org.tayyab;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PersonDataServlet")
public class PersonDataServlet extends HttpServlet {


    DbConnection dbConnection=new DbConnection();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // read form fields
        //getServletContext().getResourceAsStream("app.properties");
        if ("delete".equals(request.getParameter("action"))) {
            String username = request.getParameter("deleteid");

            DatabaseOperations databaseOperations = new DatabaseOperations();
            Response res = databaseOperations.DeletePersonData(dbConnection,username);
           // request.setAttribute("response", "ddeTELTED " + res);
            request.setAttribute("dataaction", "delete");
            //PrintWriter writer = response.getWriter();
//            String htmlRespone = "<html>";
//            htmlRespone += "<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">";
//            htmlRespone += "<strong>Data deleted!</strong>";
//            htmlRespone += "Y<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
//                    "            <span aria-hidden=\"true\">&times;</span>\n" +
//                    "        </button>";
//            htmlRespone += "</div>";
//            htmlRespone += "</html>";
           // htmlRespone += res;
            // response.sendRedirect("SaveData.jsp");
          //  writer.println(htmlRespone);
            //response.setCharacterEncoding("UTF-8");
          //  response.getWriter().write(htmlRespone);
            //response.sendRedirect("ShowData.jsp");
           // response.getWriter().println(htmlRespone);
          //  request.getRequestDispatcher("ShowData.jsp").include(request, response);
//            if (page != null && page.equals("inputForm")) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setHeader("msg",res.getEntity().toString());
               // response.sendRedirect("ShowData.jsp");
//            } else {
//                response.sendError(HttpServletResponse.SC_NOT_FOUND, "The requested page ["
//                        + page + "] not found.");
          //  }

        }else {
            String username = request.getParameter("personsname");


            System.out.println("personsname: " + username);
// get response writer
            DatabaseOperations dabaseOperations = new DatabaseOperations();
            PersonData personData = new PersonData();
            personData.setName(username);
            Response res = dabaseOperations.SavePersonData(dbConnection,personData);
            //PrintWriter writer = response.getWriter();
//
//            // build HTML code
//            String htmlRespone = "<html>";
//            htmlRespone += "<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">";
//            htmlRespone += "<strong>Data deleted!</strong>";
//            htmlRespone += "Y<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
//                    "            <span aria-hidden=\"true\">&times;</span>\n" +
//                    "        </button>";
//            htmlRespone += "</div>";
//            htmlRespone += "</html>";
          //  htmlRespone += res;
            //htmlRespone += responsezz.getStatus();




          //  htmlRespone += "</html>";
//
//
//            // return response
           // writer.println(htmlRespone);
           request.setAttribute("response", res);
            request.setAttribute("dataaction", "save");
            response.setHeader("msg",res.getEntity().toString());
        //    response.setContentType("text/plain");
            //response.setCharacterEncoding("UTF-8");

          ///  request.getRequestDispatcher("SaveData.jsp").include(request, response);

//            response.sendRedirect("SaveData.jsp");
//            response.getWriter().println(htmlRespone);
            response.setStatus(HttpServletResponse.SC_OK);
          //  response.sendRedirect("SaveData.jsp");

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // Boolean reload = (Boolean) request.getAttribute("reload");
        String username = request.getParameter("dataaction");

      //  if(username!="delete") {
            DatabaseOperations databaseOperations = new DatabaseOperations();
            List<PersonData> data = null;
            try {
                data = databaseOperations.GetPersonsData(dbConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("data", data);
         //   request.setAttribute("reload", true);
          //  request.getRequestDispatcher("ShowData.jsp").forward(request, response);
      //  }

      //
        //request.getRequestDispatcher("/WEB-INF/ShowData.jsp").forward(request, response);

        // request.getRequestDispatcher("/WEB-INF/page.jsp").forward(request, response);
    }
}
