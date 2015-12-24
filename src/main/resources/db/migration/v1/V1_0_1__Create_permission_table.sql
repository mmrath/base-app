CREATE TABLE t_permission 
  ( 
     id              BIGINT NOT NULL,
     name            VARCHAR(128) NOT NULL,
     description     VARCHAR(512) NOT NULL,
     PRIMARY KEY (id) 
  ); 

ALTER TABLE t_permission
  ADD CONSTRAINT uk_permission_01 UNIQUE (NAME);

