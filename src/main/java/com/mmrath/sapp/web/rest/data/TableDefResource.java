package com.mmrath.sapp.web.rest.data;


import com.mmrath.sapp.domain.data.TableDef;
import com.mmrath.sapp.repository.data.TableDefRepository;
import com.mmrath.sapp.service.data.TableDefService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/table")
public class TableDefResource {
    private final Logger logger = LoggerFactory.getLogger(TableDefResource.class);

    private final TableDefRepository tableDefRepository;

    private final TableDefService tableDefService;

    @Autowired
    public TableDefResource(TableDefRepository tableDefRepository,
            TableDefService tableDefService) {
        this.tableDefRepository = tableDefRepository;
        this.tableDefService = tableDefService;
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Page<TableDef> findAll(Pageable pageRequest) {
        return tableDefRepository.findAll(pageRequest);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public TableDef findById(@PathVariable("id") Long id) {
        TableDef TableDef = tableDefRepository.findOne(id);
        return TableDef;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public TableDef create(@RequestBody @Valid TableDef tableDef) {
        logger.info("Input: {}", tableDef);
        tableDef = tableDefRepository.save(tableDef);
        return tableDef;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public TableDef update(@PathVariable("id") Long id, @Valid @RequestBody TableDef tableDef) {
        tableDef = tableDefRepository.save(tableDef);
        return tableDef;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        tableDefRepository.delete(id);
    }

    @RequestMapping(value = "/search/name", method = RequestMethod.GET)
    @ResponseBody
    public TableDef searchByName(@RequestParam("name") String name) {
        return tableDefService.getTableDefFromDb(name).get();
    }

    @RequestMapping(value = "/search/alias", method = RequestMethod.GET)
    @ResponseBody
    public TableDef findByAlias(@RequestParam("alias") String alias) {
        return tableDefRepository.findByAlias(alias).get();
    }


}
