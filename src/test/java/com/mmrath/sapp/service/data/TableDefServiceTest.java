package com.mmrath.sapp.service.data;

import com.mmrath.sapp.AbstractIntegrationTest;
import com.mmrath.sapp.domain.data.TableDef;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;


public class TableDefServiceTest extends AbstractIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(TableDefServiceTest.class);
    @Autowired
    private TableDefService tableDefService;

    @Test
    public void testGetColumnDefFromDb() throws Exception {
        TableDef tableDef = tableDefService.getTableDefFromDb("T_ROLE").get();
        logger.info("Columns:{}", tableDef);
        assertThat(tableDef.getName()).matches("T_ROLE");
        assertThat(tableDef.getColumns().size()).isGreaterThan(0);
        assertThat(tableDef.getAlias()).matches("role");
        assertThat(tableDef.getDisplayLabel()).matches("Role");
    }
}