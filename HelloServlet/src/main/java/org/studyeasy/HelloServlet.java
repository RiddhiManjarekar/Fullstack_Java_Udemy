package org.studyeasy;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
 
/**
 * The HelloServlet class handles HTTP GET requests and responds with a greeting message.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    /**
     * Handles the HTTP GET method.
     * @param request HttpServletRequest object containing the request from the client.
     * @param response HttpServletResponse object for sending the response to the client.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an input or output error is detected.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type to HTML
        response.setContentType("text/html");
        
        // Get the PrintWriter to write the response
        PrintWriter out = response.getWriter();
        
        // Write the HTML response
        out.println("<html><body>");
        out.println("<h1>Hello, Servlets!</h1>");
        out.println("</body></html>");
    }
}