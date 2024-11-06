package at.fhtw.swkom.paperless.controller;


import at.fhtw.swkom.paperless.services.DocumentService;
import at.fhtw.swkom.paperless.services.dto.Document;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-13T22:09:11.446025358Z[Etc/UTC]", comments = "Generator version: 7.10.0-SNAPSHOT")
@Controller
@CrossOrigin
@RequestMapping("${openapi.paperlessRESTServer.base-path:}")
@AllArgsConstructor(onConstructor = @__(@Inject))
public class ApiApiController implements ApiApi {

    private final NativeWebRequest request;

    private final DocumentService documentService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }


    @Override
    public ResponseEntity<Void> deleteDocument(Integer id) {
        final Optional<Document> found = documentService.findById(id);
        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        documentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Document> getDocument(Integer id) {
        final Optional<Document> found = documentService.findById(id);
        return found.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<Document>> getDocuments() {
        final List<Document> found = documentService.findAll();
        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(found);
    }

    @Override
    public ResponseEntity<Void> postDocument(String author, String title, MultipartFile file) {
        final Optional<Document> saved = documentService.create(author, title, file);
        if(saved.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Document>> searchDocumentContent() {
        Integer id = 1;
        final Optional<Document> found = documentService.findById(id);
        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ApiApi.super.searchDocumentContent();
    }

    @Override
    public ResponseEntity<Document> updateMetaData(Integer id, String title, String author, MultipartFile file) {
        final Optional<Document> found = documentService.findById(id);
        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //documentService.update();
        return ResponseEntity.ok(found.get());
    }
}
