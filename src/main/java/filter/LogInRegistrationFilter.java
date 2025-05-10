package filter;

import entity.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AccountService;

import java.io.IOException;

@WebFilter({"/login", "/registration"})
public class LogInRegistrationFilter extends HttpFilter {
    private AccountService accountService;

    @Override
    public void init() {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String accountIdentifier = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accountIdentifier")) {
                    accountIdentifier = cookie.getValue();
                }
            }
        }
        if(accountIdentifier!=null) {
            Account account = accountService.findByIdentifier(accountIdentifier);
            if (account != null) {
                req.getSession().setAttribute("currentAccount", account);
            }
            if (!req.getRequestURI().endsWith("/profile")) {
                resp.sendRedirect(getServletContext().getContextPath() + "/profile");
                return;
            }
        }

        if(((Account) req.getSession().getAttribute("currentAccount"))!=null){
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
        }

        super.doFilter(req, resp, chain);
    }

}
