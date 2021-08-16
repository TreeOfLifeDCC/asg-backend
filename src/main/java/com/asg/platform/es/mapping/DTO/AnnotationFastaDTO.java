package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class AnnotationFastaDTO {

    @Field(type = FieldType.Keyword)
    String FASTA;

    public String getFASTA() {
        return FASTA;
    }

    public void setFASTA(String FASTA) {
        this.FASTA = FASTA;
    }
}
