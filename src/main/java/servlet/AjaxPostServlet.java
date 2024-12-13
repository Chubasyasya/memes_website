package servlet;

import adapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Publication;
import freemarker.core.ParseException;
import freemarker.template.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublicationService;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/feedNext")
public class AjaxPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PublicationService publicationService = (PublicationService) getServletContext().getAttribute("publicationService");

        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));

        List<Publication> publications = publicationService.getPublications(offset, limit);

        List<String> resultPost = new ArrayList<>();
        for(Publication publication:publications){
            String post = renderPost(publication);
            resultPost.add(post);
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String json = gson.toJson(resultPost);

        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    private String renderPost(Publication publication) {
        try (Writer writer = new StringWriter()) {
            FreemarkerBuilder freemarkerBuilder = (FreemarkerBuilder) getServletContext().getAttribute("freemarker");
            Configuration cfg = freemarkerBuilder.getConfiguration();
            Template template = cfg.getTemplate("renderPost.ftl");

            Map<String, Object> model = new HashMap<>();

            model.put("publication", publication);

            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
