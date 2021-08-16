package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class AnnotationHelperDTO {
    @Field(type = FieldType.Keyword)
    String GTF;
    @Field(type = FieldType.Keyword)
    String GFF3;

    public String getGTF() {
        return GTF;
    }

    public void setGTF(String GTF) {
        this.GTF = GTF;
    }

    public String getGFF3() {
        return GFF3;
    }

    public void setGFF3(String GFF3) {
        this.GFF3 = GFF3;
    }
}
