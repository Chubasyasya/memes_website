package servlet;

import entity.Account;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;
import freemarker.template.Configuration;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AccountService accountService = AccountService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = null;
        for(Cookie cookie:req.getCookies()){
            if(cookie.getName().equals("username")){
                login = cookie.getValue();
            }
        }
        if(login!=null) {
            Account account = accountService.find(login);
            if(account!=null){
                req.getSession().setAttribute("username", account.username());
            }
            String previousUrl = req.getParameter("redirectUrl");
            redirect(previousUrl, resp);
        }else {
            try {

                Configuration cfg = (Configuration) getServletContext().getAttribute("cfg");

                Template template = cfg.getTemplate("login.ftl");
                template.process(null, resp.getWriter());


            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("remember_me");

        Account account = accountService.find(login, password);
        if(account!= null){
            req.getSession().setAttribute("username", account.username());
            req.getSession().setAttribute("currentAccount", account);
            if(rememberMe!=null){
                Cookie cookie = new Cookie("username", account.username());
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
