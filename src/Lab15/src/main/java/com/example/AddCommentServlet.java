package Lab15.src.main.java.com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/AddComment")
public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String comment = request.getParameter("comment");
        String title = request.getParameter("title");
        if (session.getAttribute("loggedInUser")==null) return;
        String creator = (String) session.getAttribute("loggedInUser");

        Adboard.getInstance().addComment(title, comment, creator);

        response.sendRedirect("main_page.jsp");
    }
}