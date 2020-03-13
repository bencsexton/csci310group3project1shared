package csci310.servlet.favorites;

import com.google.gson.JsonObject;
import csci310.servlet.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;

public class FavoritesAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            // handle POST request
            JsonObject postBody = ServletUtils.readPostBodyAsJson(req.getReader());
            String cityId = postBody.getAsJsonPrimitive("cityId").getAsString();
            HashSet<String> favorites = (HashSet<String>) session.getAttribute("favorites");
            if (favorites == null) { // If session not yet initialized
                favorites = new HashSet<>();
                session.setAttribute("favorites", favorites);
            }
            favorites.add(cityId);
            // Implicitly returns 200 status
        } catch (IllegalStateException | ClassCastException | NullPointerException e) {
            resp.setStatus(400);
        }
    }

}