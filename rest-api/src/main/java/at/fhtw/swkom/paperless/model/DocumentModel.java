package at.fhtw.swkom.paperless.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.Optional;

@Data
@ToString
@NoArgsConstructor
public class DocumentModel {
    @Id
    private Integer id;
    @Nonnull
    private String title;
    private String author;
    private String created;
    private String content;
    private String path;
}
