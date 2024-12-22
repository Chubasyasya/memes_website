package properties;

public enum ServletContextAttributeProperties {
    FREEMARKER("freemarker"),
    COMMENT_SERVICE("commentService"),
    ACCOUNT_SERVICE("accountService"),
    FAVORITE_SERVICE("favoriteService"),
    IMAGE_SERVICE("imageService"),
    PUBLICATION_SERVICE("publicationService"),
    FOLDER_SERVICE("folderService");

    private final String key;

    ServletContextAttributeProperties(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
