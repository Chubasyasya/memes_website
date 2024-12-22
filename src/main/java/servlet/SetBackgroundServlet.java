package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/change-background")
public class SetBackgroundServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String background = req.getParameter("background");

        HttpSession session = req.getSession();

        session.setAttribute("background", background);

        resp.sendRedirect(getServletContext().getContextPath()+"/home");
    }
}
