package com.mmrath.sapp.repository.data;

import com.mmrath.sapp.domain.data.TableDef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableDefRepository extends JpaRepository<TableDef, Long> {

    Optional<TableDef> findByCodeName(String codeName);
}
