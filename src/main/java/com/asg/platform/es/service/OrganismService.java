package com.asg.platform.es.service;

import com.asg.platform.es.mapping.SecondaryOrganism;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrganismService {

    public List<SecondaryOrganism> findAll(int page, int size, Optional<String> sortColumn, Optional<String> sortOrder);

    public SecondaryOrganism findBioSampleByAccession(String accession);

    public String saveBioSample(SecondaryOrganism secondaryOrganism);

    public long getBiosampleCount();

    public SecondaryOrganism findBioSampleByOrganismByText(String organism);

    public Map<String, JSONArray> getSpecimensFilters(String accession) throws ParseException;

    public String getOrganismByAccession(String accession);

    public String getSpecimenByAccession(String accession);

}
