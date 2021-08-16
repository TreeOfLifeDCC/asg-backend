package com.asg.platform.es.mapping;

import com.asg.platform.es.mapping.DTO.*;
import io.swagger.annotations.ApiModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
@ApiModel(description = "Secondary Organism Model")
@Document(indexName = "organisms", createIndex = false, replicas = 2, shards = 1)
public class SecondaryOrganism {

    @Id
    private String Id;

    @Field(name = "accession", type = FieldType.Keyword)
    private String accession;

    @Field(name = "taxonId", type = FieldType.Keyword)
    private String taxonId;

    @Field(name = "specimenId", type = FieldType.Keyword)
    private String specimenId;

    @Field(name = "cultureOrStrainId", type = FieldType.Text)
    private String cultureOrStrainId;

    @Field(name = "lifestage", type = FieldType.Text)
    private String lifestage;

    @Field(name = "sex", type = FieldType.Keyword)
    private String sex;

    @Field(name = "organism", type = FieldType.Nested)
    private OrganismOntologyDTO organism;

    @Field(name = "commonName", type = FieldType.Text)
    private String commonName;

    @Field(name = "relationship", type = FieldType.Keyword)
    private String relationship;

    @Field(name = "gal", type = FieldType.Text)
    private String gal;

    @Field(name = "galSampleId", type = FieldType.Keyword)
    private String galSampleId;

    @Field(name = "collectedBy", type = FieldType.Text)
    private String collectedBy;

    @Field(name = "collectingInstitution", type = FieldType.Text)
    private String collectingInstitution;

    @Field(name = "collectionDate", type = FieldType.Date, format = DateFormat.basic_date_time, pattern = "yyyy-MM-dd")
    private String collectionDate;

    @Field(name = "geographicLocationCountryAndOrSea", type = FieldType.Text)
    private String geographicLocationCountry;

    @Field(name = "geographicLocationRegionAndLocality", type = FieldType.Text)
    private String geographicLocationRegionAndLocality;

    @Field(name = "geographicLocationLatitude", type = FieldType.Nested)
    private OrganismGeographicLocationDTO geographicLocationLatitude;

    @Field(name = "geographicLocationLongitude", type = FieldType.Nested)
    private OrganismGeographicLocationDTO geographicLocationLongitude;

    @Field(name = "habitat", type = FieldType.Text)
    private String habitat;

    @Field(name = "geographicLocationDepth", type = FieldType.Nested)
    private OrganismGeographicLocationDTO geographicLocationDepth;

    @Field(name = "geographicLocationElevation", type = FieldType.Nested)
    private OrganismGeographicLocationDTO geographicLocationElevation;

    @Field(name = "identifiedBy", type = FieldType.Text)
    private String identifiedBy;

    @Field(name = "identifierAffiliation", type = FieldType.Text)
    private String identifierAffiliation;

    @Field(name = "specimenVoucher", type = FieldType.Text)
    private String specimenVoucher;

    @Field(name = "projectName", type = FieldType.Keyword)
    private String projectName;

    @Field(name = "customFields", type = FieldType.Nested)
    private List<OrganismCustomFieldsDTO> customFields;

    @Field(name = "trackingSystem", type = FieldType.Text)
    private String trackingSystem;

    @Field(name = "etag", type = FieldType.Keyword)
    private String etag;

    @Field(name = "organismPart", type = FieldType.Keyword)
    private String organismPart;

    @Field(name = "sampleDerivedFrom", type = FieldType.Keyword)
    private String sampleDerivedFrom;

    @Field(name = "specimens", type = FieldType.Nested)
    private List<RootOrganismRecordsDTO> specimens;

    @Field(name = "assemblies", type = FieldType.Nested)
    private List<OrganismAssemblyDTO> assemblies;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(String taxonId) {
        this.taxonId = taxonId;
    }

    public String getSpecimenId() {
        return specimenId;
    }

    public void setSpecimenId(String specimenId) {
        this.specimenId = specimenId;
    }

    public String getCultureOrStrainId() {
        return cultureOrStrainId;
    }

    public void setCultureOrStrainId(String cultureOrStrainId) {
        this.cultureOrStrainId = cultureOrStrainId;
    }

    public String getLifestage() {
        return lifestage;
    }

    public void setLifestage(String lifestage) {
        this.lifestage = lifestage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public OrganismOntologyDTO getOrganism() {
        return organism;
    }

    public void setOrganism(OrganismOntologyDTO organism) {
        this.organism = organism;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getGal() {
        return gal;
    }

    public void setGal(String gal) {
        this.gal = gal;
    }

    public String getGalSampleId() {
        return galSampleId;
    }

    public void setGalSampleId(String galSampleId) {
        this.galSampleId = galSampleId;
    }

    public String getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(String collectedBy) {
        this.collectedBy = collectedBy;
    }

    public String getCollectingInstitution() {
        return collectingInstitution;
    }

    public void setCollectingInstitution(String collectingInstitution) {
        this.collectingInstitution = collectingInstitution;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getGeographicLocationCountry() {
        return geographicLocationCountry;
    }

    public void setGeographicLocationCountry(String geographicLocationCountry) {
        this.geographicLocationCountry = geographicLocationCountry;
    }

    public String getGeographicLocationRegionAndLocality() {
        return geographicLocationRegionAndLocality;
    }

    public void setGeographicLocationRegionAndLocality(String geographicLocationRegionAndLocality) {
        this.geographicLocationRegionAndLocality = geographicLocationRegionAndLocality;
    }

    public OrganismGeographicLocationDTO getGeographicLocationLatitude() {
        return geographicLocationLatitude;
    }

    public void setGeographicLocationLatitude(OrganismGeographicLocationDTO geographicLocationLatitude) {
        this.geographicLocationLatitude = geographicLocationLatitude;
    }

    public OrganismGeographicLocationDTO getGeographicLocationLongitude() {
        return geographicLocationLongitude;
    }

    public void setGeographicLocationLongitude(OrganismGeographicLocationDTO geographicLocationLongitude) {
        this.geographicLocationLongitude = geographicLocationLongitude;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public OrganismGeographicLocationDTO getGeographicLocationDepth() {
        return geographicLocationDepth;
    }

    public void setGeographicLocationDepth(OrganismGeographicLocationDTO geographicLocationDepth) {
        this.geographicLocationDepth = geographicLocationDepth;
    }

    public OrganismGeographicLocationDTO getGeographicLocationElevation() {
        return geographicLocationElevation;
    }

    public void setGeographicLocationElevation(OrganismGeographicLocationDTO geographicLocationElevation) {
        this.geographicLocationElevation = geographicLocationElevation;
    }

    public String getIdentifiedBy() {
        return identifiedBy;
    }

    public void setIdentifiedBy(String identifiedBy) {
        this.identifiedBy = identifiedBy;
    }

    public String getIdentifierAffiliation() {
        return identifierAffiliation;
    }

    public void setIdentifierAffiliation(String identifierAffiliation) {
        this.identifierAffiliation = identifierAffiliation;
    }

    public String getSpecimenVoucher() {
        return specimenVoucher;
    }

    public void setSpecimenVoucher(String specimenVoucher) {
        this.specimenVoucher = specimenVoucher;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<OrganismCustomFieldsDTO> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<OrganismCustomFieldsDTO> customFields) {
        this.customFields = customFields;
    }

    public String getTrackingSystem() {
        return trackingSystem;
    }

    public void setTrackingSystem(String trackingSystem) {
        this.trackingSystem = trackingSystem;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getOrganismPart() {
        return organismPart;
    }

    public void setOrganismPart(String organismPart) {
        this.organismPart = organismPart;
    }

    public String getSampleDerivedFrom() {
        return sampleDerivedFrom;
    }

    public void setSampleDerivedFrom(String sampleDerivedFrom) {
        this.sampleDerivedFrom = sampleDerivedFrom;
    }

    public List<RootOrganismRecordsDTO> getSpecimens() {
        return specimens;
    }

    public void setSpecimens(List<RootOrganismRecordsDTO> specimens) {
        this.specimens = specimens;
    }

    public List<OrganismAssemblyDTO> getAssemblies() {
        return assemblies;
    }

    public void setAssemblies(List<OrganismAssemblyDTO> assemblies) {
        this.assemblies = assemblies;
    }
}
