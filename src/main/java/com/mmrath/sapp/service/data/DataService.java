package com.mmrath.sapp.service.data;

import com.mmrath.sapp.domain.data.TableDef;
import com.mmrath.sapp.repository.data.TableDefRepository;
import com.mmrath.sapp.service.data.sql.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Component
public class DataService {

    @Autowired
    private TableDefRepository tableDefRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DataRepository dataRepository;

    @PostConstruct
    public void init() {
        dataRepository = new DataRepository(jdbcTemplate);
    }

    public long count(String tableDefId) {
        return dataRepository.count(getTableDefByCodeName(tableDefId));
    }

    public int delete(String alias, Serializable id) {
        return dataRepository.delete(getTableDefByCodeName(alias), id);
    }

    public void deleteAll(String tableAlias, Iterable<? extends Serializable> ids) {
        dataRepository.delete(getTableDefByCodeName(tableAlias), ids);
    }

    public boolean exists(String tableAlias, Serializable id) {
        return dataRepository.exists(getTableDefByCodeName(tableAlias), id);
    }

    public List<Map<String, Object>> findAll(String tableAlias) {
        return dataRepository.findAll(getTableDefByCodeName(tableAlias));
    }

    public Map<String, Object> findOne(String alias, Serializable id) {
        return dataRepository.findOne(getTableDefByCodeName(alias), id);
    }

    public Map<String, Object> create(String alias, Map<String, Object> values) {
        TableDef table = getTableDefByCodeName(alias);
        DataValidator.validate(table, values, true);
        return dataRepository.create(table, values);
    }

    public Map<String, Object> update(String alias, Map<String, Object> values) {
        TableDef table = getTableDefByCodeName(alias);
        DataValidator.validate(table, values, false);
        return dataRepository.update(getTableDefByCodeName(alias), values);
    }

    public Page<Map<String, Object>> findAll(String alias, Pageable page) {
        return dataRepository.findAll(getTableDefByCodeName(alias), null, page);
    }

    private TableDef getTableDefByCodeName(String alias) {
        return tableDefRepository.findByCodeName(alias).get();
    }
}
