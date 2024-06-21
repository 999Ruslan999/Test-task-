CREATE TABLE IF NOT EXISTS refresh_token (
	id UUID,
	token VARCHAR(64),
	expires TIMESTAMP,
	user_credential_id UUID,
	CONSTRAINT pk_refresh_token_id PRIMARY KEY (id),
	CONSTRAINT uq_refresh_token_token UNIQUE (token),
	CONSTRAINT uq_refresh_token_user_credential_id UNIQUE (user_credential_id),
	CONSTRAINT fk_refresh_token_user_credential_id FOREIGN KEY (user_credential_id) REFERENCES user_credential(id)
);
