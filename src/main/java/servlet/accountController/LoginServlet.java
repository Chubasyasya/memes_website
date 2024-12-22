package servlet.accountController;

import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;
import servlet.FreemarkerConfigSingleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Map<String, Object> model = new HashMap<>();
            model.put("isVariablePage", true);
            model.put("isLoggedIn", false);

            FreemarkerConfigSingleton.render("login.ftl", model, req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("remember_me");

        Account account = accountService.find(login, password);
        if(account!= null){
            req.getSession().setAttribute("currentAccount", account);
            if(rememberMe!=null){
                String identifier = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("accountIdentifier", identifier);
                accountService.saveIdentifier(account.getId(), identifier);
                resp.addCookie(cookie);
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");

        } else{
            resp.sendRedirect(getServletContext().getContextPath()+"/login");
        }
    }

}
