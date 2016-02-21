create table IF NOT EXISTS test_table1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    not_nullable_varchar_col VARCHAR(32) NOT NULL,
    nullable_varchar_col VARCHAR(32),
    null_date_col DATE,
    notnull_date_col DATE NOT NULL,
    null_datetime_col TIMESTAMP,
    notnull_datetime_col TIMESTAMP NOT NULL,
    nullable_number_col INTEGER,
    notnull_number_col INTEGER NOT NULL,
    nullable_boolean_col BIT,
    notnull_boolean_col BIT NOT NULL,
    created_date TIMESTAMP not null,
    created_by VARCHAR(32) not null,
    last_modified_by VARCHAR(32) not null,
    last_modified_date TIMESTAMP not null,
    version INTEGER not null
);