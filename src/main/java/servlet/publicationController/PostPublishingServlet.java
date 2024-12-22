package servlet.publicationController;

import entity.Account;
import entity.Image;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import service.AccountService;
import service.ImageService;
import service.PublicationService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/postPublishing")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class PostPublishingServlet extends HttpServlet {
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
        Account curAccount = (Account) req.getSession().getAttribute("currentAccount");
        String postText = new String(req.getPart("postText").getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        boolean hasImage = false;

        for (Part part : req.getParts()) {
            if (part.getName().equals("publication-image") && part.getSize() > 0) {
                hasImage = true;
                break;
            }
        }

        if (postText.isEmpty() && !hasImage) {
            resp.sendRedirect(getServletContext().getContextPath() + "/profile");
            return;
        }

        long id = publicationService.savePublication(curAccount.getId(), postText);


        for (Part part : req.getParts()) {
            if (part.getName().equals("publication-image") && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();

                imageService.saveImages(part, fileName, id);
            }
        }

        long accountId = ((Account) req.getSession().getAttribute("currentAccount")).getId();
        Account currentAccount = accountService.find(accountId);
        req.getSession().setAttribute("currentAccount", currentAccount);

        resp.sendRedirect(getServletContext().getContextPath() + "/profile");
    }
}


