package at.fhtw.swkom.paperless.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.java.Log;

import java.util.Optional;

@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class DocumentModel {
    @Id
    private Integer id;
    private String title;
    private String author;
    private String created;
    private String content;
    @Nonnull
    private String path;
}
