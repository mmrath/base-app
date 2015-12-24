CREATE TABLE t_user_role
  (
     user_id BIGINT NOT NULL,
     role_id BIGINT NOT NULL
  );

ALTER TABLE t_user_role
  ADD CONSTRAINT fk_user_role_01 FOREIGN KEY (role_id) REFERENCES t_role;

ALTER TABLE t_user_role
  ADD CONSTRAINT fk_user_role_02 FOREIGN KEY (user_id) REFERENCES t_user;
