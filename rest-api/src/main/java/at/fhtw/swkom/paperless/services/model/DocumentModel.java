package at.fhtw.swkom.paperless.services.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy hibernateProxy ? (hibernateProxy.getHibernateLazyInitializer().getPersistentClass()) : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? (hibernateProxy.getHibernateLazyInitializer().getPersistentClass()) : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DocumentModel that = (DocumentModel) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? (hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode()) : getClass().hashCode();
    }
}
