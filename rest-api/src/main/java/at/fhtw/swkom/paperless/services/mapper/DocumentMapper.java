package at.fhtw.swkom.paperless.services.mapper;

import at.fhtw.swkom.paperless.services.model.DocumentModel;
import at.fhtw.swkom.paperless.services.dto.Document;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = false))
@Component
public interface DocumentMapper extends OptionalMapStructHelper {
    @Mapping(source = "id", target = "id", qualifiedByName = "integerToOptional")
    @Mapping(source = "author", target = "author", qualifiedByName = "stringToOptional")
    @Mapping(source = "created", target = "created", qualifiedByName = "stringToOptional")
    @Mapping(source = "content", target = "content", qualifiedByName = "stringToOptional")
    @Mapping(source = "fileNameBucket", target = "path", qualifiedByName = "stringToOptional")
    Document toDto(DocumentModel documentModel);

    default List<Document> toDto(List<DocumentModel> documentModels) {
        return documentModels.stream().map(this::toDto).toList();
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "optionalToInteger")
    @Mapping(source = "author", target = "author", qualifiedByName = "optionalToString")
    @Mapping(source = "created", target = "created", qualifiedByName = "optionalToString")
    @Mapping(source = "content", target = "content", qualifiedByName = "optionalToString")
    @Mapping(source = "path", target = "fileNameBucket", qualifiedByName = "optionalToString")
    DocumentModel toModel(Document document);

    default List<DocumentModel> toModel(List<Document> documents) {
        return documents.stream().map(this::toModel).toList();
    }
}
