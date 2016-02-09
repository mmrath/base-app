package com.mmrath.sapp.service.data;

import com.mmrath.sapp.AbstractIntegrationTest;
import com.mmrath.sapp.domain.data.TableDef;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class DataServiceTest extends AbstractIntegrationTest {

    @Autowired
    private TableDefService tableDefService;

    @Autowired
    private DataService dataService;


    @Test
    public void testSave() {
        TableDef table = prepareTableDef("test_table1");
        Map<String, Object> values = new HashMap<>();
        values.put("TEST_COLUMN", "Test");
        values = dataService.create(table.getId(), values);
        assertThat(values.get(table.getPrimaryKeyColumn().getName())).isNotNull();
    }

    @Test
    public void testFindAll() {
        TableDef table = prepareTableDef("test_table1");
        Map<String, Object> values = new HashMap<>();
        values.put("TEST_COLUMN", "Test");
        values = dataService.create(table.getId(), values);
        assertThat(values.get(table.getPrimaryKeyColumn().getName())).isNotNull();

        Page<Map<String, Object>> page = dataService.findAll(table.getId(), new PageRequest(0, 20));

    }


    private TableDef prepareTableDef(String test_table1) {
        TableDef tableDef = tableDefService.getTableDefFromDb(test_table1).get();
        tableDefService.save(tableDef);
        return tableDef;
    }

}