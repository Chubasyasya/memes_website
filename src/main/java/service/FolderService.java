package service;

import dao.FolderDao;
import dao.ImageDao;
import entity.Account;
import entity.Folder;
import entity.Image;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class FolderService {
    private final FolderDao folderDao = FolderDao.getInstance();
    private final ImageDao imageDao = ImageDao.getInstance();
    public List<Folder> findByAccountId(long id) {
        return folderDao.findByAccountId(id);
    }


    public boolean addImage(long folderId, long imageId) {
        return folderDao.addImage(folderId, imageId);
    }
}
