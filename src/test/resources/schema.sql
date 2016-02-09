create table IF NOT EXISTS test_table1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    test_column VARCHAR(32) not null,
    created_date TIMESTAMP not null,
    created_by VARCHAR(32) not null,
    last_modified_by VARCHAR(32) not null,
    last_modified_date TIMESTAMP not null,
    version INTEGER not null
);