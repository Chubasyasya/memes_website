package servlet.commentController;

import entity.Account;
import entity.Comment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CommentService;
import util.StringToLongUtil;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/comment/create")
public class CreateCommentServlet extends HttpServlet {
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = (CommentService) getServletContext().getAttribute("commentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("currentAccount");
        String pathInfo = req.getPathInfo();

        String publicationIdString = req.getParameter("publicationId");
        String content = req.getParameter("content");

        if (publicationIdString == null || content == null || content.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректные данные комментария");
            return;
        }

        long publicationId = StringToLongUtil.getLongParameter(publicationIdString);

        Comment comment = new Comment(
                content,
                LocalDate.now(),
                0,
                0,
                false,
                publicationId,
                account.getId(),
                account.getName()
        );
        commentService.createComment(comment);

        resp.sendRedirect(req.getContextPath() + "/publication/" + publicationId);

    }
}
