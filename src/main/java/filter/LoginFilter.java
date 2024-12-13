package filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebFilter({"/like"})
public class LoginFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute("currentAccount") == null) {
            String redirectPath = req.getRequestURI();
            resp.sendRedirect(getServletContext().getContextPath()+"/login?redirectPath=" + redirectPath);
        } else {
            chain.doFilter(req, resp);
        }
    }
}
