package servlet;

import exception.ValidationException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import freemarker.template.Configuration;
import service.AccountService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private AccountService accountService = AccountService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Configuration cfg = (Configuration) getServletContext().getAttribute("cfg");
            Template template = cfg.getTemplate("registration.ftl");

            Map<String, Object> model = new HashMap<>();
            Object errors = req.getAttribute("errors");
            if (errors != null) {
                model.put("errors", errors);
            }

            template.process(model, resp.getWriter());

        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            accountService.save(req);
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            System.out.println("Errors: " + e.getErrors());
            doGet(req, resp);//вооооооооооооооооооооот тут проблемка
        }

    }
}
