package servlet.accountController;

import entity.Account;
import entity.Publication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.PublicationService;
import servlet.FreemarkerConfigSingleton;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private PublicationService publicationService;

    @Override
    public void init() throws ServletException {
        super.init();
        publicationService = (PublicationService) getServletContext().getAttribute("publicationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account currentAccount = (Account) req.getSession().getAttribute("currentAccount");
//        List<Publication> publications = publicationService.findByAccountId(currentAccount.getId());

        Map<String, Object> model = new HashMap<>();

        model.put("currentAccount", currentAccount);
        model.put("ownerId", currentAccount.getId());
        System.out.println(currentAccount.getId());
//        model.put("publications", publications);

        FreemarkerConfigSingleton.render("profile.ftl", model, req, resp);

    }

}
