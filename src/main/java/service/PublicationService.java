package service;

import dao.PublicationDao;
import entity.Publication;
import enums.SortType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PublicationService {
    private final PublicationDao publicationDao = PublicationDao.getInstance();
    public long savePublication(long accountId, String postText) {

       return publicationDao.saveAndGetId(
                new Publication(-1, LocalDate.now(), postText, 0, 0, accountId, null, false)
        );


    }


    public List<Publication> getPublications(int offset, int limit, SortType sortType) {
        return publicationDao.findAll(offset, limit, sortType);
    }
    public void addLike(long id){
        publicationDao.addLike(id);
    }
    public void deleteLike(long id){
        publicationDao.deleteLike(id);
    }

    public List<Publication> findByAccountId(long id, int offset, int limit) {
        return publicationDao.findByAccountId(id, offset, limit);
    }

    public Publication findById(long id) {
        return publicationDao.find(id);
    }

    public List<Publication> getPublicationsByMask(String mask, int offset, int limit, SortType sortType) {
        List<Publication> publications = getPublications(offset, limit, sortType);
        if (mask == null || mask.trim().isEmpty()) {
            return publications;
        }

        String lowerMask = mask.toLowerCase();

        return publications.stream()
                .filter(pub -> pub.getContent().toLowerCase().contains(lowerMask))
                .collect(Collectors.toList());
    }

    public int getLikeCount(long publicationId) {
        Publication publication = publicationDao.find(publicationId);
        return publication.getLikesAmount();
    }

    public Publication generateRandomPublication() {
        return publicationDao.getRandom();
    }
}
