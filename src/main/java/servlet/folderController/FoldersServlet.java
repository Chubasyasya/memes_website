package servlet.folderController;

import entity.Account;
import entity.Folder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FolderService;
import servlet.FreemarkerConfigSingleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile/folders")
public class FoldersServlet extends HttpServlet {
    private FolderService folderService;
    @Override
    public void init() throws ServletException {
        super.init();
        folderService = (FolderService) getServletContext().getAttribute("folderService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageId = req.getParameter("imageId");

        Map<String, Object> model = new HashMap<>();
        Account currentAccount = (Account) req.getSession().getAttribute("currentAccount");
        List<Folder> folders = folderService.findByAccountId(currentAccount.getId());
        model.put("folders", folders);
        model.put("imageId", imageId);
        FreemarkerConfigSingleton.render("folders.ftl", model, req, resp);
    }
}
