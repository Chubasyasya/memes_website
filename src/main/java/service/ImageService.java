package service;

import dao.ImageDao;
import entity.Image;
import jakarta.servlet.http.Part;
import util.ServicePathLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageService {
    private final ImageDao imageDao = ImageDao.getInstance();
    private final String uploadPath = ServicePathLoader.get("service.images-upload-path");


    public void saveImages(List<Part> reqParts, long publicationId) throws IOException {
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
