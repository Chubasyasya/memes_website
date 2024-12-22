package servlet.publicationController;

import adapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Publication;
import enums.SortType;
import freemarker.template.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublicationService;
import servlet.FreemarkerConfigSingleton;
import util.StringToLongUtil;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/feedNext")
public class AjaxPublicationServlet extends HttpServlet {
    private PublicationService publicationService;

    @Override
    public void init() throws ServletException {
        publicationService = (PublicationService) getServletContext().getAttribute("publicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int offset = Integer.parseInt(req.getParameter("offset"));
        int limit = Integer.parseInt(req.getParameter("limit"));

        List<Publication> publications;
        if (req.getParameter("ownerId")!=null){
            publications = profileFeed(StringToLongUtil.getLongParameter(req.getParameter("ownerId")), offset, limit);
        }else{
            String sort = req.getParameter("sort");
            String searchMask = req.getParameter("search");

            try {
                publications = mainFeed(offset, limit, sort, searchMask);
            }catch (IllegalArgumentException e){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid sort type: " + sort);
                return;
            }
        }

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

    private List<Publication> mainFeed(int offset, int limit, String sort, String searchMask) throws IllegalArgumentException{
        SortType sortType = SortType.DATE_DESC;
        if(sort!=null) {
            sortType = SortType.fromValue(sort);
        }

        if (searchMask != null && !searchMask.isEmpty()) {
            return publicationService.getPublicationsByMask(searchMask, offset, limit, sortType);
        } else {
            return publicationService.getPublications(offset, limit, sortType);
        }
    }

    private List<Publication> profileFeed(long ownerId, int offset, int limit){
        return  publicationService.findByAccountId(ownerId, offset, limit);
    }

    private String renderPost(Publication publication) {
        try (Writer writer = new StringWriter()) {
            Configuration cfg = FreemarkerConfigSingleton.getCfg();
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
