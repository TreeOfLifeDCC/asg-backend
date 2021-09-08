package com.asg.platform.controller;

import com.asg.platform.es.service.OrganismStatusTrackingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/statuses")
@Api(tags = "Root organism Status Tracking", description = "Controller for Root organism Status Tracking")
public class StatusTrackingController {

    @Autowired
    OrganismStatusTrackingService organismStatusTrackingService;

    @ApiOperation(value = "View a list of Organism Status Tracking")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> getOrganismStatuses(@RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                                                                       @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
                                                                       @RequestParam(name = "sortColumn", required = false) Optional<String> sortColumn,
                                                                       @RequestParam(value = "sortOrder", required = false) Optional<String> sortOrder) throws ParseException {
        HashMap<String, Object> response = new HashMap<>();
        JSONArray resp = organismStatusTrackingService.findAll(offset, limit, sortColumn, sortOrder);
        long count = organismStatusTrackingService.getBiosampleStatusTrackingCount();
        response.put("biosampleStatus", resp);
        response.put("count", count);
        return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

    }

    @ApiOperation(value = "Get Filters for Organism Status Tracking")
    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<JSONObject>> getStatusFilters() throws ParseException {
        return organismStatusTrackingService.getFilters();
    }

    @ApiOperation(value = "Get Search Results for Organism Status Tracking")
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findStatusSearchResults(@ApiParam(example = "lutra") @RequestParam("filter") String filter,
                                                          @RequestParam(name = "from", required = false, defaultValue = "0") Optional<String> from,
                                                          @RequestParam(value = "size", required = false, defaultValue = "20") Optional<String> size,
                                                          @RequestParam(name = "sortColumn", required = false) Optional<String> sortColumn,
                                                          @RequestParam(value = "sortOrder", required = false) Optional<String> sortOrder) {

        String resp = organismStatusTrackingService.findSearchResult(filter, from, size, sortColumn, sortOrder);
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Filtered Results for Organism Status Tracking")
    @RequestMapping(value = "/filter/results", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findStatusFilterResults(@ApiParam(example = "Biosamples - Done") @RequestBody Optional<String> filter,
                                                          @RequestParam(name = "from", required = false, defaultValue = "0") Optional<String> from,
                                                          @RequestParam(value = "size", required = false, defaultValue = "20") Optional<String> size,
                                                          @RequestParam(name = "sortColumn", required = false) Optional<String> sortColumn,
                                                          @RequestParam(value = "sortOrder", required = false) Optional<String> sortOrder,
                                                          @ApiParam(example = "[{\"rank\":\"superkingdom\",\"taxonomy\":\"Eukaryota\",\"childRank\":\"kingdom\"}]") @RequestParam(value = "taxonomyFilter", required = false) Optional<String> taxonomyFilter) throws ParseException {
        String resp = organismStatusTrackingService.findFilterResults(filter, from, size, sortColumn, sortOrder, taxonomyFilter);
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }

    @ApiIgnore
    @ApiOperation(value = "Get Organism Status Tracking")
    @RequestMapping(value = "/organism", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findStatusByOrganism(@ApiParam(example = "lutra") @RequestParam("name") String name,
                                                       @RequestParam(name = "from", required = false, defaultValue = "0") Optional<String> from,
                                                       @RequestParam(value = "size", required = false, defaultValue = "20") Optional<String> size,
                                                       @RequestParam(name = "sortColumn", required = false) Optional<String> sortColumn,
                                                       @RequestParam(value = "sortOrder", required = false) Optional<String> sortOrder) {
        String resp = organismStatusTrackingService.findBioSampleByOrganismByText(name, from, size, sortColumn, sortOrder);
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
}
