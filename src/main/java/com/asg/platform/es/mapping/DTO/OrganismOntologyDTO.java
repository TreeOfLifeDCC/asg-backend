package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

public class OrganismOntologyDTO {
    @Field(type = FieldType.Keyword)
    String text;
    @Field(type = FieldType.Keyword)
    List<String> ontologyTerms;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOntologyTerms() {
        return ontologyTerms;
    }

    public void setOntologyTerms(List<String> ontologyTerms) {
        this.ontologyTerms = ontologyTerms;
    }
}
