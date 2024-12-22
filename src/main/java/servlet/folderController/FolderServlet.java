package servlet.folderController;

import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FolderService;
import service.ImageService;
import servlet.FreemarkerConfigSingleton;
import util.StringToLongUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/profile/folders/*")
public class FolderServlet extends HttpServlet {
    private FolderService folderService;
    private ImageService imageService;
    @Override
    public void init() throws ServletException {
        super.init();
        folderService = (FolderService) getServletContext().getAttribute("folderService");
        imageService = (ImageService) getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid folder ID");
            return;
        }

        long folderId;
        try {
            folderId = StringToLongUtil.getLongParameter(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid folder ID format");
            return;
        }

        Account currentAccount = (Account) req.getSession().getAttribute("currentAccount");
        if (currentAccount == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("folderId", folderId);
        FreemarkerConfigSingleton.render("folder.ftl", model, req, resp);
    }

}

