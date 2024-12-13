package service;

import dao.FavoriteDao;
import entity.Favorite;

public class FavoriteService {
    private  final FavoriteDao favoriteDao = FavoriteDao.getInstance();

    public void addToFavorite(long accountId, long publicationId){
        favoriteDao.save(new Favorite(accountId, publicationId));
    }
}
