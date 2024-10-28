package at.fhtw.swkom.paperless.services.repositories;

import at.fhtw.swkom.paperless.services.model.DocumentModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<DocumentModel, Integer>, CrudRepository<DocumentModel, Integer> {
}
