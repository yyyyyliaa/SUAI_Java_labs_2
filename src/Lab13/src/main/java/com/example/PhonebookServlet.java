package Lab13.src.main.java.com.example;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

//@WebServlet("/")
public class PhonebookServlet extends HttpServlet {
     private final com.example.Phonebook phonebook = new com.example.Phonebook();
     private final static String filePath = "/Users/yyyyyliaa/untitled1/src/main/java/com/example/phonebook.txt";

    @Override
    public void init(ServletConfig config) {
     phonebook.loadPhonebookFromFile(filePath);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Phonebook</title></head><body>");
        out.println("<h1>Phonebook</h1>");
        System.out.println(uri);
        switch (uri) {
            case "/addName/add" -> {
                if (!phonebook.contain(request.getParameter("name"))) {
                    String curName = request.getParameter("name");
                    if (Character.isLetter(curName.charAt(0))) {
                        phonebook.add(request.getParameter("name"), request.getParameter("phonee"));
                    } else {
                        out.println("Error\n");
                    }
                } else {
                    out.println("This user was already add\n");
                }
            }
            case "/addName/edit" -> {
                if (phonebook.contain(request.getParameter("name"))) {
                    String curName = request.getParameter("name");
                    if (Character.isLetter(curName.charAt(0))) {
                        phonebook.edit(request.getParameter("name"), request.getParameter("phone"));
                    } else out.println("Error\n");
                } else
                    out.println("This user is not in the list\n");
            }
            case "/addPhone/add" -> {
                if (phonebook.contain(request.getParameter("name"))) {
                    String curName = request.getParameter("name");
                    if (Character.isLetter(curName.charAt(0))) {
                        phonebook.add(request.getParameter("name"), request.getParameter("phone"));
                    } else out.println("Error\n");
                } else
                    out.println("This user is not in the list\n");
            }
            case "/addPhone/del" -> {
                if (phonebook.contain(request.getParameter("name"))) {
                    String curName = request.getParameter("name");
                    if (Character.isLetter(curName.charAt(0))) {
                        phonebook.del(request.getParameter("name"), request.getParameter("phone"));
                    } else out.println("Error\n");
                } else
                    out.println("This user is not in the list\n");
            }
        }
        phonebook.savePhonebookToFile(filePath);
        response.sendRedirect(request.getContextPath() + "/phonebook.jsp");
        out.println("</body>\n</html>");
    }

    @Override
    public void destroy(){
        phonebook.savePhonebookToFile(filePath);
    }

}