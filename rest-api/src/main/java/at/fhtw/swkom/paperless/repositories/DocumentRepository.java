package at.fhtw.swkom.paperless.repositories;

import at.fhtw.swkom.paperless.model.DocumentModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends PagingAndSortingRepository<DocumentModel, Integer> {
}
