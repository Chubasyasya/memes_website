package entity;

import java.time.LocalDate;

public class Comment {
    private long id;
    private String content;
    private LocalDate date;
    private int likesAmount;
    private int dislikesAmount;
    private boolean changed;
    private long publicationId;
    private long accountId;
    private String creatorName;

    public Comment(long id, String content, LocalDate date, int likesAmount, int dislikesAmount, boolean changed, long publicationId, long accountId, String creatorName) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.likesAmount = likesAmount;
        this.dislikesAmount = dislikesAmount;
        this.changed = changed;
        this.publicationId = publicationId;
        this.accountId = accountId;
        this.creatorName = creatorName;
    }

    public Comment(String content, LocalDate date, int likesAmount, int dislikesAmount, boolean changed, long publicationId, long accountId, String creatorName) {
        this.content = content;
        this.date = date;
        this.likesAmount = likesAmount;
        this.dislikesAmount = dislikesAmount;
        this.changed = changed;
        this.publicationId = publicationId;
        this.accountId = accountId;
        this.creatorName = creatorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getLikesAmount() {
        return likesAmount;
    }

    public int getDislikesAmount() {
        return dislikesAmount;
    }

    public boolean isChanged() {
        return changed;
    }

    public long getPublicationId() {
        return publicationId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikesAmount(int likesAmount) {
        this.likesAmount = likesAmount;
    }

    public void setDislikesAmount(int dislikesAmount) {
        this.dislikesAmount = dislikesAmount;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void setPublicationId(long publicationId) {
        this.publicationId = publicationId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}

