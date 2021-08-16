package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class OrganismSpecimensDTO {

    @Field(type = FieldType.Keyword)
    private String accession;
    @Field(type = FieldType.Keyword)
    private String organismPart;

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getOrganismPart() {
        return organismPart;
    }

    public void setOrganismPart(String organismPart) {
        this.organismPart = organismPart;
    }
}
