package Lab15.src.main.java.com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/AddAnnouncement")
public class AddAnnouncementServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        if (session.getAttribute("loggedInUser")==null) return;
        String creator = (String) session.getAttribute("loggedInUser");
        Date currentDate = new Date();
        String data = currentDate.toString();

        Adboard.getInstance().newAd(title, text, creator, data);

        response.sendRedirect("main_page.jsp");
    }
}

