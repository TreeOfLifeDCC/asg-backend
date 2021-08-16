package com.asg.platform.es.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrganismStatusTrackingService {

    public JSONArray findAll(int page, int size, Optional<String> sortColumn, Optional<String> sortOrder) throws ParseException;

    public long getBiosampleStatusTrackingCount();

    public Map<String, List<JSONObject>> getFilters() throws ParseException;

    public String findSearchResult(String search, Optional<String> from, Optional<String> size, Optional<String> sortColumn, Optional<String> sortOrder);

    public String findFilterResults(Optional<String> filter, Optional<String> from, Optional<String> size, Optional<String> sortColumn, Optional<String> sortOrder, Optional<String> taxonomyFilter) throws ParseException;

    public String findBioSampleByOrganismByText(String search, Optional<String> from, Optional<String> size, Optional<String> sortColumn, Optional<String> sortOrder);

}
