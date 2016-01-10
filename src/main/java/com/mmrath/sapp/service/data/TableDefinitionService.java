package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.TableDef;
import com.mmrath.sapp.repository.data.ColumnDefRepository;
import com.mmrath.sapp.repository.data.TableDefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableDefinitionService {

    @Autowired
    private ColumnDefRepository columnDefRepository;

    @Autowired
    private TableDefRepository tableDefRepository;

    public void save(TableDef tableDef) {
        tableDefRepository.save(tableDef);
    }

    public void delete(TableDef tableDef) {
        tableDefRepository.delete(tableDef);
    }

}
