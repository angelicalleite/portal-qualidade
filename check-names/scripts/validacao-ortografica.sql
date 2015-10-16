--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2014-11-17 20:48:51 BRST

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
-- TOC entry 174 (class 1259 OID 16820)
-- Name: validacao_ortografica; Type: TABLE; Schema: quality; Owner: -; Tablespace: 
--

CREATE TABLE quality.validacao_ortografica (
    id integer NOT NULL,
    dwca_id character varying(75) NOT NULL,
    nome_cientifico character varying(250),
    taxon_rank character varying(25),
    fonte_avaliadora character varying(250),
    is_nome_fornecido boolean,
    is_nome_encontrado boolean,
    is_sinonimo boolean,
    sinonimo_de character varying(250),
    classificacao_superior character varying(500)
);


--
-- TOC entry 1998 (class 0 OID 0)
-- Dependencies: 174
-- Name: TABLE validacao_ortografica; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON TABLE validacao_ortografica IS 'Validação ortográfica do nome fornecido no registro de ocorrência';


--
-- TOC entry 1999 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.dwca_id; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.dwca_id IS 'Identificador do darwin core.';


--
-- TOC entry 2000 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.nome_cientifico; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.nome_cientifico IS 'Nome a ser validado ortograficamente.';


--
-- TOC entry 2001 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.taxon_rank; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.taxon_rank IS 'Classificação taxonômica do nome.
Valores possíveis:
- REINO
- FILO
- CLASSE
- ORDEM
- FAMILIA
- GENERO
- ESPECIE';


--
-- TOC entry 2002 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.fonte_avaliadora; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.fonte_avaliadora IS 'Base de dados onde o nome foi submetido para validação ortográfica.
Fontes consideradas:
- BACKBONE BONE TAXONÔMICO DO GBIF
- FLORA DO BRASIL
- EOL (FUTURO)
- OUTRAS NO FUTURO';


--
-- TOC entry 2003 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.is_nome_encontrado; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.is_nome_encontrado IS 'Indica se o nome foi encontrado na fonte avaliadora.';


--
-- TOC entry 2004 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.is_sinonimo; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.is_sinonimo IS 'Verificar se o nome é sinônimo.
VALORES: TRUE ou FALSE';


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.sinonimo_de; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.sinonimo_de IS 'De quem o nome é sinônimo.';


--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.classificacao_superior; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.classificacao_superior IS 'Classificação superior do nome.
A hierarquia taxonômica.';


--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.is_nome_fornecido; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.is_nome_fornecido IS 'Indicar se o nome foi fornecido pelo publicador.
Será utilizado em conjunto com o RANK para dizer qual nome não foi fornecido.';


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 174
-- Name: COLUMN validacao_ortografica.id; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON COLUMN validacao_ortografica.id IS 'Surrogate key';


--
-- TOC entry 178 (class 1259 OID 17615)
-- Name: validacao_ortografica_id_seq; Type: SEQUENCE; Schema: quality; Owner: -
--

CREATE SEQUENCE validacao_ortografica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 178
-- Name: validacao_ortografica_id_seq; Type: SEQUENCE OWNED BY; Schema: quality; Owner: -
--

ALTER SEQUENCE validacao_ortografica_id_seq OWNED BY validacao_ortografica.id;


--
-- TOC entry 1881 (class 2604 OID 17617)
-- Name: id; Type: DEFAULT; Schema: quality; Owner: -
--

ALTER TABLE ONLY validacao_ortografica ALTER COLUMN id SET DEFAULT nextval('validacao_ortografica_id_seq'::regclass);


--
-- TOC entry 1884 (class 2606 OID 17628)
-- Name: validacao_ortografica_pk; Type: CONSTRAINT; Schema: quality; Owner: -; Tablespace: 
--

ALTER TABLE ONLY validacao_ortografica
    ADD CONSTRAINT validacao_ortografica_pk PRIMARY KEY (id);


--
-- TOC entry 1886 (class 2606 OID 17605)
-- Name: validacao_ortografica_uk; Type: CONSTRAINT; Schema: quality; Owner: -; Tablespace: 
--

ALTER TABLE ONLY validacao_ortografica
    ADD CONSTRAINT validacao_ortografica_uk UNIQUE (dwca_id, nome_cientifico, taxon_rank, fonte_avaliadora);


--
-- TOC entry 1882 (class 1259 OID 16936)
-- Name: dwca_id_idx; Type: INDEX; Schema: quality; Owner: -; Tablespace: 
--

CREATE INDEX dwca_id_idx ON validacao_ortografica USING btree (dwca_id);


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 1882
-- Name: INDEX dwca_id_idx; Type: COMMENT; Schema: quality; Owner: -
--

COMMENT ON INDEX dwca_id_idx IS 'Índice na coluna dwca_id para melhorar a pesquisa';


-- Completed on 2014-11-17 20:48:51 BRST

--
-- PostgreSQL database dump complete
--

