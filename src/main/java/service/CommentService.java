package service;

import dao.CommentDao;
import entity.Comment;
import util.StringToLongUtil;

import java.util.List;

public class CommentService {
    private CommentDao commentDao = CommentDao.getInstance();

    public List<Comment> findByPublicationId(long publicationId) {
        return commentDao.findByPublicationId(publicationId);
    }

    public void createComment(Comment comment) {
        commentDao.save(comment);
    }

    public Comment findById(long commentId) {
        return commentDao.find(commentId);
    }

    public void delete(long commentId) {
        commentDao.delete(commentId);
    }
}
