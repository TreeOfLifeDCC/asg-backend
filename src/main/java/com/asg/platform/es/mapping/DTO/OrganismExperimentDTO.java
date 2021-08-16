package com.asg.platform.es.mapping.DTO;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

public class OrganismExperimentDTO {
    @Field(type = FieldType.Keyword)
    private String study_accession;
    @Field(type = FieldType.Keyword)
    private String sample_accession;
    @Field(type = FieldType.Keyword)
    private String experiment_accession;
    @Field(type = FieldType.Keyword)
    private String run_accession;
    @Field(type = FieldType.Keyword)
    private String tax_id;
    @Field(type = FieldType.Keyword)
    private String scientific_name;
    @Field(type = FieldType.Keyword)
    private List<String> fastq_ftp;
    @Field(type = FieldType.Keyword)
    private List<String> submitted_ftp;
    @Field(type = FieldType.Keyword)
    private List<String> sra_ftp;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String first_public;
    @Field(type = FieldType.Keyword)
    private String instrument_model;
    @Field(type = FieldType.Keyword)
    private String instrument_platform;
    @Field(type = FieldType.Keyword)
    private String library_layout;
    @Field(type = FieldType.Keyword)
    private String library_selection;
    @Field(type = FieldType.Keyword)
    private String library_source;
    @Field(type = FieldType.Keyword)
    private String library_strategy;
    @Field(type = FieldType.Keyword)
    private String library_construction_protocol;

    @Field(type = FieldType.Keyword)
    private String secondary_study_accession;
    @Field(type = FieldType.Keyword)
    private String secondary_sample_accession;
    @Field(type = FieldType.Keyword)
    private String library_name;
    @Field(type = FieldType.Keyword)
    private String nominal_length;
    @Field(type = FieldType.Keyword)
    private String read_count;
    @Field(type = FieldType.Keyword)
    private String base_count;
    @Field(type = FieldType.Keyword)
    private String center_name;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String last_updated;
    @Field(type = FieldType.Keyword)
    private String experiment_title;
    @Field(type = FieldType.Keyword)
    private String study_title;
    @Field(type = FieldType.Keyword)
    private String study_alias;
    @Field(type = FieldType.Keyword)
    private String experiment_alias;
    @Field(type = FieldType.Keyword)
    private String run_alias;
    @Field(type = FieldType.Keyword)
    private String fastq_bytes;
    @Field(type = FieldType.Keyword)
    private String fastq_md5;
    @Field(type = FieldType.Keyword)
    private String fastq_aspera;
    @Field(type = FieldType.Keyword)
    private String fastq_galaxy;
    @Field(type = FieldType.Keyword)
    private String submitted_bytes;
    @Field(type = FieldType.Keyword)
    private String submitted_md5;
    @Field(type = FieldType.Keyword)
    private String submitted_aspera;
    @Field(type = FieldType.Keyword)
    private String submitted_galaxy;
    @Field(type = FieldType.Keyword)
    private String submitted_format;
    @Field(type = FieldType.Keyword)
    private String sra_bytes;
    @Field(type = FieldType.Keyword)
    private String sra_md5;
    @Field(type = FieldType.Keyword)
    private String sra_aspera;
    @Field(type = FieldType.Keyword)
    private String sra_galaxy;
    @Field(type = FieldType.Keyword)
    private String cram_index_ftp;
    @Field(type = FieldType.Keyword)
    private String cram_index_aspera;
    @Field(type = FieldType.Keyword)
    private String cram_index_galaxy;
    @Field(type = FieldType.Keyword)
    private String sample_alias;
    @Field(type = FieldType.Keyword)
    private String broker_name;
    @Field(type = FieldType.Keyword)
    private String sample_title;
    @Field(type = FieldType.Keyword)
    private String nominal_sdev;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String first_created;

    public String getSecondary_study_accession() {
        return secondary_study_accession;
    }

    public void setSecondary_study_accession(String secondary_study_accession) {
        this.secondary_study_accession = secondary_study_accession;
    }

    public String getSecondary_sample_accession() {
        return secondary_sample_accession;
    }

    public void setSecondary_sample_accession(String secondary_sample_accession) {
        this.secondary_sample_accession = secondary_sample_accession;
    }

    public String getLibrary_name() {
        return library_name;
    }

    public void setLibrary_name(String library_name) {
        this.library_name = library_name;
    }

    public String getNominal_length() {
        return nominal_length;
    }

    public void setNominal_length(String nominal_length) {
        this.nominal_length = nominal_length;
    }

    public String getRead_count() {
        return read_count;
    }

    public void setRead_count(String read_count) {
        this.read_count = read_count;
    }

    public String getBase_count() {
        return base_count;
    }

    public void setBase_count(String base_count) {
        this.base_count = base_count;
    }

    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getExperiment_title() {
        return experiment_title;
    }

    public void setExperiment_title(String experiment_title) {
        this.experiment_title = experiment_title;
    }

    public String getStudy_title() {
        return study_title;
    }

    public void setStudy_title(String study_title) {
        this.study_title = study_title;
    }

    public String getStudy_alias() {
        return study_alias;
    }

    public void setStudy_alias(String study_alias) {
        this.study_alias = study_alias;
    }

    public String getExperiment_alias() {
        return experiment_alias;
    }

    public void setExperiment_alias(String experiment_alias) {
        this.experiment_alias = experiment_alias;
    }

    public String getRun_alias() {
        return run_alias;
    }

    public void setRun_alias(String run_alias) {
        this.run_alias = run_alias;
    }

    public String getFastq_bytes() {
        return fastq_bytes;
    }

    public void setFastq_bytes(String fastq_bytes) {
        this.fastq_bytes = fastq_bytes;
    }

    public String getFastq_md5() {
        return fastq_md5;
    }

    public void setFastq_md5(String fastq_md5) {
        this.fastq_md5 = fastq_md5;
    }

    public String getFastq_aspera() {
        return fastq_aspera;
    }

    public void setFastq_aspera(String fastq_aspera) {
        this.fastq_aspera = fastq_aspera;
    }

    public String getFastq_galaxy() {
        return fastq_galaxy;
    }

    public void setFastq_galaxy(String fastq_galaxy) {
        this.fastq_galaxy = fastq_galaxy;
    }

    public String getSubmitted_bytes() {
        return submitted_bytes;
    }

    public void setSubmitted_bytes(String submitted_bytes) {
        this.submitted_bytes = submitted_bytes;
    }

    public String getSubmitted_md5() {
        return submitted_md5;
    }

    public void setSubmitted_md5(String submitted_md5) {
        this.submitted_md5 = submitted_md5;
    }

    public String getSubmitted_aspera() {
        return submitted_aspera;
    }

    public void setSubmitted_aspera(String submitted_aspera) {
        this.submitted_aspera = submitted_aspera;
    }

    public String getSubmitted_galaxy() {
        return submitted_galaxy;
    }

    public void setSubmitted_galaxy(String submitted_galaxy) {
        this.submitted_galaxy = submitted_galaxy;
    }

    public String getSubmitted_format() {
        return submitted_format;
    }

    public void setSubmitted_format(String submitted_format) {
        this.submitted_format = submitted_format;
    }

    public String getSra_bytes() {
        return sra_bytes;
    }

    public void setSra_bytes(String sra_bytes) {
        this.sra_bytes = sra_bytes;
    }

    public String getSra_md5() {
        return sra_md5;
    }

    public void setSra_md5(String sra_md5) {
        this.sra_md5 = sra_md5;
    }

    public String getSra_aspera() {
        return sra_aspera;
    }

    public void setSra_aspera(String sra_aspera) {
        this.sra_aspera = sra_aspera;
    }

    public String getSra_galaxy() {
        return sra_galaxy;
    }

    public void setSra_galaxy(String sra_galaxy) {
        this.sra_galaxy = sra_galaxy;
    }

    public String getCram_index_ftp() {
        return cram_index_ftp;
    }

    public void setCram_index_ftp(String cram_index_ftp) {
        this.cram_index_ftp = cram_index_ftp;
    }

    public String getCram_index_aspera() {
        return cram_index_aspera;
    }

    public void setCram_index_aspera(String cram_index_aspera) {
        this.cram_index_aspera = cram_index_aspera;
    }

    public String getCram_index_galaxy() {
        return cram_index_galaxy;
    }

    public void setCram_index_galaxy(String cram_index_galaxy) {
        this.cram_index_galaxy = cram_index_galaxy;
    }

    public String getSample_alias() {
        return sample_alias;
    }

    public void setSample_alias(String sample_alias) {
        this.sample_alias = sample_alias;
    }

    public String getBroker_name() {
        return broker_name;
    }

    public void setBroker_name(String broker_name) {
        this.broker_name = broker_name;
    }

    public String getSample_title() {
        return sample_title;
    }

    public void setSample_title(String sample_title) {
        this.sample_title = sample_title;
    }

    public String getNominal_sdev() {
        return nominal_sdev;
    }

    public void setNominal_sdev(String nominal_sdev) {
        this.nominal_sdev = nominal_sdev;
    }

    public String getFirst_created() {
        return first_created;
    }

    public void setFirst_created(String first_created) {
        this.first_created = first_created;
    }

    public String getStudy_accession() {
        return study_accession;
    }

    public void setStudy_accession(String study_accession) {
        this.study_accession = study_accession;
    }

    public String getSample_accession() {
        return sample_accession;
    }

    public void setSample_accession(String sample_accession) {
        this.sample_accession = sample_accession;
    }

    public String getExperiment_accession() {
        return experiment_accession;
    }

    public void setExperiment_accession(String experiment_accession) {
        this.experiment_accession = experiment_accession;
    }

    public String getRun_accession() {
        return run_accession;
    }

    public void setRun_accession(String run_accession) {
        this.run_accession = run_accession;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getScientific_name() {
        return scientific_name;
    }

    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }

    public List<String> getFastq_ftp() {
        return fastq_ftp;
    }

    public void setFastq_ftp(List<String> fastq_ftp) {
        this.fastq_ftp = fastq_ftp;
    }

    public List<String> getSubmitted_ftp() {
        return submitted_ftp;
    }

    public void setSubmitted_ftp(List<String> submitted_ftp) {
        this.submitted_ftp = submitted_ftp;
    }

    public List<String> getSra_ftp() {
        return sra_ftp;
    }

    public void setSra_ftp(List<String> sra_ftp) {
        this.sra_ftp = sra_ftp;
    }

    public String getFirst_public() {
        return first_public;
    }

    public void setFirst_public(String first_public) {
        this.first_public = first_public;
    }

    public String getInstrument_model() {
        return instrument_model;
    }

    public void setInstrument_model(String instrument_model) {
        this.instrument_model = instrument_model;
    }

    public String getInstrument_platform() {
        return instrument_platform;
    }

    public void setInstrument_platform(String instrument_platform) {
        this.instrument_platform = instrument_platform;
    }

    public String getLibrary_layout() {
        return library_layout;
    }

    public void setLibrary_layout(String library_layout) {
        this.library_layout = library_layout;
    }

    public String getLibrary_selection() {
        return library_selection;
    }

    public void setLibrary_selection(String library_selection) {
        this.library_selection = library_selection;
    }

    public String getLibrary_source() {
        return library_source;
    }

    public void setLibrary_source(String library_source) {
        this.library_source = library_source;
    }

    public String getLibrary_strategy() {
        return library_strategy;
    }

    public void setLibrary_strategy(String library_strategy) {
        this.library_strategy = library_strategy;
    }

    public String getLibrary_construction_protocol() {
        return library_construction_protocol;
    }

    public void setLibrary_construction_protocol(String library_construction_protocol) {
        this.library_construction_protocol = library_construction_protocol;
    }
}
