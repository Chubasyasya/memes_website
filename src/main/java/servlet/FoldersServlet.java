package servlet;

import entity.Account;
import entity.Folder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile/folders")
public class FoldersServlet extends HomeServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreemarkerBuilder freemarkerBuilder = (FreemarkerBuilder) getServletContext().getAttribute("freemarker");
        Map<String, Object> model = new HashMap<>();
        Account currentAccount = (Account) req.getSession().getAttribute("currentAccount");
        List<Folder> folders = currentAccount.folders();
        model.put("folders", folders);
        freemarkerBuilder.render("folders.ftl", model, resp);
    }
}
