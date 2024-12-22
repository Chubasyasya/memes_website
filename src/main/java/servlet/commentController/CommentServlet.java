package servlet.commentController;

import adapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Comment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CommentService;
import servlet.FreemarkerConfigSingleton;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long publicationId = (long) req.getAttribute("publicationId");
        CommentService commentService = (CommentService) getServletContext().getAttribute("commentService");

        List<Comment> comments = commentService.findByPublicationId(publicationId);

        List<String> resultComment = new ArrayList<>();
        for(Comment comment:comments){
            String renderedComment = renderComment(comment);
            resultComment.add(renderedComment);
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String json = gson.toJson(resultComment);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    private String renderComment(Comment comment) {
        try (Writer writer = new StringWriter()) {
            Configuration cfg = FreemarkerConfigSingleton.getCfg();
            Template template = cfg.getTemplate("comment.ftl");

            Map<String, Object> model = new HashMap<>();

            model.put("comment", comment);

            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
