package service;

import dao.ImageDao;
import entity.Image;
import jakarta.servlet.http.Part;
import util.ServicePathLoader;
import util.StringToLongUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ImageService {
    private final ImageDao imageDao = ImageDao.getInstance();
    private final String uploadPath = ServicePathLoader.get("service.images-upload-path");

    public void saveImages(Part part, String fileName, long publicationId) throws IOException {
        String filePath = uploadPath + File.separator + fileName;
        part.write(filePath);

        imageDao.save(new Image(-1, filePath, fileName, "image", publicationId, LocalDate.now()));
    }
    public List<Image> findImagesByFolderId(long folderId, int page, int limit){
        int offset = (page - 1) * limit;
        return imageDao.findByFolderId(folderId, offset, limit);
    }

    public List<Image> findImagesByPublicationId(String publicationId) {
        long id = StringToLongUtil.getLongParameter(publicationId);
        return imageDao.findByPublicationId(id);
    }
}
