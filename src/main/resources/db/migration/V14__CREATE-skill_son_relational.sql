CREATE TABLE skill_son_relational
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    deleted      BOOLEAN                                 NOT NULL,
    skill_id     BIGINT                                  NOT NULL,
    skill_son_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_skill_son_relational PRIMARY KEY (id)
);

ALTER TABLE skill_son_relational
    ADD CONSTRAINT uc_ce734366a30a4725bc6d0b40e UNIQUE (skill_id, skill_son_id);

ALTER TABLE skill_son_relational
    ADD CONSTRAINT FK_SKILL_SON_RELATIONAL_ON_SKILL FOREIGN KEY (skill_id) REFERENCES skill (id);

ALTER TABLE skill_son_relational
    ADD CONSTRAINT FK_SKILL_SON_RELATIONAL_ON_SKILL_SON FOREIGN KEY (skill_son_id) REFERENCES skill_son (id);