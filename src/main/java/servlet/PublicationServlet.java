package servlet;

import entity.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import jakarta.websocket.Session;
import service.AccountService;
import service.ImageService;
import service.PublicationService;

import java.io.IOException;
import java.util.List;

@WebServlet("/publication")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class PublicationServlet extends HttpServlet {
    private AccountService accountService;
    private ImageService imageService;
    private PublicationService publicationService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
        publicationService = (PublicationService) getServletContext().getAttribute("publicationService");
        imageService = (ImageService) getServletContext().getAttribute("imageService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = publicationService.savePublication((List<Part>) req.getParts(), req.getSession());
        imageService.saveImages((List<Part>) req.getParts(), id);

        long accountId = ((Account) req.getSession().getAttribute("currentAccount")).id();
        Account currentAccount = accountService.find(accountId);
        req.getSession().setAttribute("currentAccount", currentAccount);

        resp.sendRedirect(getServletContext().getContextPath() + "/profile");
    }
}


