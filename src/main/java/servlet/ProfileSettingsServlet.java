package servlet;

import entity.Account;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@WebServlet("/profileSettings")
public class ProfileSettingsServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FreemarkerBuilder freemarkerBuilder = (FreemarkerBuilder) getServletContext().getAttribute("freemarker");

        Map<String, Object> model = new HashMap<>();
        model.put("account", req.getSession().getAttribute("currentAccount"));

        freemarkerBuilder.render("profileSettings.ftl", model, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            accountService.update(req);
            long id = ((Account) req.getSession().getAttribute("currentAccount")).id();
            Account account = accountService.find(id);
            req.getSession().setAttribute("currentAccount", account);
        }catch (ValidationException e) {
            req.getSession().setAttribute("errors", e.getErrors());
        }
        resp.sendRedirect(getServletContext().getContextPath()+"/profileSettings");
    }

}
