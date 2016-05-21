package com.mmrath.sapp.web.rest.data;


import com.mmrath.sapp.service.data.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataResource {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/{codeName}/{id}", method = RequestMethod.GET)
    public Map<String, Object> findOne(@PathVariable("codeName") String alias, @PathVariable("id") Long id) {
        logger.trace("findOne, table alias {}, id: {}", alias, id);
        return dataService.findOne(alias, id);
    }

    @RequestMapping(value = "/{codeName}", method = RequestMethod.GET)
    public Page<Map<String, Object>> findAll(@PathVariable("codeName") String alias, Pageable pageRequest) {
        logger.trace("findAll, table alias {}, page request: {}", alias, pageRequest);
        return dataService.findAll(alias, pageRequest);
    }

    @RequestMapping(value = "/{codeName}/{id}", method = RequestMethod.POST)
    public Map<String, Object> create(@PathVariable("codeName") String alias, @RequestBody Map<String, Object> value) {
        logger.trace("create, table alias {}, value: {}", alias, value);
        return dataService.create(alias, value);
    }

    @RequestMapping(value = "/{codeName}/{id}", method = RequestMethod.PUT)
    public Map<String, Object> update(@PathVariable("codeName") String alias, @RequestBody Map<String, Object> value) {
        logger.trace("create, table alias {}, value: {}", alias, value);
        return dataService.update(alias, value);
    }

    @RequestMapping(value = "/{codeName}/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("codeName") String alias, @PathVariable("id") Long id) {
        logger.trace("create, table alias {}, id: {}", alias, id);
        dataService.delete(alias, id);
    }
}
