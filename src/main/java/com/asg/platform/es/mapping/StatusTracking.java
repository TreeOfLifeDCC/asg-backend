package com.asg.platform.es.mapping;

import com.asg.platform.es.mapping.DTO.TrackingStatusDTO;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@ApiModel(description = "Status Tracking Model")
@Document(indexName = "tracking_status_index", createIndex = false, replicas = 2, shards = 1)
public class StatusTracking {

    @Id
    private String Id;

    @Field(name="annotation", type = FieldType.Keyword)
    private String annotation;

    @Field(name="annotation_complete", type = FieldType.Keyword)
    private String annotation_complete;

    @Field(name="annotation_date", type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String annotation_date;

    @Field(name="assemblies", type = FieldType.Keyword)
    private String assemblies;

    @Field(name="biosamples", type = FieldType.Keyword)
    private String biosamples;

    @Field(name="biosamples_date", type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String biosamples_date;

    @Field(name="commonName", type = FieldType.Keyword)
    private String commonName;

    @Field(name="ena_date", type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String ena_date;

    @Field(name="mapped_reads", type = FieldType.Keyword)
    private String mapped_reads;

    @Field(name="organism", type = FieldType.Keyword)
    private String organism;

    @Field(name="raw_data", type = FieldType.Keyword)
    private String raw_data;

    @Field(name = "trackingSystem", type = FieldType.Nested)
    private TrackingStatusDTO trackingSystem;



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation_complete() {
        return annotation_complete;
    }

    public void setAnnotation_complete(String annotation_complete) {
        this.annotation_complete = annotation_complete;
    }

    public String getAnnotation_date() {
        return annotation_date;
    }

    public void setAnnotation_date(String annotation_date) {
        this.annotation_date = annotation_date;
    }

    public String getAssemblies() {
        return assemblies;
    }

    public void setAssemblies(String assemblies) {
        this.assemblies = assemblies;
    }

    public String getBiosamples() {
        return biosamples;
    }

    public void setBiosamples(String biosamples) {
        this.biosamples = biosamples;
    }

    public String getBiosamples_date() {
        return biosamples_date;
    }

    public void setBiosamples_date(String biosamples_date) {
        this.biosamples_date = biosamples_date;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getEna_date() {
        return ena_date;
    }

    public void setEna_date(String ena_date) {
        this.ena_date = ena_date;
    }

    public String getMapped_reads() {
        return mapped_reads;
    }

    public void setMapped_reads(String mapped_reads) {
        this.mapped_reads = mapped_reads;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(String raw_data) {
        this.raw_data = raw_data;
    }
}
