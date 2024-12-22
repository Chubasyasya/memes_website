package entity;

public class Favorite {
    private long accountId;
    private long publicationId;

    public Favorite(long accountId, long publicationId) {
        this.accountId = accountId;
        this.publicationId = publicationId;
    }

    public long getAccountId() {
        return accountId;
    }

    public long getPublicationId() {
        return publicationId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setPublicationId(long publicationId) {
        this.publicationId = publicationId;
    }
}

