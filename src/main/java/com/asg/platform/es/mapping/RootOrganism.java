package com.asg.platform.es.mapping;

import com.asg.platform.es.mapping.DTO.*;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@ApiModel(description = "Root Organism Model")
@Document(indexName = "data_portal_test", createIndex = false, replicas = 2, shards = 1)
public class RootOrganism {
    @Id
    String id;

    @Field(name = "commonName", type = FieldType.Keyword)
    private String commonName;

    @Field(name = "organism", type = FieldType.Keyword)
    private String organism;

    @Field(name = "trackingSystem", type = FieldType.Nested)
    private TrackingStatusDTO trackingSystem;

    @Field(name = "records", type = FieldType.Nested)
    private List<RootOrganismRecordsDTO> records;

    @Field(name = "experiment", type = FieldType.Nested)
    private List<OrganismExperimentDTO> experiment;

    @Field(name = "assemblies", type = FieldType.Nested)
    private List<OrganismAssemblyDTO> assemblies;

    @Field(name = "annotation", type = FieldType.Nested)
    private List<AnnotationDTO> annotation;

    @Field(name = "tax_id", type = FieldType.Keyword)
    private String taxId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public TrackingStatusDTO getTrackingSystem() {
        return trackingSystem;
    }

    public void setTrackingSystem(TrackingStatusDTO trackingSystem) {
        this.trackingSystem = trackingSystem;
    }

    public List<RootOrganismRecordsDTO> getRecords() {
        return records;
    }

    public void setRecords(List<RootOrganismRecordsDTO> records) {
        this.records = records;
    }

    public List<OrganismExperimentDTO> getExperiment() {
        return experiment;
    }

    public void setExperiment(List<OrganismExperimentDTO> experiment) {
        this.experiment = experiment;
    }

    public List<OrganismAssemblyDTO> getAssemblies() {
        return assemblies;
    }

    public void setAssemblies(List<OrganismAssemblyDTO> assemblies) {
        this.assemblies = assemblies;
    }

    public List<AnnotationDTO> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(List<AnnotationDTO> annotation) {
        this.annotation = annotation;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }
}
