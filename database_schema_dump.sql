--
-- PostgreSQL database dump
--

-- Dumped from database version 14.15 (Debian 14.15-1.pgdg120+1)
-- Dumped by pg_dump version 14.13 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: ingredient; Type: TABLE; Schema: public; Owner: quarkus
--

CREATE TABLE public.ingredient (
    id uuid NOT NULL,
    name character varying(255)
);


ALTER TABLE public.ingredient OWNER TO quarkus;

--
-- Name: recipe; Type: TABLE; Schema: public; Owner: quarkus
--

CREATE TABLE public.recipe (
    id uuid NOT NULL,
    name character varying(255),
    youtube character varying(255)
);


ALTER TABLE public.recipe OWNER TO quarkus;

--
-- Name: recipeingredient; Type: TABLE; Schema: public; Owner: quarkus
--

CREATE TABLE public.recipeingredient (
    amount integer NOT NULL,
    ingredient uuid NOT NULL,
    recipe uuid NOT NULL,
    unit character varying(255)
);


ALTER TABLE public.recipeingredient OWNER TO quarkus;

--
-- Name: ingredient ingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: quarkus
--

ALTER TABLE ONLY public.ingredient
    ADD CONSTRAINT ingredient_pkey PRIMARY KEY (id);


--
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: quarkus
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- Name: recipeingredient recipeingredient_pkey; Type: CONSTRAINT; Schema: public; Owner: quarkus
--

ALTER TABLE ONLY public.recipeingredient
    ADD CONSTRAINT recipeingredient_pkey PRIMARY KEY (ingredient, recipe);


--
-- PostgreSQL database dump complete
--

