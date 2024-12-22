package filter;

import entity.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebFilter("/*")
public class IsLoggedInFilter extends HttpFilter{
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        String queryString = req.getQueryString();
        String contextPath = req.getContextPath();

        if (requestURI.startsWith(contextPath + "/static/")) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Account account = (Account) session.getAttribute("currentAccount");

        if(requestURI.equals(contextPath+"/feed")|| requestURI.equals(contextPath+"/image-download")|| requestURI.equals(contextPath+"/home") || requestURI.equals(contextPath+"/") || requestURI.equals(contextPath+"/feedNext")){
            req.setAttribute("isLoggedIn", account!=null);
        }else {
            if (account == null) {
                if (!requestURI.endsWith("/login") && !requestURI.endsWith("/registration")) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            }
        }

        chain.doFilter(req, resp);
    }
}
