package com.asg.platform.es.repository;

import com.asg.platform.es.mapping.SecondaryOrganism;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganismRepository extends PagingAndSortingRepository<SecondaryOrganism, String> {

    Page<SecondaryOrganism> findAll(Pageable pageable);

    SecondaryOrganism save(SecondaryOrganism secondaryOrganism);

    SecondaryOrganism findBioSampleByAccession(String accession);

}
