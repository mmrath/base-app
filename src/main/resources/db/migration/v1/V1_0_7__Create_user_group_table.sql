CREATE TABLE t_user_group 
  ( 
     id          BIGINT NOT NULL, 
     create_time TIMESTAMP NOT NULL, 
     created_by  VARCHAR(255) NOT NULL, 
     update_time TIMESTAMP NOT NULL, 
     updated_by  VARCHAR(255) NOT NULL, 
     version     INTEGER, 
     description VARCHAR(64) NOT NULL, 
     NAME        VARCHAR(30) NOT NULL, 
     PRIMARY KEY (id) 
  ); 

CREATE TABLE t_user_group_role 
  ( 
     group_id BIGINT NOT NULL, 
     role_id  BIGINT NOT NULL 
  ); 

ALTER TABLE t_user_group_role 
  ADD CONSTRAINT fk_user_group_role_01 FOREIGN KEY (role_id) REFERENCES t_role; 

ALTER TABLE t_user_group_role 
  ADD CONSTRAINT fk_user_group_role_02 FOREIGN KEY (group_id) REFERENCES 
  t_user_group; 

CREATE TABLE t_user_group_user 
  ( 
     group_id BIGINT NOT NULL, 
     user_id  BIGINT NOT NULL 
  ); 

ALTER TABLE t_user_group_user 
  ADD CONSTRAINT fk_user_group_user_01 FOREIGN KEY (user_id) REFERENCES t_role; 

ALTER TABLE t_user_group_user 
  ADD CONSTRAINT fk_user_group_user_02 FOREIGN KEY (group_id) REFERENCES 
  t_user_group; 