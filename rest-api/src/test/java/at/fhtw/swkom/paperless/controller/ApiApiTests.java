package at.fhtw.swkom.paperless.controller;

import at.fhtw.swkom.paperless.services.DocumentService;
import at.fhtw.swkom.paperless.services.dto.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiApiControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private ApiApiController apiController;

    private Document testDocument;
    private MockMultipartFile testFile;

    @BeforeEach
    void setUp() {
        testDocument = new Document()
                .id(1)
                .title("Test Document")
                .author("Test Author")
                .content("Test Content")
                .created("2024-01-22")
                .minioFilePath("/test/path");

        testFile = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "test content".getBytes()
        );
    }

    @Test
    void deleteDocument_Success() {
        // Arrange
        Integer documentId = 1;
        when(documentService.findById(documentId)).thenReturn(Optional.of(testDocument));
        doNothing().when(documentService).deleteById(documentId);

        // Act
        ResponseEntity<Void> response = apiController.deleteDocument(documentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(documentService).deleteById(documentId);
    }

    @Test
    void deleteDocument_NotFound() {
        // Arrange
        Integer documentId = 999;
        when(documentService.findById(documentId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = apiController.deleteDocument(documentId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(documentService, never()).deleteById(documentId);
    }

    @Test
    void getDocument_Success() {
        // Arrange
        Integer documentId = 1;
        when(documentService.findById(documentId)).thenReturn(Optional.of(testDocument));

        // Act
        ResponseEntity<Document> response = apiController.getDocument(documentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        //assertEquals(documentId, response.getBody().getId());
    }

    @Test
    void getDocument_NotFound() {
        // Arrange
        Integer documentId = 999;
        when(documentService.findById(documentId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Document> response = apiController.getDocument(documentId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getDocuments_Success() {
        // Arrange
        List<Document> documents = Arrays.asList(
                testDocument,
                new Document()
                        .id(2)
                        .title("Test Document 2")
                        .author("Test Author 2")
        );
        when(documentService.findAll()).thenReturn(documents);

        // Act
        ResponseEntity<List<Document>> response = apiController.getDocuments();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void postDocument_Success() {
        // Arrange
        String author = "Test Author";
        String title = "Test Title";
        when(documentService.create(eq(author), eq(title), any(MockMultipartFile.class)))
                .thenReturn(Optional.ofNullable(testDocument));

        // Act
        ResponseEntity<Void> response = apiController.postDocument(author, title, testFile);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(documentService).create(author, title, testFile);
    }



    @Test
    void updateMetaData_DocumentNotFound() {
        // Arrange
        Integer documentId = 999;
        String newTitle = "Updated Title";
        when(documentService.findById(documentId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Document> response = apiController.updateMetaData(
                documentId,
                newTitle,
                null,
                null
        );

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(documentService, never()).updateDocument(any(), any(), any());
    }
}

