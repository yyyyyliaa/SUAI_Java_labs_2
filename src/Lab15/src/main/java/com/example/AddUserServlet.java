package Lab15.src.main.java.com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/AddUser")
public class AddUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (session.getAttribute("loggedInUser")==null) return;

        Adboard.getInstance().newUser(username, password);

        response.sendRedirect("main_page.jsp");
    }
}