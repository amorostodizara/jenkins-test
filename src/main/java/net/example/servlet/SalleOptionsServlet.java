package net.example.servlet;

import net.example.dao.SalleDAO;
import net.example.model.SalleModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/salles/options")
public class SalleOptionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SalleDAO salleDAO = new SalleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici

        // Récupérer toutes les salles pour les options des <select>
        List<SalleModel> salles = salleDAO.getAllSalles();
        JSONArray jsonArray = new JSONArray();

        for (SalleModel salle : salles) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("codesal", salle.getCodesal());
            jsonObj.put("designation", salle.getDesignation());
            jsonArray.put(jsonObj);
        }

        response.getWriter().write(jsonArray.toString());
    }
}