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

@WebServlet("/comment/delete")
public class DeleteCommentServlet extends HttpServlet {
    private CommentService commentService;
    @Override
    public void init() throws ServletException {
        commentService = (CommentService) getServletContext().getAttribute("commentService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = (Account) req.getSession().getAttribute("currentAccount");

        long commentId = StringToLongUtil.getLongParameter(req.getParameter("commentId"));
        long publicationId = StringToLongUtil.getLongParameter(req.getParameter("publicationId"));

        Comment comment = commentService.findById(commentId);
        if(comment.getAccountId() == account.getId()){
            commentService.delete(commentId);
        }
        resp.sendRedirect(req.getContextPath() + "/publication/" + publicationId);
    }
}
