package servlet;

import entity.Account;
import entity.Publication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account currentAccount = (Account) req.getSession().getAttribute("currentAccount");
        List<Publication> publications = currentAccount.publications();

        Map<String, Object> model = new HashMap<>();

        model.put("currentAccount", currentAccount);
        model.put("publications", publications);

        FreemarkerBuilder freemarkerBuilder = (FreemarkerBuilder) getServletContext().getAttribute("freemarker");
        freemarkerBuilder.render("profile.ftl", model, resp);

    }

}
