package at.fhtw.swkom.paperless.mapper;

import at.fhtw.swkom.paperless.services.mapper.DocumentMapper;
import at.fhtw.swkom.paperless.services.mapper.DocumentMapperImpl;
import at.fhtw.swkom.paperless.services.model.DocumentModel;
import at.fhtw.swkom.paperless.services.dto.Document;
import at.fhtw.swkom.paperless.services.dto.DocumentDtoBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class DocumentMapperTest {
    private final DocumentMapper documentMapper = new DocumentMapperImpl();

    @Test
    void convertEmptyDtoToModel(){
        Document dto = new Document();
        dto.setMinioFilePath(Optional.of("Path"));
        DocumentModel model = new DocumentModel();
        model.setFileNameBucket("Path");
        var result = documentMapper.toModel(dto);
        Assertions.assertThat(result).isEqualTo(model);
    }

    @Test
    void convertEmptyModelToDto(){
        Document dto = new Document();
        dto.setMinioFilePath(Optional.of("Path"));
        DocumentModel model = new DocumentModel("Path");
        var result = documentMapper.toDto(model);
        Assertions.assertThat(result).isEqualTo(dto);
    }

    @Test
    void convertDtoToModel() {
        Document dto = DocumentDtoBuilder.builder()
                .withId(null)
                .withTitle("title")
                .withContent("content")
                .withPath("path")
                .build();
    }
}
