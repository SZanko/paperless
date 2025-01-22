package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.persistence.entities.DocumentModel;
import at.fhtw.swkom.paperless.services.dto.Document;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Inject))
public class ElasticSearchService {
    private final ElasticsearchClient elasticsearchClient;

    public List<Document> searchDocuments(String searchText){
        try {
            SearchResponse<Document> searchResponse = elasticsearchClient.search(s -> s
                    .index("ocr")
                    .query(q -> q
                            .match(t -> t
                                    .field("text")
                                    .query(searchText)
                            )), Document.class);

            log.info("Raw Elasticsearch response: " + searchResponse);

            return searchResponse.hits().hits().stream()
                    .map(hit -> {
                        Document document = hit.source();
                        document.setId(Optional.of(Integer.parseInt(hit.id())));
                        document.setContent(Optional.of(hit.source().getContent().orElse("")));
                        return document;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error searching documents: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
