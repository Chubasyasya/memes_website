package servlet;

import entity.Publication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublicationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/generate-random-meme")
public class GenerateRandomMemeServlet extends HttpServlet {
    private PublicationService publicationService;
    @Override
    public void init() throws ServletException {
        publicationService = (PublicationService) getServletContext().getAttribute("publicationService");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Publication randomPublication = publicationService.generateRandomPublication();
        Map<String, Object> model = new HashMap<>();
        model.put("publication", randomPublication);
        FreemarkerConfigSingleton.render("generate-random-meme.ftl", model, req, resp);
    }
}
