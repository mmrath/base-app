CREATE TABLE t_user_credential
  (
     expiry_date      TIMESTAMP NOT NULL,
     invalid_attempts INTEGER NOT NULL,
     locked           BOOLEAN NOT NULL,
     password         VARCHAR(255),
     salt             VARCHAR(255),
     user_id          BIGINT NOT NULL,
     PRIMARY KEY (user_id)
  );

ALTER TABLE t_user_credential
  ADD CONSTRAINT fk_user_credential_01 FOREIGN KEY (user_id) REFERENCES t_user;
