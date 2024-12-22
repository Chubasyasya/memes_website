package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Image {
    private long id;
    private String path;
    private String name;
    private String description;
    private long publicationId;
    private LocalDate dateCreated;
    private String dateAsString;

    public Image(long id, String path, String name, String description, long publicationId, LocalDate dateCreated) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.description = description;
        this.publicationId = publicationId;
        this.dateCreated = dateCreated;
    }

    public String getDateAsString() {
        if (dateCreated != null) {
            return dateCreated.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public long getPublicationId() {
        return publicationId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPublicationId(long publicationId) {
        this.publicationId = publicationId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateAsString(String dateAsString) {
        this.dateAsString = dateAsString;
    }
}

