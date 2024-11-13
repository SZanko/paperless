package at.fhtw.swkom.paperless.persistence.repositories;

import at.fhtw.swkom.paperless.persistence.entities.DocumentModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<DocumentModel, Integer>, CrudRepository<DocumentModel, Integer> {
}
