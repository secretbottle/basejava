CREATE TABLE IF NOT EXISTS resume
(
    uuid VARCHAR(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS contact
(
    id SERIAL NOT NULL
        CONSTRAINT contact_pk PRIMARY KEY,
    resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type TEXT NOT NULL,
    value TEXT NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

CREATE TABLE IF NOT EXISTS section
(
    id SERIAL NOT NULL
        CONSTRAINT section_pk PRIMARY KEY,
    resume_uuid VARCHAR(36) NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type TEXT NOT NULL,
    content TEXT NOT NULL
);

CREATE UNIQUE INDEX section_uuid_type_index
    ON section (resume_uuid, type);


