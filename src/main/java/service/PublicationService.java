package service;

import dao.FavoriteDao;
import dao.ImageDao;
import dao.PublicationDao;
import entity.Account;
import entity.Image;
import entity.Publication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import util.ServicePathLoader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PublicationService {
    private final PublicationDao publicationDao = PublicationDao.getInstance();
    public long savePublication(List<Part> reqParts, HttpSession session) {
        if (reqParts == null || session == null) {
            throw new IllegalArgumentException("Request parts or session cannot be null");
        }

        Account curAccount = (Account) session.getAttribute("currentAccount");

        try {
            String postText = extractPostText(reqParts);
           return publicationDao.saveAndGetId(
                    new Publication(-1, LocalDate.now(), postText, 0, 0, curAccount.id(), null, false)
            );

        } catch (IOException e) {
            throw new RuntimeException("Error while saving publication", e);
        }
    }

    private String extractPostText(List<Part> reqParts) throws IOException {
        for (Part part : reqParts) {
            if (part.getName().equals("postText")) {
                return new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        throw new IllegalArgumentException("Post text is missing in the request parts");
    }


    public List<Publication> getPublications(int offset, int limit) {
        return publicationDao.find(offset, limit);
    }
    public void updateLikes(long id){
        publicationDao.updateLikes(id);
    }
}
