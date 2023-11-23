package Lab15.src.main.java.com.example;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean valid = Adboard.getInstance().checkLogin(username, password);

        if (valid) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", username);
            response.sendRedirect("main_page.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }


}

