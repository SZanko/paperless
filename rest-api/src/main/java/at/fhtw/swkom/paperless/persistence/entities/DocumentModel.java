package at.fhtw.swkom.paperless.persistence.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "document")
public class DocumentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String author;
    private String created;
    private String content;
    @Nonnull
    private String fileNameBucket;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentModel model)) return false;

        return Objects.equals(id, model.id) && Objects.equals(title, model.title) && Objects.equals(author, model.author) && Objects.equals(created, model.created) && Objects.equals(content, model.content) && fileNameBucket.equals(model.fileNameBucket);
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? (hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode()) : getClass().hashCode();
    }
}
