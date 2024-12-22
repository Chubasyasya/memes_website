package servlet.publicationController;

import entity.Account;
import entity.Comment;
import entity.Publication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;
import service.CommentService;
import service.PublicationService;
import servlet.FreemarkerConfigSingleton;
import util.StringToLongUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/publication/*")
public class PublicationServlet extends HttpServlet {
    private PublicationService publicationService;
    private CommentService commentService;
    @Override
    public void init() throws ServletException {
        publicationService = (PublicationService) getServletContext().getAttribute("publicationService");
        commentService = (CommentService) getServletContext().getAttribute("commentService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Публикация не найдена");
            return;
        }

        long publicationId = StringToLongUtil.getLongParameter(pathInfo.substring(1));;

        Account account = (Account) req.getSession().getAttribute("currentAccount");
        Publication publication = publicationService.findById(publicationId);
        List<Comment> comments = commentService.findByPublicationId(publicationId);

        Map<String, Object> model = new HashMap<>();
        model.put("publication", publication);
        model.put("comments", comments);
        model.put("accountId", account.getId());

        FreemarkerConfigSingleton.render("publication.ftl", model, req, resp);
    }
}
