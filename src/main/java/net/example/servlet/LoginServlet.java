package net.example.servlet;

import net.example.dao.UserDAO;
import net.example.model.UserModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Email reçu : " + email);
        System.out.println("Mot de passe reçu : " + password);

        UserModel user = userDAO.getUserByEmailAndPassword(email, password);

        if (user != null) {
            response.sendRedirect("home.jsp");
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<script type='text/javascript'>alert('Mauvais email ou mot de passe'); window.history.back();</script>");
        }
    }
}