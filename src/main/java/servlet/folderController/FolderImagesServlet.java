package servlet.folderController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Image;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ImageService;
import util.LocalDateSerializer;
import util.StringToLongUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/folder/images")
public class FolderImagesServlet extends HttpServlet {
    private ImageService imageService;
    @Override
    public void init() throws ServletException {
        super.init();
        imageService = (ImageService) getServletContext().getAttribute("imageService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tablePage = Integer.parseInt(req.getParameter("tablePage"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        long folderId = StringToLongUtil.getLongParameter(req.getParameter("folderId"));

        List<Image> images = imageService.findImagesByFolderId(folderId, tablePage, limit);

        for (Image image : images) {
            if (image.getDateCreated() != null) {
                image.setDateAsString(image.getDateCreated().format(DateTimeFormatter.ISO_LOCAL_DATE));
            }
        }

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        writer.write(gson.toJson(images));
        writer.flush();
    }
}
