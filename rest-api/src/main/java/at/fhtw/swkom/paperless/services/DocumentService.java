package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.services.mappers.DocumentMapper;
import at.fhtw.swkom.paperless.services.dto.Document;
import at.fhtw.swkom.paperless.persistence.entities.DocumentModel;
import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import io.minio.errors.*;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log
@AllArgsConstructor(onConstructor = @__(@Inject))
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper mapper;
    private final MinioService minioService;
    private final MessageSender messageSender;


    public Optional<Document> findById(@Nullable Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        final Optional<DocumentModel> model = documentRepository.findById(id);
        return model.map(mapper::toDto);
    }

    public void deleteById(@NotNull Integer id) {
        final Optional<Document> model = this.findById(id);
        if(model.isEmpty()) {
            log.severe("Document was deleted before calling deleteById");
            return;
        }
        if(model.get().getMinioFilePath().isEmpty()) {
            log.warning("No File Uploaded");
            documentRepository.deleteById(id);
            return;
        }
        minioService.deleteFile(model.get().getMinioFilePath().get());
        documentRepository.deleteById(id);
    }

    public List<Document> findAll() {
        return mapper.toDto((List<DocumentModel>) documentRepository.findAll());
    }


    public Optional<Document> create(String author, String title, MultipartFile file) {
        final String fileNameBucket;
        try {
            fileNameBucket = minioService.uploadFile(file);
            messageSender.sendToOCRWorker(fileNameBucket);
        } catch (IOException | ServerException | InsufficientDataException | ErrorResponseException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            log.severe(e.getMessage());
            log.severe("Failed to upload document to minio");
            return Optional.empty();
        }
        final DocumentModel toBeSaved = new DocumentModel(null, title, author, LocalDateTime.now().toString(), null, fileNameBucket);
        documentRepository.save(toBeSaved);
        final Optional<DocumentModel> model = documentRepository.findById(toBeSaved.getId());
        return model.map(mapper::toDto);
    }


    public Optional<Document> updateDocument(Integer id, String author, String title) {
        // Check if document exists
        Optional<DocumentModel> existingDocument = documentRepository.findById(id);
        if (existingDocument.isEmpty()) {
            log.severe("Document not found for updating with id: " + id);
            return Optional.empty();
        }

        // Update metadata
        DocumentModel documentToUpdate = existingDocument.get();
        documentToUpdate.setTitle(title);
        documentToUpdate.setAuthor(author);

        // Save first
        documentRepository.save(documentToUpdate);

        // Then fetch the updated document from DB (like in create method)
        final Optional<DocumentModel> updatedModel = documentRepository.findById(id);
        return updatedModel.map(mapper::toDto);
    }

}
