package com.asg.platform.es.service;

import org.json.simple.parser.ParseException;

import java.util.Optional;

public interface TaxanomyService {

    public String findTaxanomiesByParent(String parent);

    public Boolean findIfTaxanomyHasChild(String organism);

    public String getTaxonomicRanksAndCounts(Optional<String> taxonomy) throws ParseException;

    public String getChildTaxonomyRank(Optional<String> search, Optional<String> filter, String rank, String taxonomy, String childRank, String tree, String type) throws ParseException;

    public String getPhylogeneticTree();

}
