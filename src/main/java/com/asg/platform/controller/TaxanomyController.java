package com.asg.platform.controller;

import com.asg.platform.es.service.TaxanomyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@ApiIgnore
@RestController
@RequestMapping("/taxonomy")
@Api(tags = "Eukaryota Taxonomies", description = "Controller for Taxonomy")
public class TaxanomyController {

    @Autowired
    TaxanomyService taxanomyService;

    @ApiOperation(value = "View a list of Eukaryota child Taxanomies")
    @RequestMapping(value = "/{rank}/child", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getChildTaxonomyRank(@ApiParam(example = "Submitted to BioSamples") @RequestParam("filter") Optional<String> filter,
                                                       @ApiParam(example = "superkingdom") @PathVariable("rank") String rank,
                                                       @ApiParam(example = "Eukaryota") @RequestParam("taxonomy") String taxonomy,
                                                       @ApiParam(example = "subkingdom") @RequestParam("childRank") String childRank,
                                                       @ApiParam(example = "data") @RequestParam("type") String type,
                                                       @ApiParam(example = "Salmo") @RequestParam(value = "searchText", required = false) Optional<String> search,
                                                       @ApiParam(example = "[{\"rank\":\"superkingdom\",\"taxonomy\":\"Eukaryota\",\"childRank\":\"kingdom\"}]") @RequestBody String taxaTree) throws ParseException {
        String resp = taxanomyService.getChildTaxonomyRank(search, filter, rank, taxonomy, childRank, taxaTree, type);
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Taxonomy Filters for Root Organisms")
    @RequestMapping(value = "/filters", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTaxonomyFilters(@RequestParam("taxonomy") Optional<String> taxonomy) throws ParseException {
        String resp = taxanomyService.getTaxonomicRanksAndCounts(taxonomy);
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }

    @CrossOrigin()
    @ApiOperation(value = "Get Phylogenetic Tree")
    @RequestMapping(value = "/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPhylogeneticTree() throws ParseException {
        String resp = taxanomyService.getPhylogeneticTree();
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
}
