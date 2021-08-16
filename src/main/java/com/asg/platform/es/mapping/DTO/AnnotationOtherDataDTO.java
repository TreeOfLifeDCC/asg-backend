package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class AnnotationOtherDataDTO {

    @Field(type = FieldType.Keyword)
    String ftp_dumps;

    public String getFtp_dumps() {
        return ftp_dumps;
    }

    public void setFtp_dumps(String ftp_dumps) {
        this.ftp_dumps = ftp_dumps;
    }
}
