package servlet;

import entity.Account;
import entity.Folder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.FolderService;
import util.StringToLongUtil;

import java.io.IOException;

@WebServlet("/profile/folders/folder/*")
public class FolderServlet extends HttpServlet {
    private FolderService folderService;
    @Override
    public void init() throws ServletException {
        super.init();
        folderService = (FolderService) getServletContext().getAttribute("folderService");
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

        Folder folder = folderService.getFolderByIdAndAccount(folderId, currentAccount);
        if (folder == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Folder not found");
            return;
        }
        req.setAttribute("folder", folder);
    }

}

