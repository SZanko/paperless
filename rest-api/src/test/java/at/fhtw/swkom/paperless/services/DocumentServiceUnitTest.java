package at.fhtw.swkom.paperless.services;


import at.fhtw.swkom.paperless.persistence.entities.DocumentModel;
import at.fhtw.swkom.paperless.persistence.repositories.DocumentRepository;
import at.fhtw.swkom.paperless.services.dto.Document;
import at.fhtw.swkom.paperless.services.mappers.DocumentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceUnitTest {


    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private MinioService minioService;

    @Mock
    private MessageSender messageSender;

    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        documentService = new DocumentService(
                documentRepository,
                documentMapper,
                minioService,
                messageSender
        );
    }

    @Test
    void findById_IdExists(){
        Integer testId = 1;
        DocumentModel documentModel = new DocumentModel();
        Document expectedDocument = new Document();

        when(documentRepository.findById(testId)).thenReturn(Optional.of(documentModel));
        when(documentMapper.toDto(documentModel)).thenReturn(expectedDocument);

        Optional<Document> result = documentService.findById(testId);

        assertTrue(result.isPresent());
        assertEquals(expectedDocument, result.get());
        verify(documentRepository).findById(testId);
    }

    @Test
    void findById_whenIdIsNull() {
        Optional<Document> result = documentService.findById(null);

        assertTrue(result.isEmpty());
        verify(documentRepository, never()).findById(any());
    }

    @Test
    void findById_whenIdNotExists() {
        Integer testId = 999;
        when(documentRepository.findById(testId)).thenReturn(Optional.empty());

        Optional<Document> result = documentService.findById(testId);

        assertTrue(result.isEmpty());
        verify(documentRepository).findById(testId);
    }


    @Test
    void updateDocument_whenDocumentExists_shouldUpdateMetadata() {
        Integer testId = 1;
        String newAuthor = "New Author";
        String newTitle = "New Title";

        DocumentModel existingDocument = new DocumentModel();
        existingDocument.setId(testId);

        Document expectedDocument = new Document();

        when(documentRepository.findById(testId)).thenReturn(Optional.of(existingDocument));
        when(documentRepository.findById(testId)).thenReturn(Optional.of(existingDocument));
        when(documentMapper.toDto(existingDocument)).thenReturn(expectedDocument);

        Optional<Document> result = documentService.updateDocument(testId, newAuthor, newTitle);

        assertTrue(result.isPresent());
        verify(documentRepository).save(existingDocument);
        assertEquals(newAuthor, existingDocument.getAuthor());
        assertEquals(newTitle, existingDocument.getTitle());
    }

    @Test
    void updateDocument_whenDocumentNotExists_shouldReturnEmptyOptional() {
        Integer testId = 999;
        String newAuthor = "New Author";
        String newTitle = "New Title";

        when(documentRepository.findById(testId)).thenReturn(Optional.empty());

        Optional<Document> result = documentService.updateDocument(testId, newAuthor, newTitle);

        assertTrue(result.isEmpty());
        verify(documentRepository, never()).save(any());
    }
}

