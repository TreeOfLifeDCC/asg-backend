package com.asg.platform.es.repository;

import com.asg.platform.es.mapping.RootOrganism;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RootOrganismRepository extends PagingAndSortingRepository<RootOrganism, String> {

    Page<RootOrganism> findAll(Pageable pageable);

    RootOrganism findRootOrganismByOrganism(String organism);
}
