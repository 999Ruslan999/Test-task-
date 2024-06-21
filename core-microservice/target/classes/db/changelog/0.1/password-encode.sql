CREATE EXTENSION IF NOT EXISTS pgcrypto;

UPDATE user_credential SET password = crypt(password, gen_salt('bf'));