package net.example.servlet;

import net.example.dao.UserDAO;
import net.example.model.UserModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            // Récupérer tous les utilisateurs
            List<UserModel> users = userDAO.getAllUsers();
            response.getWriter().write(new JSONArray(users).toString());
        } else {
            // Récupérer un utilisateur par son ID
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                try {
                    Long id = Long.parseLong(pathParts[1]);
                    UserModel user = userDAO.getUserById(id);
                    if (user != null) {
                        response.getWriter().write(new JSONObject(user).toString());
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\": \"User not found\"}");
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Invalid user ID\"}");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Créer un nouvel utilisateur
        JSONObject json = getRequestBody(request);
        UserModel user = new UserModel();
        user.setUsername(json.getString("username"));
        user.setEmail(json.getString("email"));
        user.setPassword(json.getString("password")); // Ajout du champ password

        userDAO.saveUser(user);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"User created successfully\"}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mettre à jour un utilisateur existant
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid request\"}");
            return;
        }

        Long id = Long.parseLong(pathInfo.split("/")[1]);
        JSONObject json = getRequestBody(request);
        String newEmail = json.getString("email");

        userDAO.updateUser(id, newEmail);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"User updated successfully\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Supprimer un utilisateur
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid request\"}");
            return;
        }

        Long id = Long.parseLong(pathInfo.split("/")[1]);
        userDAO.deleteUser(id);
        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"User deleted successfully\"}");
    }

    // Méthode utilitaire pour lire le corps de la requête
    private JSONObject getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        return new JSONObject(requestBody.toString());
    }
}