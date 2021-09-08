package com.asg.platform.controller;

import com.asg.platform.es.service.OrganismService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/organisms")
@Api(tags = "Secondary Organisms", description = "Controller for Secondary Organisms")
public class SecondaryOrganismsController {

    @Autowired
    OrganismService organismService;

    @ApiOperation(value = "Get Secondary Organism By Accession")
    @RequestMapping(value = "/{accession}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSecondaryOrganismByAccession(@ApiParam(example = "SAMEA8104413") @PathVariable("accession") String accession) {
        String rs = organismService.getOrganismByAccession(accession);
        return new ResponseEntity<String> (rs, HttpStatus.OK);
    }

    @ApiIgnore
    @ApiOperation(value = "Get Filters for Secondary Organisms")
    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, JSONArray>> getSecondaryOrganismFilters(@ApiParam(example = "SAMEA8104413") @RequestParam(name = "accession") String accession) throws ParseException {
        Map<String, JSONArray> resp = organismService.getSpecimensFilters(accession);
        return new ResponseEntity<Map<String, JSONArray>>(resp, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Specimen By Accession")
    @RequestMapping(value = "/specimen/{accession}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSpecimenByAccession(@ApiParam(example = "SAMEA8104413") @PathVariable("accession") String accession) {
        String rs = organismService.getSpecimenByAccession(accession);
        return new ResponseEntity<String> (rs, HttpStatus.OK);
    }

}
