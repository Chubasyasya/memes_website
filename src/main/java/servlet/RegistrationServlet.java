package servlet;

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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Map<String, Object> model = new HashMap<>();
            Object errors = req.getSession().getAttribute("errors");

            if (errors != null) {
                model.put("errors", errors);
            }

            FreemarkerBuilder freemarkerBuilder = (FreemarkerBuilder) getServletContext().getAttribute("freemarker");
            freemarkerBuilder.render("registration.ftl", model, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        try{
            accountService.save(login, password, name);
            resp.sendRedirect(getServletContext().getContextPath() + "/login");
        }catch (ValidationException e) {
            req.getSession().setAttribute("errors", e.getErrors());
            resp.sendRedirect(getServletContext().getContextPath()+"/registration");
        }

    }
}
