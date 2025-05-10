package service;

import dao.FavoriteDao;
import entity.Favorite;

public class FavoriteService {
    private  final FavoriteDao favoriteDao = FavoriteDao.getInstance();

    public void addToFavorite(long accountId, long publicationId){
        favoriteDao.save(new Favorite(accountId, publicationId));
    }

    public void deleteFromFavorite(long accountId, long publicationId) {
        favoriteDao.delete(accountId, publicationId);
    }

    public boolean get(long id, long publicationId) {
        return favoriteDao.findByAccountIdPublicationId(id, publicationId);
    }
}
