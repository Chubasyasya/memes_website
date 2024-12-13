package servlet;

import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;

import java.io.IOException;
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
        FreemarkerBuilder freemarkerBuilder = (FreemarkerBuilder) getServletContext().getAttribute("freemarker");

        String accountIdentifier = null;
        for(Cookie cookie:req.getCookies()){
            if(cookie.getName().equals("accountIdentifier")){
                accountIdentifier = cookie.getValue();
            }
        }
        if(accountIdentifier!=null) {
            Account account = accountService.findByIdentifier(accountIdentifier);
            if(account!=null){
                req.getSession().setAttribute("currentAccount", account);
            }
            String previousUrl = req.getParameter("redirectUrl");
            redirect(previousUrl, resp);
        }else {
            freemarkerBuilder.render("login.ftl", null, resp);
        }
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
                accountService.saveIdentifier(account.id(), identifier);
                resp.addCookie(cookie);
            }
            String previousUrl = req.getParameter("redirectUrl");
            redirect(previousUrl, resp);

        } else{
            resp.sendRedirect(getServletContext().getContextPath()+"/login");
        }
    }

    private void redirect(String previousUrl, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(Objects.requireNonNullElse(previousUrl, getServletContext().getContextPath()+"/profile"));
    }
}
