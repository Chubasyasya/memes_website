package entity;

import java.time.LocalDate;
import java.util.List;

public class Publication {
    private long id;
    private LocalDate date;
    private String content;
    private int commentsAmount;
    private int likesAmount;
    private long accountId;
    private List<Image> images;
    private boolean liked;

    public Publication(long id, LocalDate date, String content, int commentsAmount, int likesAmount, long accountId, List<Image> images, boolean liked) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.commentsAmount = commentsAmount;
        this.likesAmount = likesAmount;
        this.accountId = accountId;
        this.images = images;
        this.liked = liked;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public int getCommentsAmount() {
        return commentsAmount;
    }

    public int getLikesAmount() {
        return likesAmount;
    }

    public long getAccountId() {
        return accountId;
    }

    public List<Image> getImages() {
        return images;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentsAmount(int commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    public void setLikesAmount(int likesAmount) {
        this.likesAmount = likesAmount;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}

