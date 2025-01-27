-- Created by running pg_dump -U quarkus -p 32785 -d quarkus -h localhost --schema-only > database_schema_dump.sql
-- on a version of the application running in dev mode with auto ddl.

CREATE TABLE public.ingredient
(
    id   uuid NOT NULL,
    name character varying(255)
);

CREATE TABLE public.recipe
(
    id      uuid NOT NULL,
    name    character varying(255),
    youtube character varying(255)
);

CREATE TABLE public.recipeingredient
(
    amount     integer NOT NULL,
    ingredient uuid    NOT NULL,
    recipe     uuid    NOT NULL,
    unit       character varying(255)
);
