package service;

import dao.CommentDao;
import entity.Comment;

import java.util.List;

public class CommentService {
    private CommentDao commentDao = CommentDao.getInstance();

    public List<Comment> findByPublicationId(long publicationId) {
        return commentDao.findByPublicationId(publicationId);
    }
}
