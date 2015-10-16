--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2014-11-18 02:02:09 BRST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = quality, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 178 (class 1259 OID 17932)
-- Name: gbif_cache_names; Type: TABLE; Schema: quality; Owner: -; Tablespace:
--

CREATE TABLE quality.gbif_cache_names (
    id integer NOT NULL,
    nome_cientifico character varying(250),
    taxon_rank character varying(25),
    canonical_name character varying(250),
    classificacao_superior character varying(500),
    is_sinonimo boolean
);


--
-- TOC entry 2002 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN gbif_cache_names.id; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN gbif_cache_names.id IS 'Identificador único do registro.';


--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN gbif_cache_names.canonical_name; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN gbif_cache_names.canonical_name IS 'Nome científico sem o autor.';


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 178
-- Name: COLUMN gbif_cache_names.classificacao_superior; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN gbif_cache_names.classificacao_superior IS 'Classificação superior do taxon.';


--
-- TOC entry 179 (class 1259 OID 17938)
-- Name: cache_names_gbif_id_seq; Type: SEQUENCE; Schema: quality; Owner: -
--

CREATE SEQUENCE cache_names_gbif_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 179
-- Name: cache_names_gbif_id_seq; Type: SEQUENCE OWNED BY; Schema: quality; Owner: -
--

ALTER SEQUENCE cache_names_gbif_id_seq OWNED BY gbif_cache_names.id;


--
-- TOC entry 1884 (class 2604 OID 17940)
-- Name: id; Type: DEFAULT; Schema: quality; Owner: -
--

ALTER TABLE ONLY gbif_cache_names ALTER COLUMN id SET DEFAULT nextval('cache_names_gbif_id_seq'::regclass);


--
-- TOC entry 1886 (class 2606 OID 17942)
-- Name: cache_name_gbif_pk; Type: CONSTRAINT; Schema: quality; Owner: -; Tablespace:
--

ALTER TABLE ONLY gbif_cache_names
    ADD CONSTRAINT cache_name_gbif_pk PRIMARY KEY (id);


--
-- TOC entry 1889 (class 2606 OID 17944)
-- Name: gbif_cache_names_uk; Type: CONSTRAINT; Schema: quality; Owner: -; Tablespace:
--

ALTER TABLE ONLY gbif_cache_names
    ADD CONSTRAINT gbif_cache_names_uk UNIQUE (nome_cientifico, canonical_name, taxon_rank);


--
-- TOC entry 1887 (class 1259 OID 17945)
-- Name: canonical_name_idx; Type: INDEX; Schema: quality; Owner: -; Tablespace:
--

CREATE INDEX canonical_name_idx ON gbif_cache_names USING btree (canonical_name, taxon_rank);


--
-- TOC entry 1890 (class 1259 OID 17946)
-- Name: nome_cientifico_idx; Type: INDEX; Schema: quality; Owner: -; Tablespace:
--

CREATE INDEX nome_cientifico_idx ON gbif_cache_names USING btree (nome_cientifico, taxon_rank);


-- Completed on 2014-11-18 02:02:09 BRST

--
-- PostgreSQL database dump complete
--




ALTER TABLE quality.gbif_cache_names OWNER TO dbadmin;