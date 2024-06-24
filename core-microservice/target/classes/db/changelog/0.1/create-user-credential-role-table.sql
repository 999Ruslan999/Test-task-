CREATE TABLE IF NOT EXISTS user_credential_role
(
    user_credential_id
    UUID
    NOT
    NULL,
    roles
    VARCHAR
(
    255
) DEFAULT 'USER',
    CONSTRAINT ch_user_credential_role CHECK
(
    roles
    IN
(
    'USER',
    'MODERATOR'
))
    );
