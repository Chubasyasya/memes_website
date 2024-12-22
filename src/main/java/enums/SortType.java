package enums;

public enum SortType {
    DATE_DESC("date-desc", "date", "DESC"),
    DATE_ASC("date-asc", "date", "ASC"),
    LIKES_DESC("likes_amount-desc", "likes_amount", "DESC"),
    LIKES_ASC("likes_amount-asc", "likes_amount", "ASC");

    private final String value;
    private final String field;
    private final String direction;

    SortType(String value, String field, String direction) {
        this.value = value;
        this.field = field;
        this.direction = direction;
    }

    public String getValue() {
        return value;
    }

    public String getField() {
        return field;
    }

    public String getDirection() {
        return direction;
    }

    public static SortType fromValue(String value) {
        for (SortType sortType : values()) {
            System.out.println(sortType.getValue() + " " + value + " = " + sortType.getValue().equalsIgnoreCase(value));
            if (sortType.getValue().equalsIgnoreCase(value)) {
                return sortType;
            }
        }
        throw new IllegalArgumentException("Unknown sort type: " + value);
    }
}

