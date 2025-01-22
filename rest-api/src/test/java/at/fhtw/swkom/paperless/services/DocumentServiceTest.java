package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.services.dto.Document;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Inject))
class DocumentServiceTest {

    private final DocumentService documentService;

    @Test
    void getIdFromDatabase(){
        var result = documentService.findById(1);
        Assertions.assertFalse(result.isPresent());
    }

}
