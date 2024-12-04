package entity;

import java.time.LocalDate;
import java.util.List;

public record Publication(long id, LocalDate date, String content, int commentsAmount, int likesAmount, long accountId, List<Image> images) {
}
