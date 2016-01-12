package com.mmrath.sapp.service.data;

import com.mmrath.sapp.AbstractIntegrationTest;
import com.mmrath.sapp.domain.data.ColumnDef;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TableDefServiceTest extends AbstractIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(TableDefServiceTest.class);
    @Autowired
    private TableDefService tableDefService;

    @Test
    public void testGetColumnDefFromDb() throws Exception {
        List<ColumnDef> columnDefs = tableDefService.getColumnDefFromDb("T_ROLE");
        logger.info("Columns:{}", columnDefs);
    }
}