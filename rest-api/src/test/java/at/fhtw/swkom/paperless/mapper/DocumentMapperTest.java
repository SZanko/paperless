package at.fhtw.swkom.paperless.mapper;

import at.fhtw.swkom.paperless.services.mappers.DocumentMapper;
import at.fhtw.swkom.paperless.services.mappers.DocumentMapperImpl;
import at.fhtw.swkom.paperless.persistence.repositories.model.DocumentModel;
import at.fhtw.swkom.paperless.services.dto.Document;
import at.fhtw.swkom.paperless.services.dto.DocumentDtoBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class DocumentMapperTest {
    private final DocumentMapper documentMapper = new DocumentMapperImpl();

    @Test
    void convertEmptyDtoToModel(){
        final Document dto = new Document();
        dto.setMinioFilePath(Optional.of("Path"));
        final DocumentModel model = new DocumentModel();
        model.setFileNameBucket("Path");
        final var result = documentMapper.toModel(dto);
        Assertions.assertThat(result).isEqualTo(model);
    }

    @Test
    void convertEmptyModelToDto(){
        final Document dto = new Document();
        dto.setMinioFilePath(Optional.of("Path"));
        final DocumentModel model = new DocumentModel("Path");
        final var result = documentMapper.toDto(model);
        Assertions.assertThat(result).isEqualTo(dto);
    }

    @Test
    void convertDtoToModel() {
        final Document dto = DocumentDtoBuilder.builder()
                .withId(null)
                .withTitle("title")
                .withContent("content")
                .withMinioPath("path")
                .build();
        final var result = documentMapper.toModel(dto);
        Assertions.assertThat(result.getId()).isNull();
        Assertions.assertThat(result.getTitle()).isEqualTo("title");
        Assertions.assertThat(result.getContent()).isEqualTo("content");
        Assertions.assertThat(result.getFileNameBucket()).isEqualTo("path");
    }

    @Test
    void convertModelToDto() {
        final DocumentModel model = new DocumentModel(null, "title", "author", "created", "content", "path");
        final var result = documentMapper.toDto(model);
        Assertions.assertThat(result.getId()).isEmpty();
        Assertions.assertThat(result.getTitle()).isEqualTo("title");
        Assertions.assertThat(result.getContent()).isNotEmpty();
        Assertions.assertThat(result.getContent().get()).contains("content");
        Assertions.assertThat(result.getAuthor()).isNotEmpty();
        Assertions.assertThat(result.getAuthor().get()).contains("author");
        Assertions.assertThat(result.getCreated()).isNotEmpty();
        Assertions.assertThat(result.getCreated().get()).contains("created");
        Assertions.assertThat(result.getMinioFilePath()).isNotEmpty();
        Assertions.assertThat(result.getMinioFilePath().get()).contains("path");
    }
}
