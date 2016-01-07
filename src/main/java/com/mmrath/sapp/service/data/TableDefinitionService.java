package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.TableDefinition;
import com.mmrath.sapp.repository.data.ColumnDefinitionRepository;
import com.mmrath.sapp.repository.data.TableDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TableDefinitionService {

    @Autowired
    private ColumnDefinitionRepository columnDefinitionRepository;

    @Autowired
    private TableDefinitionRepository tableDefinitionRepository;

    public void save(TableDefinition tableDefinition) {
        tableDefinitionRepository.save(tableDefinition);
    }

    public void delete(TableDefinition tableDefinition) {
        tableDefinitionRepository.delete(tableDefinition);
    }

}
