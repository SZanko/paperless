package at.fhtw.swkom.paperless.services.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Document
 */

@JsonTypeName("document")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-16T14:45:41.998108992+02:00[Europe/Vienna]", comments = "Generator version: 7.6.0")
public class Document {

  private Optional<Integer> id = Optional.empty();

  private String title;

  private Optional<String> author = Optional.empty();

  private Optional<String> created = Optional.empty();

  private Optional<String> content = Optional.empty();

  private Optional<String> path = Optional.empty();

  public Document() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Document(String title) {
    this.title = title;
  }

  public Document id(Integer id) {
    this.id = Optional.of(id);
    return this;
  }

  /**
   * The id of the document
   * @return id
  */
  
  @Schema(name = "id", description = "The id of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Optional<Integer> getId() {
    return id;
  }

  public void setId(Optional<Integer> id) {
    this.id = id;
  }

  public Document title(String title) {
    this.title = title;
    return this;
  }

  /**
   * The title of the document
   * @return title
  */
  @NotNull 
  @Schema(name = "title", description = "The title of the document", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Document author(String author) {
    this.author = Optional.of(author);
    return this;
  }

  /**
   * The author of the document's contents
   * @return author
  */
  
  @Schema(name = "author", description = "The author of the document's contents", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public Optional<String> getAuthor() {
    return author;
  }

  public void setAuthor(Optional<String> author) {
    this.author = author;
  }

  public Document created(String created) {
    this.created = Optional.of(created);
    return this;
  }

  /**
   * Get created
   * @return created
  */
  
  @Schema(name = "created", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("created")
  public Optional<String> getCreated() {
    return created;
  }

  public void setCreated(Optional<String> created) {
    this.created = created;
  }

  public Document content(String content) {
    this.content = Optional.of(content);
    return this;
  }

  /**
   * Content of the document
   * @return content
  */
  
  @Schema(name = "content", description = "Content of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public Optional<String> getContent() {
    return content;
  }

  public void setContent(Optional<String> content) {
    this.content = content;
  }

  public Document path(String path) {
    this.path = Optional.of(path);
    return this;
  }

  /**
   * File Path
   * @return path
  */
  
  @Schema(name = "path", description = "File Path", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("path")
  public Optional<String> getPath() {
    return path;
  }

  public void setPath(Optional<String> path) {
    this.path = path;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Document document = (Document) o;
    return Objects.equals(this.id, document.id) &&
        Objects.equals(this.title, document.title) &&
        Objects.equals(this.author, document.author) &&
        Objects.equals(this.created, document.created) &&
        Objects.equals(this.content, document.content) &&
        Objects.equals(this.path, document.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, created, content, path);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Document {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    created: ").append(toIndentedString(created)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

