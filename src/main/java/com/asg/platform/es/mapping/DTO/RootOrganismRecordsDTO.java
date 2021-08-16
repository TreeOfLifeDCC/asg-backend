package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class RootOrganismRecordsDTO {

    @Field(type = FieldType.Keyword)
    private String accession;
    @Field(type = FieldType.Keyword)
    private String commonName;
    @Field(type = FieldType.Keyword)
    private OrganismOntologyDTO organism;
    @Field(type = FieldType.Keyword)
    private String organismPart;
    @Field(type = FieldType.Keyword)
    private String sex;
    @Field(type = FieldType.Keyword)
    private String trackingSystem;

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public OrganismOntologyDTO getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismOntologyDTO organism) {
        this.organism = organism;
    }

    public String getOrganismPart() {
        return organismPart;
    }

    public void setOrganismPart(String organismPart) {
        this.organismPart = organismPart;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTrackingSystem() {
        return trackingSystem;
    }

    public void setTrackingSystem(String trackingSystem) {
        this.trackingSystem = trackingSystem;
    }
}

