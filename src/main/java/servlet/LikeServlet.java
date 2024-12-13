package servlet;

import entity.Account;
import entity.Publication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FavoriteService;
import service.PublicationService;
import util.StringToLongUtil;

import java.io.IOException;
import java.util.List;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {
    private final FavoriteService favoriteService = (FavoriteService) getServletContext().getAttribute("favoriteService");
    private final PublicationService publicationService = (PublicationService) getServletContext().getAttribute("publicationService");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long publicationId = StringToLongUtil.getLongParameter(req.getParameter("publicationId"));
            long accountId = StringToLongUtil.getLongParameter(req.getParameter("accountId"));

            favoriteService.addToFavorite(accountId, publicationId);
            publicationService.updateLikes(publicationId);
            Account currentAccount = (Account) req.getSession().getAttribute("currentAccount");
            List<Publication> publications = currentAccount.publications();

            for (int i = 0; i < publications.size(); i++) {
                Publication publication = publications.get(i);
                if (publicationId == publication.id()) {
                    publications.set(i, new Publication(publication.id(), publication.date(), publication.content(),
                            publication.commentsAmount(), publication.likesAmount(), publication.accountId(),
                            publication.images(), true));
                }
            }

            String referer = req.getHeader("Referer");
            if (referer != null) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный параметр запроса: " + e.getMessage());
        }

    }
}
