package at.fhtw.swkom.paperless.persistence.repositories;

import at.fhtw.swkom.paperless.persistence.entities.DocumentModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<DocumentModel, Integer>, CrudRepository<DocumentModel, Integer> {
    @Transactional
    @Modifying
    @Query("update DocumentModel d set d.content = ?1 where d.content is null and d.fileNameBucket like ?2")
    int updateContentByContentNullAndFileNameBucketLike(@NonNull String content, @NonNull String fileNameBucket);

}
