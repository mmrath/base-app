package com.mmrath.sapp.service.data;

import com.mmrath.sapp.AbstractIntegrationTest;
import com.mmrath.sapp.domain.data.TableDef;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
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
        values.put("notNullableVarcharCol", "Test");
        values.put("notnullDateCol", new Date());
        values.put("notnullDatetimeCol", new Date());
        values.put("notnullNumberCol", 10);
        values.put("notnullBooleanCol", false);
        values = dataService.create(table.getAlias(), values);
        assertThat(values.get(table.getPrimaryKeyColumn().getName())).isNotNull();
    }

    @Test
    public void testFindAll() {
        TableDef table = prepareTableDef("test_table1");
        Map<String, Object> values = new HashMap<>();
        values.put("TEST_COLUMN", "Test");
        values.put("notNullableVarcharCol", "Test");
        values.put("notnullDateCol", new Date());
        values.put("notnullDatetimeCol", new Date());
        values.put("notnullNumberCol", 10);
        values.put("notnullBooleanCol", false);

        values = dataService.create(table.getAlias(), values);

        values.put("notnullNumberCol", 20);
        values = dataService.update(table.getAlias(), values);

        assertThat(values.get(table.getPrimaryKeyColumn().getName())).isNotNull();
        assertThat(values.get(table.getVersionColumn().getName())).isEqualTo(2L);

        Page<Map<String, Object>> page = dataService.findAll(table.getAlias(), new PageRequest(0, 20));
        assertThat(page.getTotalElements()).isEqualTo(1L);

        dataService.delete(table.getAlias(), (Long) values.get(table.getPrimaryKeyColumn().getName()));
        page = dataService.findAll(table.getAlias(), new PageRequest(0, 20));
        assertThat(page.getTotalElements()).isEqualTo(0L);
    }


    private TableDef prepareTableDef(String test_table1) {
        TableDef tableDef = tableDefService.getTableDefFromDb(test_table1).get();
        tableDefService.save(tableDef);
        return tableDef;
    }

}