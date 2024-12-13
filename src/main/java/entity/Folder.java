package entity;

import java.time.LocalDate;
import java.util.List;

public record Folder (long id, String name, String description, int imageAmount, LocalDate dateCreated, long accountId, List<Image> images){
}
