package at.fhtw.swkom.paperless.mapper;

import at.fhtw.swkom.paperless.model.DocumentModel;
import at.fhtw.swkom.paperless.services.dto.Document;
import at.fhtw.swkom.paperless.services.dto.DocumentDtoBuilder;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class DocumentMapperTest {
    private final DocumentMapper documentMapper = new DocumentMapperImpl();

    @Test
    void convertEmptyDtoToModel(){
        Document dto = new Document();
        DocumentModel model = new DocumentModel();
        var result = documentMapper.toModel(dto);
        Assertions.assertThat(result).isEqualTo(model);
    }

    @Test
    void convertEmptyModelToDto(){
        Document dto = new Document();
        DocumentModel model = new DocumentModel();
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
