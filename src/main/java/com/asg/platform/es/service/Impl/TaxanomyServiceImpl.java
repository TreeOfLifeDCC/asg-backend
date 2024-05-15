package com.asg.platform.es.service.Impl;

import com.asg.platform.es.service.TaxanomyService;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Transactional
public class TaxanomyServiceImpl implements TaxanomyService {

    @Value("${ES_CONNECTION_URL}")
    String esConnectionURL;

    @Value("${ES_USERNAME}")
    String esUsername;

    @Value("${ES_PASSWORD}")
    String esPassword;

    @Autowired
    Driver driver;

    @Override
    public String findTaxanomiesByParent(String parent) {
        Map<String, JSONArray> filterMap = new HashMap<String, JSONArray>();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("'query' : { 'parent_id' : { 'type' :'grandchild',");
        sb.append("'id' : '" + parent + "'");
        sb.append("}}}");

        String query = sb.toString().replaceAll("'", "\"");
        String respString = this.postRequest( "https://"+esConnectionURL + "/ontology/_search", query);

        return respString;
    } static final String [] taxaRankArray = {"superkingdom", "kingdom","subkingdom","superphylum","phylum","subphylum","superclass","class","subclass","infraclass","cohort","subcohort","superorder","order","suborder","infraorder","parvorder","section","subsection","superfamily","family","subfamily","tribe","subtribe","genus","series","subgenus","species_group","species_subgroup","species","subspecies","varietas","forma"};


    @Override
    public Boolean findIfTaxanomyHasChild(String organism) {
        return null;
    }

    @Override
    public String getTaxonomicRanksAndCounts(Optional<String> taxonomy) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (taxonomy.isPresent() && !taxonomy.get().equals("")) {
            sb.append("'size':0,");
            sb.append("'query' : { 'nested' : { 'path': 'taxonomies', 'query' : ");
            sb.append("{ 'query_string' : { 'query' : '");
            sb.append(taxonomy.get() + "'");
            sb.append("}}}},");
        }

        getNestedOntologyAggregations(sb);

        sb.append("}}}}");
        String query = sb.toString().replaceAll("'", "\"");
        String respString = this.postRequest("https://"+esConnectionURL + "/data_portal/_search", query);
        JSONObject aggregations = (JSONObject) ((JSONObject) ((JSONObject) new JSONParser().parse(respString)).get("aggregations")).get("filters");

        return aggregations.toJSONString();
    }

    @Override
    public String getChildTaxonomyRank(Optional<String> search, Optional<String> filter, String rank, String taxonomy, String childRank, String tree, String type) throws ParseException {
        StringBuilder sb = new StringBuilder();
        StringBuilder filtersb = new StringBuilder();
        JSONObject resp = new JSONObject();
        JSONArray taxaTree = (JSONArray) new JSONParser().parse(tree);

        JSONArray childDataArray = new JSONArray();
        StringBuilder hasChildQuery = new StringBuilder();
        StringBuilder hasChildFilterQuery = new StringBuilder();
        String esURL = "https://"+esConnectionURL;

        StringBuilder searchQuery = new StringBuilder();

        if (search.isPresent()) {
            String[] searchArray = search.get().split(" ");
            for (String temp : searchArray) {
                searchQuery.append("*" + temp + "*");
            }
        }

        sb.append("{");
        sb.append("'size':0,");
        sb.append("'query' : { 'bool' : { 'must' : [");

        if (type.equals("status")) {
            esURL = esURL + "/tracking_status_index/_search";

        } else if (type.equals("data")) {
            esURL = esURL + "/data_portal/_search";
            if (searchQuery.length() != 0) {
                sb.append("{'query_string': {");
                sb.append("'query' : '" + searchQuery.toString() + "',");
                sb.append("'fields' : ['organism.normalize','commonName.normalize', 'biosamples','raw_data','mapped_reads','assemblies_status','annotation_complete','annotation_status']");
                sb.append("}},");
            }

        }

        for (int i = 0; i < taxaTree.size(); i++) {
            JSONObject taxa = (JSONObject) taxaTree.get(i);
            if (taxaTree.size() == 1) {
                sb.append("{ 'nested' : { 'path': 'taxonomies', 'query' : ");
                sb.append("{ 'nested' : { 'path': 'taxonomies." + taxa.get("rank") + "', 'query' : ");
                sb.append("{ 'bool' : { 'must' : [");
                sb.append("{ 'term' : { 'taxonomies.");
                sb.append(taxa.get("rank") + ".scientificName': '" + taxa.get("taxonomy") + "'}}");
                sb.append("]}}}}}}");
            } else {
                if (i == taxaTree.size() - 1) {
                    sb.append("{ 'nested' : { 'path': 'taxonomies', 'query' : ");
                    sb.append("{ 'nested' : { 'path': 'taxonomies." + taxa.get("rank") + "', 'query' : ");
                    sb.append("{ 'bool' : { 'must' : [");
                    sb.append("{ 'term' : { 'taxonomies.");
                    sb.append(taxa.get("rank") + ".scientificName': '" + taxa.get("taxonomy") + "'}}");
                    sb.append("]}}}}}}");
                } else {
                    sb.append("{ 'nested' : { 'path': 'taxonomies', 'query' : ");
                    sb.append("{ 'nested' : { 'path': 'taxonomies." + taxa.get("rank") + "', 'query' : ");
                    sb.append("{ 'bool' : { 'must' : [");
                    sb.append("{ 'term' : { 'taxonomies.");
                    sb.append(taxa.get("rank") + ".scientificName': '" + taxa.get("taxonomy") + "'}}");
                    sb.append("]}}}}}},");
                }
            }
        }

        if (filter.isPresent() && (!filter.get().equals("undefined") && !filter.get().equals("")) ) {
            if (type.equals("data")) {
                String[] filterArray = filter.get().split(",");
                if(tree != null && tree !="undefined") {
                    if(((JSONArray) (new JSONParser().parse(tree))).size() > 0)
                        sb.append(filtersb.toString() + ",");
                    for (int i = 0; i < filterArray.length; i++) {
                        String[] splitArray = filterArray[i].split("-");
                        if (splitArray[0].trim().equals("Biosamples")) {
                            filtersb.append("{'terms' : {'biosamples':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        } else if (splitArray[0].trim().equals("Raw data")) {

                            filtersb.append("{'terms' : {'raw_data':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        }
                        else if (splitArray[0].trim().equals("Mapped reads")) {

                            filtersb.append("{'terms' : {'mapped_reads':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        }
                        else if (splitArray[0].trim().equals("Assemblies")) {

                            filtersb.append("{'terms' : {'assemblies_status':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("Annotation complete")) {


                            filtersb.append("{'terms' : {'annotation_complete':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        } else if (splitArray[0].trim().equals("Annotation")) {

                            filtersb.append("{'terms' : {'annotation_status':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        } else if (splitArray[0].trim().equals("Genome Notes")) {
                            filtersb.append("{ 'nested': {'path': 'genome_notes','query': {'bool': {'must': [{'exists': {'field': 'genome_notes.id'}}");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}}}");
                            else
                                filtersb.append("]}}}},");
                        } else if (splitArray[0].trim().equals("symbiontsBioSamplesStatus")) {
                            String symbiontsStatusFilter = filterArray[i].trim().replaceFirst("symbiontsBioSamplesStatus-", "");
                            filtersb.append("{'terms' : {'symbionts_biosamples_status':[");
                            filtersb.append("'" + symbiontsStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("symbiontsRawDataStatus")) {
                            String symbiontsStatusFilter = filterArray[i].trim().replaceFirst("symbiontsRawDataStatus-", "");
                            filtersb.append("{'terms' : {'symbionts_raw_data_status':[");
                            filtersb.append("'" + symbiontsStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("symbiontsAssembliesStatus")) {
                            String symbiontsStatusFilter = filterArray[i].trim().replaceFirst("symbiontsAssembliesStatus-", "");
                            filtersb.append("{'terms' : {'symbionts_assemblies_status':[");
                            filtersb.append("'" + symbiontsStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        }else if (splitArray[0].trim().equals("metagenomesBioSamplesStatus")) {
                            String metagenomesStatusFilter = filterArray[i].trim().replaceFirst("metagenomesBioSamplesStatus-", "");
                            filtersb.append("{'terms' : {'metagenomes_biosamples_status':[");
                            filtersb.append("'" + metagenomesStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("metagenomesRawDataStatus")) {
                            String metagenomesStatusFilter = filterArray[i].trim().replaceFirst("metagenomesRawDataStatus-", "");
                            filtersb.append("{'terms' : {'metagenomes_raw_data_status':[");
                            filtersb.append("'" + metagenomesStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("metagenomesAssembliesStatus")) {
                            String metagenomesStatusFilter = filterArray[i].trim().replaceFirst("metagenomesAssembliesStatus-", "");
                            filtersb.append("{'terms' : {'metagenomes_assemblies_status':[");
                            filtersb.append("'" + metagenomesStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (Arrays.asList(taxaRankArray).contains(splitArray[0].trim())) {
                            filtersb.append("{ 'nested' : { 'path': 'taxonomies', 'query' : ");
                            filtersb.append("{ 'nested' : { 'path': 'taxonomies." + splitArray[0].trim() + "', 'query' : ");
                            filtersb.append("{ 'bool' : { 'must' : [");
                            filtersb.append("{ 'term' : { 'taxonomies.");
                            filtersb.append(splitArray[0].trim() + ".tax_id': '" + splitArray[1].trim() + "'}}");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}}}}}");
                            else
                                filtersb.append("]}}}}}},");
                        } else if (splitArray[0].trim().equals("experimentType")) {
                            String experimentTypeFilter = filterArray[i].trim().replaceFirst("experimentType-", "");
                            filtersb.append("{ 'nested' : { 'path': 'experiment', 'query' : ");
                            filtersb.append("{ 'bool' : { 'must' : [");
                            filtersb.append("{ 'term' : { 'experiment.library_construction_protocol.keyword' : '"+ experimentTypeFilter.trim()+ "'"  );
                            if (i == (filterArray.length - 1))
                                filtersb.append("}}]}}}}");
                            else
                                filtersb.append("}}]}}}},");

                        }
                    }
                }
            } else if (type.equals("status")) {
                String[] filterArray = filter.get().split(",");
                if (filterArray.length > 0 && !filterArray[0].equals("")) {
                    filtersb.append(",");
                    for (int i = 0; i < filterArray.length; i++) {
                        String[] splitArray = filterArray[i].split("-");
                        if (splitArray[0].trim().equals("Biosamples")) {
                            filtersb.append("{'terms' : {'biosamples.keyword':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("Raw data")) {
                            filtersb.append("{'terms' : {'raw_data.keyword':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("Mapped reads")) {
                            filtersb.append("{'terms' : {'mapped_reads.keyword':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("Assemblies")) {
                            filtersb.append("{'terms' : {'assemblies.keyword':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("Annotation complete")) {
                            filtersb.append("{'terms' : {'annotation_complete.keyword':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("Annotation")) {
                            filtersb.append("{'terms' : {'annotation.keyword':[");
                            filtersb.append("'" + splitArray[1].trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        } else if (splitArray[0].trim().equals("symbiontsBioSamplesStatus")) {
                            String symbiontsStatusFilter = filterArray[i].trim().replaceFirst("symbiontsBioSamplesStatus-", "");
                            filtersb.append("{'terms' : {'symbionts_biosamples_status':[");
                            filtersb.append("'" + symbiontsStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("symbiontsRawDataStatus")) {
                            String symbiontsStatusFilter = filterArray[i].trim().replaceFirst("symbiontsRawDataStatus-", "");
                            filtersb.append("{'terms' : {'symbionts_raw_data_status':[");
                            filtersb.append("'" + symbiontsStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("symbiontsAssembliesStatus")) {
                            String symbiontsStatusFilter = filterArray[i].trim().replaceFirst("symbiontsAssembliesStatus-", "");
                            filtersb.append("{'terms' : {'symbionts_assemblies_status':[");
                            filtersb.append("'" + symbiontsStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");

                        }else if (splitArray[0].trim().equals("metagenomesBioSamplesStatus")) {
                            String metagenomesStatusFilter = filterArray[i].trim().replaceFirst("metagenomesBioSamplesStatus-", "");
                            filtersb.append("{'terms' : {'metagenomes_biosamples_status':[");
                            filtersb.append("'" + metagenomesStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("metagenomesRawDataStatus")) {
                            String metagenomesStatusFilter = filterArray[i].trim().replaceFirst("metagenomesRawDataStatus-", "");
                            filtersb.append("{'terms' : {'metagenomes_raw_data_status':[");
                            filtersb.append("'" + metagenomesStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (splitArray[0].trim().equals("metagenomesAssembliesStatus")) {
                            String metagenomesStatusFilter = filterArray[i].trim().replaceFirst("metagenomesAssembliesStatus-", "");
                            filtersb.append("{'terms' : {'metagenomes_assemblies_status':[");
                            filtersb.append("'" + metagenomesStatusFilter.trim() + "'");
                            if (i == (filterArray.length - 1))
                                filtersb.append("]}}");
                            else
                                filtersb.append("]}},");
                        } else if (Arrays.asList(taxaRankArray).contains(splitArray[0].trim())) {
                        filtersb.append("{ 'nested' : { 'path': 'taxonomies', 'query' : ");
                        filtersb.append("{ 'nested' : { 'path': 'taxonomies." + splitArray[0].trim() + "', 'query' : ");
                        filtersb.append("{ 'bool' : { 'must' : [");
                        filtersb.append("{ 'term' : { 'taxonomies.");
                        filtersb.append(splitArray[0].trim() + ".tax_id': '" + splitArray[1].trim() + "'}}");
                        if (i == (filterArray.length - 1))
                            filtersb.append("]}}}}}}");
                        else
                            filtersb.append("]}}}}}},");
                    } else if (splitArray[0].trim().equals("experimentType")) {
                            String experimentTypeFilter = filterArray[i].trim().replaceFirst("experimentType-", "");
                            filtersb.append("{ 'nested' : { 'path': 'experiment', 'query' : ");
                            filtersb.append("{ 'bool' : { 'must' : [");
                            filtersb.append("{ 'term' : { 'experiment.library_construction_protocol.keyword' : '"+ experimentTypeFilter.trim()+ "'"  );
                            if (i == (filterArray.length - 1))
                                filtersb.append("}}]}}}}");
                            else
                                filtersb.append("}}]}}}},");

                        }
                    }
                }
            }
        }
        sb.append(filtersb.toString());

        sb.append("]}},");

        sb.append("'aggregations': {");
        sb.append("'kingdomRank': { 'nested': { 'path':'taxonomies.kingdom'},");
        sb.append("'aggs':{'scientificName':{'terms':{'field':'taxonomies.kingdom.scientificName', 'size': 20000},");
        sb.append("'aggs':{'commonName':{'terms':{'field':'taxonomies.kingdom.commonName', 'size': 20000}},");
        sb.append("'taxId':{'terms':{'field':'taxonomies.kingdom.tax_id.keyword', 'size': 20000}}}}}},");
        sb.append("'childRank': { 'nested': { 'path':'taxonomies." + childRank + "'},");
        sb.append("'aggs':{'scientificName':{'terms':{'field':'taxonomies." + childRank + ".scientificName', 'size': 20000},");
        sb.append("'aggs':{'commonName':{'terms':{'field':'taxonomies." + childRank + ".commonName', 'size': 20000}},");
        sb.append("'taxId':{'terms':{'field':'taxonomies." + childRank + ".tax_id.keyword', 'size': 20000}}}}}}");
        sb.append("}}");
        String query = sb.toString().replaceAll("'", "\"");
        System.out.println(query);
        String respString = this.postRequest(esURL, query);
        JSONArray aggregations = null;
        JSONArray rootAggregations = null;
        aggregations = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) new JSONParser().parse(respString)).get("aggregations")).get("childRank")).get("scientificName")).get("buckets");
        rootAggregations = (JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) new JSONParser().parse(respString)).get("aggregations")).get("kingdomRank")).get("scientificName")).get("buckets");
        JSONObject childTaxa = new JSONObject();
        JSONObject rootAggregationObject = new JSONObject();
        rootAggregationObject.put("parent", "");
        rootAggregationObject.put("rank", "");
        rootAggregationObject.put("childData", rootAggregations);
        rootAggregationObject.put("expanded", false);
        childTaxa.put("rootAggregations", rootAggregationObject);

        childTaxa.put("parent", taxonomy);
        childTaxa.put("rank", childRank);
        childTaxa.put("taxId", 2759);
        childTaxa.put("childData", aggregations);
        childTaxa.put("expanded", false);

        resp.put(rank, childTaxa);
        return resp.toJSONString();
    }

    @Override
    public String getPhylogeneticTree() {
        JSONArray resultList = new JSONArray();
        JSONObject root = new JSONObject();
        try (Session session = driver.session()) {
            String query ="MATCH (parent:Taxonomies {parentId: 0})-[:CHILD]->(child:Taxonomies) "+
                    "WITH child "+
                    "MATCH childPath=(child)-[:CHILD*0..]->(subChild) "+
                    "with childPath  "+
                    ",CASE WHEN subChild:Taxonomies THEN subChild.id END as orderField order by orderField "+
                    "with collect(childPath) as paths "+
                    "CALL apoc.convert.toTree(paths) yield value "+
                    "RETURN value";
            Result result = session.run(query);
//            List<Record> records = result.list();
//            for(Record record: records) {
//                record.keys().forEach(r -> r = "\""+r+"\"");
//            }
//            String response = records.toString();
//            response = response.replaceAll("Record<\\{value:","");
//            response = response.replaceAll(">","");
//            response = response.replaceAll(", ",", \"");
//            response = response.replaceAll(":","\":");
//            response = response.replaceAll("size","\"size");
//            response = response.replaceAll("'","\"");
//            response = response.replaceAll("\"\\{","{");
//            response = response.replaceAll("\" \\{","{");
//            response = response.replaceAll("]}},","]},");
//            response = response.replaceAll("child","children");
//            response = "{'name': 'Eukaryota', 'children':"+response+"}";
//            response = response.replaceAll("'", "\"");
//            response = response.substring(0, response.length() -3);
//            response = response + "]}";

            StringBuilder sb = new StringBuilder();
            while ( result.hasNext() ) {
                Record record = result.next();
                Map<String, Object> map = new HashMap<>();
                map = record.asMap();
                String recordString = new Gson().toJson(map.get("value"));
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(recordString);
                resultList.add(json);
            }
            root.put("id", 1);
            root.put("name", "Eukaryota");
            root.put("parentId", 0);
            root.put("commonName", "eucaryotes");
            root.put("child", resultList);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String response = root.toJSONString();
        response = response.replaceAll("child","children");
        return response;
    }

    private void getNestedOntologyAggregations(StringBuilder sb) {
        sb.append("'aggregations':{");
        sb.append("'filters': { 'nested': { 'path':'taxonomies'},");
        sb.append("'aggs':{");
        sb.append("'class':{'terms':{'field':'taxonomies.class', 'size': 20000}},");
        sb.append("'cohort':{'terms':{'field':'taxonomies.cohort', 'size': 20000}},");
        sb.append("'family':{'terms':{'field':'taxonomies.family', 'size': 20000}},");
        sb.append("'forma':{'terms':{'field':'taxonomies.forma', 'size': 20000}},");
        sb.append("'genus':{'terms':{'field':'taxonomies.genus', 'size': 20000}},");
        sb.append("'infraclass':{'terms':{'field':'taxonomies.infraclass', 'size': 20000}},");
        sb.append("'infraorder':{'terms':{'field':'taxonomies.infraorder', 'size': 20000}},");
        sb.append("'kingdom':{'terms':{'field':'taxonomies.kingdom', 'size': 20000}},");
        sb.append("'order':{'terms':{'field':'taxonomies.order', 'size': 20000}},");
        sb.append("'parvorder':{'terms':{'field':'taxonomies.parvorder', 'size': 20000}},");
        sb.append("'phylum':{'terms':{'field':'taxonomies.phylum', 'size': 20000}},");
        sb.append("'section':{'terms':{'field':'taxonomies.section', 'size': 20000}},");
        sb.append("'series':{'terms':{'field':'taxonomies.series', 'size': 20000}},");
        sb.append("'species':{'terms':{'field':'taxonomies.species', 'size': 20000}},");
        sb.append("'species_group':{'terms':{'field':'taxonomies.species_group', 'size': 20000}},");
        sb.append("'species_subgroup':{'terms':{'field':'taxonomies.species_subgroup', 'size': 20000}},");
        sb.append("'subclass':{'terms':{'field':'taxonomies.subclass', 'size': 20000}},");
        sb.append("'subcohort':{'terms':{'field':'taxonomies.subcohort', 'size': 20000}},");
        sb.append("'subfamily':{'terms':{'field':'taxonomies.subfamily', 'size': 20000}},");
        sb.append("'subgenus':{'terms':{'field':'taxonomies.subgenus', 'size': 20000}},");
        sb.append("'subkingdom':{'terms':{'field':'taxonomies.subkingdom', 'size': 20000}},");
        sb.append("'suborder':{'terms':{'field':'taxonomies.suborder', 'size': 20000}},");
        sb.append("'subphylum':{'terms':{'field':'taxonomies.subphylum', 'size': 20000}},");
        sb.append("'subsection':{'terms':{'field':'taxonomies.subsection', 'size': 20000}},");
        sb.append("'subspecies':{'terms':{'field':'taxonomies.subspecies', 'size': 20000}},");
        sb.append("'subtribe':{'terms':{'field':'taxonomies.subtribe', 'size': 20000}},");
        sb.append("'superclass':{'terms':{'field':'taxonomies.superclass', 'size': 20000}},");
        sb.append("'superfamily':{'terms':{'field':'taxonomies.superfamily', 'size': 20000}},");
        sb.append("'superkingdom':{'terms':{'field':'taxonomies.superkingdom', 'size': 20000}},");
        sb.append("'superorder':{'terms':{'field':'taxonomies.superorder', 'size': 20000}},");
        sb.append("'superphylum':{'terms':{'field':'taxonomies.superphylum', 'size': 20000}},");
        sb.append("'tribe':{'terms':{'field':'taxonomies.tribe', 'size': 20000}},");
        sb.append("'varietas':{'terms':{'field':'taxonomies.varietas', 'size': 20000}}");
    }

    private static final String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }


    private String postRequest(String baseURL, String body) {
        CloseableHttpClient client = HttpClients.createDefault();
        StringEntity entity = null;
        String resp = "";
        try {
            HttpPost httpPost = new HttpPost(baseURL);
            entity = new StringEntity(body);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", getBasicAuthenticationHeader(esUsername, esPassword));
            CloseableHttpResponse rs = client.execute(httpPost);
            resp = IOUtils.toString(rs.getEntity().getContent(), StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    private String[] taxonomyArray() {
        String[] arr = {"cellularorganism", "superkingdom", "kingdom", "subkingdom", "superphylum", "phylum", "subphylum", "superclass", "class", "subclass", "infraclass", "cohort", "subcohort", "superorder", "order", "parvorder", "suborder", "infraorder", "section", "subsection", "superfamily", "family", "subfamily", "tribe", "subtribe", "genus", "series", "subgenus", "species_group", "species_subgroup", "species", "subspecies", "varietas", "forma"};
        return arr;
    }

    private String findChildRank(String rank) {
        String[] rankArray = {"cellularorganism", "superkingdom", "kingdom", "subkingdom", "superphylum", "phylum", "subphylum", "superclass", "class", "subclass", "infraclass", "cohort", "subcohort", "superorder", "order", "parvorder", "suborder", "infraorder", "section", "subsection", "superfamily", "family", "subfamily", "tribe", "subtribe", "genus", "series", "subgenus", "species_group", "species_subgroup", "species", "subspecies", "varietas", "forma"};
        int currIndex = Arrays.asList(rankArray).indexOf(rank);
        if(!rank.equals("forma"))
            return rankArray[currIndex + 1];
        else
            return "forma";
    }
}
