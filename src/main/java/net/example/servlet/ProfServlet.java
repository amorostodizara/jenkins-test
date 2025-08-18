package net.example.servlet;

import net.example.dao.ProfDAO;
import net.example.model.ProfModel;
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

@WebServlet("/api/profs/*")
public class ProfServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfDAO profDAO = new ProfDAO();

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
        String keyword = request.getParameter("search");

        if (keyword != null && !keyword.isEmpty()) {
            // Rechercher des professeurs par mot-clé
            List<ProfModel> profs = profDAO.searchProf(keyword);
            response.getWriter().write(new JSONArray(profs).toString());
        } else if (pathInfo == null || pathInfo.equals("/")) {
            // Récupérer tous les professeurs
            List<ProfModel> profs = profDAO.getAllProfs();
            response.getWriter().write(new JSONArray(profs).toString());
        } else {
            // Récupérer un professeur par ID
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                String codeprof = pathParts[1]; 
                ProfModel prof = profDAO.getProfById(codeprof);
                if (prof != null) {
                    response.getWriter().write(new JSONObject(prof).toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Prof non trouvé\"}");
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

        // Créer un nouveau professeur
        ProfModel prof = new ProfModel();
        prof.setCodeprof(json.getString("codeprof")); // Ajouter codeprof
        prof.setNom(json.getString("nom"));
        prof.setPrenom(json.getString("prenom"));
        prof.setGrade(json.optString("grade", null));

        profDAO.saveProf(prof);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"Prof est créer avec succèss\"}");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
       
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.split("/").length != 2) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Reqête invalide\"}");
            return;
        }

        // Mettre à jour un professeur
        String codeprof = pathInfo.split("/")[1]; // codeprof est désormais un String
        JSONObject json = getRequestBody(request);
        String newNom = json.getString("nom");
        String newPrenom = json.getString("prenom");
        String newGrade = json.optString("grade", null);

        profDAO.updateProf(codeprof, newNom, newPrenom, newGrade);
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
        response.getWriter().write("{\"message\": \"Prof modifié avec succèss\"}");
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

        // Supprimer un professeur
        String codeprof = pathInfo.split("/")[1]; // codeprof est désormais un String
        profDAO.deleteProf(codeprof);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\": \"Prof supprimé avec success\"}");
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