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
    private final ImageDao imageDao = ImageDao.getInstance();

    public Folder getFolderByIdAndAccount(long folderId, Account account) {
        return account.folders().stream()
                .filter(folder -> folder.id() == folderId)
                .findFirst()
                .map(folder -> {
                    // Загружаем изображения, если их нет
                    if (folder.images().isEmpty()) {
                        folder.images().addAll(imageDao.findByFolderId(folder.id()));
                    }
                    return folder;
                })
                .orElse(null);
    }
}
