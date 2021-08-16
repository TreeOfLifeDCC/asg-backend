package com.asg.platform.statusUpdate.service.Impl;

import com.asg.platform.statusUpdate.model.StatusUpdateDTO;
import com.asg.platform.statusUpdate.service.StatusUpdateService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StatusUpdateServiceImpl implements StatusUpdateService {

    @Value("${ES_CONNECTION_URL}")
    String esConnectionURL;

    final static Logger logger = LoggerFactory.getLogger(StatusUpdateService.class);


    public String updateOrganismTrackingStatus(MultipartFile multipartFile) throws IOException {
        List<StatusUpdateDTO> statusTrackingDTOList = null;
        List<String> failedStatuses = new ArrayList<>();

        File file = new File("src/main/resources/status-update.json");
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(multipartFile.getBytes());
            os.close();

            ObjectMapper objectMapper = new ObjectMapper();
            statusTrackingDTOList = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, StatusUpdateDTO.class));
            for(StatusUpdateDTO statusUpdateDTO: statusTrackingDTOList) {
                JSONObject statusObj = getOrganismFromIndex(statusUpdateDTO.getOrganism(), "statuses_index");
                String statusResp = updateStatusesIndexWithStatus(statusObj, statusUpdateDTO);

                JSONObject orgObj = getOrganismFromIndex(statusUpdateDTO.getOrganism(), "data_portal_index");
                String orgResp = updateDataPortalIndexWithStatus(orgObj, statusUpdateDTO);

                if(statusResp.contains("error") || orgResp.contains("error"))
                    failedStatuses.add(statusUpdateDTO.getOrganism());

                if(!failedStatuses.isEmpty())
                    writeFailedStatusesUpdateToFile(failedStatuses);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return "status updated";
    }

    private void writeFailedStatusesUpdateToFile(List<String> failedStatuses) {
        File file = new File("src/main/resources/failed-status-update.json");
        try {
            FileWriter os = new FileWriter(file);
            for(String str:failedStatuses) {
                os.write(str);
            }
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String findOrganismByNameQuery(String organism) {
        StringBuilder sb = new StringBuilder();
        sb.append("{'query': {");
        sb.append("'bool': { 'should': [");
        sb.append("{'terms' : { 'organism':['"+organism+"']}}");
        sb.append("]}}}");
        String query = sb.toString().replaceAll("'", "\"");
        return query;
    }

    private JSONObject getOrganismFromIndex(String organism, String index) throws ParseException {
        String respString = null;
        String query = this.findOrganismByNameQuery(organism);
        respString = this.postRequest("http://" + esConnectionURL + "/"+index+"/_search", query);
        JSONObject res = (JSONObject) new JSONParser().parse(respString);
        JSONArray respArray = ((JSONArray) ((JSONObject) res.get("hits")).get("hits"));
        JSONObject responseObj = respArray.size() > 0 ? (JSONObject) ((JSONObject) respArray.get(0)).get("_source") : new JSONObject();
        return responseObj;
    }

    private String updateDataPortalIndexWithStatus(JSONObject object, StatusUpdateDTO statusTrackingDTO) {
        String respString = null;
        if(statusTrackingDTO.getStatus().toLowerCase().equals("annotation_complete")) {
            JSONObject trackStatus = (JSONObject) object.get("trackingSystem");
            trackStatus.put("status","Annotation Complete");
            trackStatus.put("rank",3);
            object.put("trackingSystem",trackStatus);
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("assemblies")) {
            JSONObject trackStatus = (JSONObject) object.get("trackingSystem");
            trackStatus.put("status","Assemblies - Submitted");
            trackStatus.put("rank",2);
            object.put("trackingSystem",trackStatus);
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("biosamples")) {
            JSONObject trackStatus = (JSONObject) object.get("trackingSystem");
            trackStatus.put("status","Submitted to Biosamples");
            trackStatus.put("rank",1);
            object.put("trackingSystem",trackStatus);
        }
        String urlEncodedOrganismName = object.get("organism").toString().replaceAll(" ", "%20");
        respString = this.postRequest("http://" + esConnectionURL + "/data_portal_index/_doc/"+urlEncodedOrganismName, object.toJSONString());
        return respString;
    }

    private String updateStatusesIndexWithStatus(JSONObject object, StatusUpdateDTO statusTrackingDTO) {
        String respString = null;
        if(statusTrackingDTO.getStatus().toLowerCase().equals("biosamples")) {
            object.put("biosamples","Done");
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("raw_data")) {
            object.put("raw_data","Done");
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("mapped_reads")) {
            object.put("mapped_reads","Done");
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("assemblies")) {
            object.put("assemblies","Done");
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("annotation")) {
            object.put("annotation","Done");
        }
        else if(statusTrackingDTO.getStatus().toLowerCase().equals("annotation_complete")) {
            object.put("annotation_complete","Done");
        }
        String urlEncodedOrganismName = object.get("organism").toString().replaceAll(" ", "%20");
        respString = this.postRequest("http://" + esConnectionURL + "/statuses_index/_doc/"+urlEncodedOrganismName, object.toJSONString());
        return respString;
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
}
