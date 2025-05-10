package servlet.accountController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import service.AccountService;

import java.io.IOException;

@WebServlet("/exit")
public class ExitServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accountIdentifier".equals(cookie.getName())) {
                    String accountIdentifier = cookie.getValue();
                    accountService.deleteIdentifier(accountIdentifier);
                }
            }
        }

        Cookie cookie = new Cookie("accountIdentifier", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
