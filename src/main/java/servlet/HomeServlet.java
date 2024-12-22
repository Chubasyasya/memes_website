package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Selected background: " + req.getSession().getAttribute("background"));

        boolean isLoggedIn = req.getSession().getAttribute("currentAccount") != null;
        Map<String, Object> model = new HashMap<>();
        model.put("isVariablePage", true);
        model.put("isLoggedIn", isLoggedIn);
        FreemarkerConfigSingleton.render("home.ftl", model, req, resp);
    }
}
