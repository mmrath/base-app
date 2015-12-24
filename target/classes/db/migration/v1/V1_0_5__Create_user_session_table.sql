CREATE TABLE t_user_session
  (
     session_id      VARCHAR(255) NOT NULL,
     auto_signed_off BOOLEAN,
     end_time        TIMESTAMP,
     start_time      TIMESTAMP NOT NULL,
     user_id         BIGINT NOT NULL,
     PRIMARY KEY (session_id)
  );

ALTER TABLE t_user_session
  ADD CONSTRAINT fk_user_session_01 FOREIGN KEY (user_id) REFERENCES t_user;