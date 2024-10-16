package at.fhtw.swkom.paperless.controller;


import at.fhtw.swkom.paperless.services.dto.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
@RequestMapping("${openapi.paperlessRESTServer.base-path:}")
public class ApiApiController implements ApiApi {

    private final NativeWebRequest request;

    @Autowired
    public ApiApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }


    @Override
    public ResponseEntity<Void> deleteDocument(Integer id) {
        return ApiApi.super.deleteDocument(id);
    }

    @Override
    public ResponseEntity<Document> getDocument(Integer id) {
        return ApiApi.super.getDocument(id);
    }

    @Override
    public ResponseEntity<List<Document>> getDocuments() {
        return ApiApi.super.getDocuments();
    }

    @Override
    public ResponseEntity<Void> postDocument(String author, String title, MultipartFile file) {
        return ApiApi.super.postDocument(author, title, file);
    }

    @Override
    public ResponseEntity<List<Document>> searchDocumentContent() {
        return ApiApi.super.searchDocumentContent();
    }

    @Override
    public ResponseEntity<Document> updateMetaData(Integer documentId, String author, String title) {
        return ApiApi.super.updateMetaData(documentId, author, title);
    }
}
