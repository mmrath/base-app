CREATE TABLE t_user
  (
     id          BIGINT NOT NULL,
     email       VARCHAR(64) NOT NULL,
     enabled     SMALLINT NOT NULL,
     first_name  VARCHAR(255),
     last_name   VARCHAR(255),
     username    VARCHAR(30) NOT NULL,
     create_time TIMESTAMP NOT NULL,
     created_by  VARCHAR(255) NOT NULL,
     update_time TIMESTAMP NOT NULL,
     updated_by  VARCHAR(255) NOT NULL,
     version     INTEGER,
     PRIMARY KEY (id),
  );

ALTER TABLE t_user
  ADD CONSTRAINT uk_user_01 UNIQUE (email);

ALTER TABLE t_user
  ADD CONSTRAINT uk_user_02 UNIQUE (username);

