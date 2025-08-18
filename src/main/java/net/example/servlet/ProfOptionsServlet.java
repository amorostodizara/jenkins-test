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
import java.io.IOException;
import java.util.List;

@WebServlet("/profs/options")
public class ProfOptionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProfDAO profDAO = new ProfDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setCharacterEncoding("UTF-8"); // Ajout ici
        response.setContentType("application/json;charset=UTF-8"); // Mise à jour ici

        // Récupérer tous les professeurs pour les options des <select>
        List<ProfModel> profs = profDAO.getAllProfs();
        JSONArray jsonArray = new JSONArray();

        for (ProfModel prof : profs) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("codeprof", prof.getCodeprof());
            jsonObj.put("nom", prof.getNom());
            jsonObj.put("prenom", prof.getPrenom());
            jsonArray.put(jsonObj);
        }

        response.getWriter().write(jsonArray.toString());
    }
}