package servlet.publicationController;

import entity.Account;
import entity.Publication;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.FavoriteService;
import service.PublicationService;
import util.StringToLongUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/like-publication")
public class LikeServlet extends HttpServlet {
    private FavoriteService favoriteService;
    private PublicationService publicationService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        favoriteService = (FavoriteService) context.getAttribute("favoriteService");
        publicationService = (PublicationService) context.getAttribute("publicationService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        StringBuilder jsonBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        }

        JSONObject json = new JSONObject(jsonBody.toString());
        long publicationId = StringToLongUtil.getLongParameter(json.getString("publicationId"));
        String action = json.getString("action");

        Account account = (Account) req.getSession().getAttribute("currentAccount");

        int currentLikeCount = publicationService.getLikeCount(publicationId);
        boolean isLiked;

        if ("like".equals(action)) {
            favoriteService.addToFavorite(account.getId(), publicationId);
            publicationService.addLike(publicationId);
            currentLikeCount++;
            isLiked = true;
        } else if ("unlike".equals(action)) {
            favoriteService.deleteFromFavorite(account.getId(), publicationId);
            publicationService.deleteLike(publicationId);
            currentLikeCount--;
            isLiked = false;
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }


        JSONObject responseJson = new JSONObject();
        responseJson.put("likeCount", currentLikeCount);
        responseJson.put("isLiked", isLiked);

        PrintWriter out = resp.getWriter();
        out.print(responseJson.toString());
        out.flush();
    }
}
