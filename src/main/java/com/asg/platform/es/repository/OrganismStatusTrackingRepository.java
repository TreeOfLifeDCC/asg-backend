package com.asg.platform.es.repository;

import com.asg.platform.es.mapping.StatusTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganismStatusTrackingRepository extends PagingAndSortingRepository<StatusTracking, String> {

    Page<StatusTracking> findAll(Pageable pageable);
}
