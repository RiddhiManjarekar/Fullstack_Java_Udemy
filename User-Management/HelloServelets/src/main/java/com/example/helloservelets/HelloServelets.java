package com.example.helloservelets;
 
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
 
public class HelloServelets extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    // Handles GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to HTML
        response.setContentType("text/html");
        
        // Get the print writer to write the response
        PrintWriter out = response.getWriter();
        
        // Write HTML content
        out.println("<html>");
        out.println("<head><title>Hello Servlet</title></head>");
        out.println("<body>");
        out.println("<h1>Hello, World!</h1>");
        out.println("<p>This is a simple servlet example.</p>");
        out.println("</body>");
        out.println("</html>");
    }
}