package org.studyeasy;
 
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

public class HelloServletM extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Hello, World!");
        
        //String query=request.getParameter("q");
        String val1=request.getParameter("v1");
        String val2=request.getParameter("v2");
        response.getWriter().print("You sarched for: "+val1+" and "+val2);
    }
}