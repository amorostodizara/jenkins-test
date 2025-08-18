package net.example.servlet;

import net.example.dao.SalleDAO;
import net.example.model.SalleModel; // Mettre à jour l'import
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

@WebServlet("/api/salles/*")
public class SalleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SalleDAO salleDAO = new SalleDAO();

    private void setCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
        String pathInfo = request.getPathInfo();
       

        if (pathInfo == null || pathInfo.equals("/")) {
            // Récupérer toutes les salles
            List<SalleModel> salles = salleDAO.getAllSalles(); // Mettre à jour le type
            response.getWriter().write(new JSONArray(salles).toString());
        } else {
            // Récupérer une salle par son ID
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                String codesal = pathParts[1]; // codesal est désormais un String
                SalleModel salle = salleDAO.getSalleById(codesal); // Mettre à jour le type
                if (salle != null) {
                    response.getWriter().write(new JSONObject(salle).toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Salle non trouvé\"}");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
      
        JSONObject json = getRequestBody(request);

        // Créer une nouvelle salle
        SalleModel salle = new SalleModel(); // Mettre à jour le type
        salle.setCodesal(json.getString("codesal")); // Ajouter codesal
        salle.setDesignation(json.getString("designation"));

        salleDAO.saveSalle(salle); // Mettre à jour le type
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
        response.getWriter().write("{\"message\": \"Salle crée avec success\"}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
      
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Requete invalide\"}");
            return;
        }

        // Mettre à jour une salle
        String codesal = pathInfo.split("/")[1]; // codesal est désormais un String
        JSONObject json = getRequestBody(request);
        String designation = json.getString("designation");

        salleDAO.updateSalle(codesal, designation); // Mettre à jour le type
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
        response.getWriter().write("{\"message\": \"Salle modifié avec success\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
      
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Requete invalide\"}");
            return;
        }

        // Supprimer une salle
        String codesal = pathInfo.split("/")[1]; // codesal est désormais un String
        salleDAO.deleteSalle(codesal); // Mettre à jour le type
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
        response.getWriter().write("{\"message\": \"Salle supprimé avec success\"}");
    }

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