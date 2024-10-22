package at.fhtw.swkom.paperless.services.dto;

import java.util.Optional;

public class DocumentDtoBuilder {
    private Optional<Integer> id = Optional.empty();
    private String title;
    private Optional<String> author = Optional.empty();
    private Optional<String> created = Optional.empty();
    private Optional<String> content = Optional.empty();
    private Optional<String> path = Optional.empty();

    public DocumentDtoBuilder() {
        // No-args constructor
    }

    public static DocumentDtoBuilder builder() {
        return new DocumentDtoBuilder();
    }

    public DocumentDtoBuilder withId(Integer id) {
        this.id = Optional.ofNullable(id);
        return this;
    }

    public DocumentDtoBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public DocumentDtoBuilder withAuthor(String author) {
        this.author = Optional.ofNullable(author);
        return this;
    }

    public DocumentDtoBuilder withCreated(String created) {
        this.created = Optional.ofNullable(created);
        return this;
    }

    public DocumentDtoBuilder withContent(String content) {
        this.content = Optional.ofNullable(content);
        return this;
    }

    public DocumentDtoBuilder withPath(String path) {
        this.path = Optional.ofNullable(path);
        return this;
    }

    public Document build() {
        Document document = new Document(title);  // Required field constructor
        document.setId(this.id);
        document.setAuthor(this.author);
        document.setCreated(this.created);
        document.setContent(this.content);
        document.setPath(this.path);
        return document;
    }
}