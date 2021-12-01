package com.asg.platform.es.service;

import com.asg.platform.es.mapping.RootOrganism;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RootSampleService {

    public JSONArray findAllOrganisms(int page, int size, Optional<String> sortColumn, Optional<String> sortOrder) throws ParseException;

    public Map<String, List<JSONObject>> getRootOrganismFilters() throws ParseException;

    public Map<String, JSONArray> getSecondaryOrganismFilters(String organism) throws ParseException;

    public String findRootOrganismSearchResult(String search, Optional<String> from, Optional<String> size, Optional<String> sortColumn, Optional<String> sortOrder);

    public String findSecondaryOrganismFilterResults(String organism, String filter, Optional<String> from, Optional<String> size, Optional<String> sortColumn, Optional<String> sortOrder);

    public String findRootOrganismFilterResults(Optional<String> filter, Optional<String> from, Optional<String> size, Optional<String> sortColumn, Optional<String> sortOrder, Optional<String> taxonomyFilter) throws ParseException;

    public long getRootOrganismCount() throws ParseException;

    public long getRelatedOrganismCount() throws ParseException;

    public String getDistinctRootSamplesByOrganismQuery(String size, Optional<String> sortColumn, Optional<String> sortOrder, Optional<String> afterKey);

    public String getDistinctRootSamplesCountByOrganismQuery();

    public JSONObject getDistinctRootSamplesByOrganism(String size, Optional<String> sortColumn, Optional<String> sortOrder, Optional<String> afterKey) throws ParseException;

    public String getDistinctRootSamplesCountByOrganism() throws ParseException;

    public RootOrganism findRootSampleByOrganism(String organism);

    public JSONArray findSampleAccessionByOrganism(String organism) throws ParseException;

    public JSONObject findRootSampleById(String id) throws ParseException;

}
