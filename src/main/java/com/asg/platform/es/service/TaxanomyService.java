package com.asg.platform.es.service;

import org.json.simple.parser.ParseException;

import java.util.Optional;

public interface TaxanomyService {

    public String findTaxanomiesByParent(String parent);

    public Boolean findIfTaxanomyHasChild(String organism);

    public String getTaxonomicRanksAndCounts(Optional<String> taxonomy) throws ParseException;

    public String getChildTaxonomyRank(Optional<String> filter, String taxonomy, String value, String childRank, String taxaTree, String type) throws ParseException;

    public String getPhylogeneticTree();

}
