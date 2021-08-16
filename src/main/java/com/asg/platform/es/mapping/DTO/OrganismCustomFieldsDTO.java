package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

public class OrganismCustomFieldsDTO {

    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String value;
    @Field(type = FieldType.Keyword)
    private String unit;
    @Field(type = FieldType.Keyword)
    private List<String> ontologyTerms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<String> getOntologyTerms() {
        return ontologyTerms;
    }

    public void setOntologyTerms(List<String> ontologyTerms) {
        this.ontologyTerms = ontologyTerms;
    }
}
