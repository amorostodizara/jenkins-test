package net.example.servlet;

import net.example.dao.OccuperDAO;
import net.example.model.OccuperModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/api/occupers/*")
public class OccuperServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OccuperDAO occuperDAO = new OccuperDAO();

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
            // Récupérer toutes les occupations
            List<OccuperModel> occupers = occuperDAO.getAllOccupers();
            JSONArray jsonArray = new JSONArray();

            for (OccuperModel occuper : occupers) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("id", occuper.getId());
                jsonObj.put("codeprof", occuper.getCodeprof());
                jsonObj.put("codesal", occuper.getCodesal());
                jsonObj.put("date", new SimpleDateFormat("yyyy-MM-dd").format(occuper.getDate()));
                jsonArray.put(jsonObj);
            }

            response.getWriter().write(jsonArray.toString());
        } else {
            // Récupérer une occupation par son ID
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2) {
                try {
                    int id = Integer.parseInt(pathParts[1]);
                    OccuperModel occuper = occuperDAO.getOccuperById(id);
                    if (occuper != null) {
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("id", occuper.getId());
                        jsonObj.put("codeprof", occuper.getCodeprof());
                        jsonObj.put("codesal", occuper.getCodesal());
                        jsonObj.put("date", new SimpleDateFormat("yyyy-MM-dd").format(occuper.getDate()));
                        response.getWriter().write(jsonObj.toString());
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\": \"Occupation non trouvé\"}");
                    }
                } catch (NumberFormatException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"error\": \"Occupation invalide: ID\"}");
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

        String codeprof = json.getString("codeprof");
        String codesal = json.getString("codesal");
        String dateString = json.getString("date");

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            OccuperModel occuper = new OccuperModel();
            occuper.setCodeprof(codeprof);
            occuper.setCodesal(codesal);
            occuper.setDate(date);

            occuperDAO.saveOccuper(occuper);
            response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
            response.getWriter().write("{\"message\": \"Occupation crée avec success\"}");
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Format date invalide\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setCorsHeaders(response);
        request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
        JSONObject json = getRequestBody(request);

        // Récupérer les données de la requête
        int id = json.getInt("id"); // Récupérer l'ID de l'entrée à mettre à jour
        String codeprof = json.getString("codeprof");
        String codesal = json.getString("codesal");
        String dateString = json.getString("date");

        try {
            // Convertir la date en objet Date
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

            // Utiliser la méthode updateOccuper pour mettre à jour l'entrée existante
            occuperDAO.updateOccuper(id, codeprof, codesal, date);

            // Répondre avec un message de succès
            response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
            response.getWriter().write("{\"message\": \"Occupation crée avec success\"}");
        } catch (ParseException e) {
            // Gérer l'erreur de format de date
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Format date invalide\"}");
        }
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

        int id = Integer.parseInt(pathInfo.split("/")[1]);
        occuperDAO.deleteOccuper(id);
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici
        response.getWriter().write("{\"message\": \"Occupation supprimé avec success\"}");
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