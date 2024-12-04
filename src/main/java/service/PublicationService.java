package service;

import dao.ImageDao;
import dao.PublicationDao;
import entity.Account;
import entity.Image;
import entity.Publication;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import util.ServicePathLoader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class PublicationService {
    private final PublicationDao publicationDao = PublicationDao.getInstance();
    private final ImageDao imageDao = ImageDao.getInstance();
    private static final PublicationService INSTANCE = new PublicationService();
    private final String uploadPath = ServicePathLoader.get("service.images-upload-path");

    private PublicationService(){}
    public static PublicationService getInstance(){
        return INSTANCE;
    }

    public void savePublication(List<Part> reqParts, HttpSession session) {
        if (reqParts == null || session == null) {
            throw new IllegalArgumentException("Request parts or session cannot be null");
        }

        Account curAccount = (Account) session.getAttribute("currentAccount");

        try {
            String postText = extractPostText(reqParts);
            Publication addedPublication = publicationDao.save(
                    new Publication(-1, LocalDate.now(), postText, 0, 0, curAccount.id(), null)
            );

            saveImages(reqParts, uploadPath, addedPublication.id());
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

    private void saveImages(List<Part> reqParts, String uploadPath, long publicationId) throws IOException {
        for (Part part : reqParts) {
            if (part.getName().equals("postImage") && part.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();
                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath);
                imageDao.save(new Image(-1, filePath, fileName, publicationId));
            }
        }
    }

}
