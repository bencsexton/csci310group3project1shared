package csci310.servlet.favorites;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FavoritesListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // handle GET request
        HttpSession session = req.getSession();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            HashSet<String> favorites = (HashSet<String>) session.getAttribute("favorites");
            if (favorites == null) { // If session not yet initialized
                favorites = new HashSet<>();
                session.setAttribute("favorites", favorites);
            }
            JsonObject response = new JsonObject();
            response.add("favorites", new JsonArray());
            for (String fav : favorites) {
                response.getAsJsonArray("favorites").add(new JsonPrimitive(fav));
            }

            resp.getWriter().write(response.toString());

        } catch (ClassCastException e) {
            resp.setStatus(400);
        }

    }

}