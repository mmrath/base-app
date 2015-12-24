CREATE TABLE t_role 
  ( 
     id          BIGINT NOT NULL, 
     name        VARCHAR(30) NOT NULL,
     description VARCHAR(64) NOT NULL,
     create_time TIMESTAMP NOT NULL,
     created_by  VARCHAR(255) NOT NULL,
     update_time TIMESTAMP NOT NULL,
     updated_by  VARCHAR(255) NOT NULL,
     version     INTEGER,
     PRIMARY KEY (id)
  ); 

ALTER TABLE t_role 
  ADD CONSTRAINT uk_role_01 UNIQUE (NAME); 

CREATE TABLE t_role_permission 
  ( 
     role_id       BIGINT NOT NULL, 
     permission_id BIGINT NOT NULL 
  ); 

ALTER TABLE t_role_permission 
  ADD CONSTRAINT fk_role_permission_01 FOREIGN KEY (permission_id) REFERENCES 
  t_permission; 

ALTER TABLE t_role_permission 
  ADD CONSTRAINT fk_role_permission_02 FOREIGN KEY (role_id) REFERENCES t_role; 