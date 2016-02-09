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


    public long count(long tableDefId) {
        return dataRepository.count(getTableDef(tableDefId));
    }

    public void delete(String alias, Serializable id) {
        dataRepository.delete(getTableDef(alias), id);
    }

    public void deleteAll(long tableId, Iterable<? extends Serializable> ids) {
        dataRepository.delete(getTableDef(tableId), ids);
    }

    public boolean exists(long tableId, Serializable id) {
        return dataRepository.exists(getTableDef(tableId), id);
    }

    public List<Map<String, Object>> findAll(long tableId) {
        return dataRepository.findAll(getTableDef(tableId));
    }

    public Map<String, Object> findOne(long tableId, Serializable id) {
        return dataRepository.findOne(getTableDef(tableId), id);
    }

    public Map<String, Object> findOne(String alias, Serializable id) {
        return dataRepository.findOne(getTableDef(alias), id);
    }

    public Map<String, Object> create(long tableId, Map<String, Object> values) {
        return dataRepository.create(getTableDef(tableId), values);
    }

    public Map<String, Object> create(String alias, Map<String, Object> values) {
        return dataRepository.create(getTableDef(alias), values);
    }

    public Map<String, Object> update(String alias, Map<String, Object> values) {
        return dataRepository.update(getTableDef(alias), values);
    }


    public Page<Map<String, Object>> findAll(long tableId, Pageable page) {
        return dataRepository.findAll(getTableDef(tableId), null, page);
    }

    public Page<Map<String, Object>> findAll(String alias, Pageable page) {
        return dataRepository.findAll(getTableDef(alias), null, page);
    }

    private TableDef getTableDef(long tableId) {
        return tableDefRepository.findOne(tableId);
    }

    private TableDef getTableDef(String alias) {
        return tableDefRepository.findByAlias(alias).get();
    }
}
