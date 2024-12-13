package entity;

import java.time.LocalDate;
import java.util.List;

public record Account(long  id, String name, String email, String password, String phoneNumber, String status, LocalDate birthday, List<Publication> publications, List<Folder> folders) {
}
