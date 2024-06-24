CREATE TABLE IF NOT EXISTS user_credential
(
    id
    UUID,
    client_id
    UUID
    NOT
    NULL,
    username
    VARCHAR
(
    32
) NOT NULL,
    password VARCHAR
(
    64
) NOT NULL,
    CONSTRAINT pk_user_credential_id PRIMARY KEY
(
    id
),
    CONSTRAINT uq_user_credential_client_id UNIQUE
(
    client_id
),
    CONSTRAINT uq_user_credential_username UNIQUE
(
    username
)
    );
