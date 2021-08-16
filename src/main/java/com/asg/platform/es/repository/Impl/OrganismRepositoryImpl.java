package com.asg.platform.es.repository.Impl;

import com.asg.platform.es.mapping.SecondaryOrganism;
import com.asg.platform.es.repository.OrganismRepository;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Repository
public abstract class OrganismRepositoryImpl implements OrganismRepository {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Override
    public SecondaryOrganism save(SecondaryOrganism secondaryOrganism) {
        IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(SecondaryOrganism.class);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(UUID.randomUUID().toString())
                .withObject(secondaryOrganism)
                .build();
        String documentId = elasticsearchOperations.index(indexQuery, indexCoordinates);
        return secondaryOrganism;
    }

    @Override
    public SecondaryOrganism findBioSampleByAccession(String accession) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("accession",accession).operator(Operator.AND))
                .build();
        SearchHits<SecondaryOrganism> bioSample = elasticsearchOperations
                .search(searchQuery, SecondaryOrganism.class, IndexCoordinates.of("organisms"));

        if(bioSample.getTotalHits() > 0) {
            return bioSample.getSearchHit(0).getContent();
        }
        else {
            return new SecondaryOrganism();
        }
    }

}
