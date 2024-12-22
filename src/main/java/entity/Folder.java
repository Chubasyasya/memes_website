package entity;

import java.time.LocalDate;
import java.util.List;

import java.time.LocalDate;

public class Folder {
    private long id;
    private String name;
    private String description;
    private int imageAmount;
    private LocalDate dateCreated;
    private long accountId;

    public Folder(long id, String name, String description, int imageAmount, LocalDate dateCreated, long accountId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageAmount = imageAmount;
        this.dateCreated = dateCreated;
        this.accountId = accountId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageAmount() {
        return imageAmount;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageAmount(int imageAmount) {
        this.imageAmount = imageAmount;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
