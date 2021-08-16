package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class AnnotationDTO {

    @Field(type = FieldType.Keyword)
    private String accession;
    @Field(type = FieldType.Keyword)
    private AnnotationHelperDTO annotation;
    @Field(type = FieldType.Keyword)
    private AnnotationFastaDTO proteins;
    @Field(type = FieldType.Keyword)
    private AnnotationFastaDTO transcripts;
    @Field(type = FieldType.Keyword)
    private AnnotationFastaDTO softmasked_genome;
    @Field(type = FieldType.Keyword)
    private AnnotationOtherDataDTO other_data;
    @Field(type = FieldType.Keyword)
    private String view_in_browser;

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public AnnotationHelperDTO getAnnotation() {
        return annotation;
    }

    public void setAnnotation(AnnotationHelperDTO annotation) {
        this.annotation = annotation;
    }

    public AnnotationFastaDTO getProteins() {
        return proteins;
    }

    public void setProteins(AnnotationFastaDTO proteins) {
        this.proteins = proteins;
    }

    public AnnotationFastaDTO getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(AnnotationFastaDTO transcripts) {
        this.transcripts = transcripts;
    }

    public AnnotationFastaDTO getSoftmasked_genome() {
        return softmasked_genome;
    }

    public void setSoftmasked_genome(AnnotationFastaDTO softmasked_genome) {
        this.softmasked_genome = softmasked_genome;
    }

    public AnnotationOtherDataDTO getOther_data() {
        return other_data;
    }

    public void setOther_data(AnnotationOtherDataDTO other_data) {
        this.other_data = other_data;
    }

    public String getView_in_browser() {
        return view_in_browser;
    }

    public void setView_in_browser(String view_in_browser) {
        this.view_in_browser = view_in_browser;
    }
}
