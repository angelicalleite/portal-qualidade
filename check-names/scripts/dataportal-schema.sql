--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2014-10-11 17:47:13 BRT

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 16386)
-- Name: quality; Type: SCHEMA; Schema: -; Owner: -
--


--
--CRIAÇÃO DE USUÁRIO
--

-- CREATE USER daniele SUPERUSER INHERIT CREATEDB CREATEROLE;
-- ALTER USER daniele PASSWORD '1234';

-- CREATE SCHEMA quality;


SET search_path = quality, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 16387)
-- Name: occurrence_raw; Type: TABLE; Schema: quality; Owner: -
--

CREATE TABLE occurrence_raw (
    auto_id integer NOT NULL,
    dwcaid character varying(75),
    sourcefileid character varying(50) NOT NULL,
    acceptednameusage text,
    acceptednameusageid text,
    accessrights text,
    associatedmedia text,
    associatedoccurrences text,
    associatedreferences text,
    associatedsequences text,
    associatedtaxa text,
    basisofrecord text,
    bed text,
    behavior text,
    bibliographiccitation text,
    catalognumber text,
    _class text,
    collectioncode text,
    collectionid text,
    continent text,
    coordinateprecision text,
    coordinateuncertaintyinmeters text,
    country text,
    countrycode text,
    county text,
    datageneralizations text,
    datasetid text,
    datasetname text,
    dateidentified text,
    day text,
    decimallatitude text,
    decimallongitude text,
    disposition text,
    dynamicproperties text,
    earliestageorloweststage text,
    earliesteonorlowesteonothem text,
    earliestepochorlowestseries text,
    earliesteraorlowesterathem text,
    earliestperiodorlowestsystem text,
    enddayofyear text,
    establishmentmeans text,
    eventdate text,
    eventid text,
    eventremarks text,
    eventtime text,
    family text,
    fieldnotes text,
    fieldnumber text,
    footprintspatialfit text,
    footprintsrs text,
    footprintwkt text,
    formation text,
    genus text,
    geodeticdatum text,
    geologicalcontextid text,
    georeferencedby text,
    georeferenceddate text,
    georeferenceprotocol text,
    georeferenceremarks text,
    georeferencesources text,
    georeferenceverificationstatus text,
    _group text,
    habitat text,
    higherclassification text,
    highergeography text,
    highergeographyid text,
    highestbiostratigraphiczone text,
    identificationid text,
    identificationqualifier text,
    identificationreferences text,
    identificationremarks text,
    identificationverificationstatus text,
    identifiedby text,
    individualcount text,
    individualid text,
    informationwithheld text,
    infraspecificepithet text,
    institutioncode text,
    institutionid text,
    island text,
    islandgroup text,
    kingdom text,
    language text,
    latestageorhigheststage text,
    latesteonorhighesteonothem text,
    latestepochorhighestseries text,
    latesteraorhighesterathem text,
    latestperiodorhighestsystem text,
    lifestage text,
    lithostratigraphicterms text,
    locality text,
    locationaccordingto text,
    locationid text,
    locationremarks text,
    lowestbiostratigraphiczone text,
    maximumdepthinmeters text,
    maximumdistanceabovesurfaceinmeters text,
    maximumelevationinmeters text,
    member text,
    minimumdepthinmeters text,
    minimumdistanceabovesurfaceinmeters text,
    minimumelevationinmeters text,
    modified text,
    month text,
    municipality text,
    nameaccordingto text,
    nameaccordingtoid text,
    namepublishedin text,
    namepublishedinid text,
    namepublishedinyear text,
    nomenclaturalcode text,
    nomenclaturalstatus text,
    occurrenceid text,
    occurrenceremarks text,
    occurrencestatus text,
    _order text,
    originalnameusage text,
    originalnameusageid text,
    othercatalognumbers text,
    ownerinstitutioncode text,
    parentnameusage text,
    parentnameusageid text,
    phylum text,
    pointradiusspatialfit text,
    preparations text,
    previousidentifications text,
    recordedby text,
    recordnumber text,
    _references text,
    reproductivecondition text,
    rights text,
    rightsholder text,
    samplingeffort text,
    samplingprotocol text,
    scientificname text,
    scientificnameauthorship text,
    scientificnameid text,
    sex text,
    specificepithet text,
    startdayofyear text,
    stateprovince text,
    subgenus text,
    taxonconceptid text,
    taxonid text,
    taxonomicstatus text,
    taxonrank text,
    taxonremarks text,
    type text,
    typestatus text,
    verbatimcoordinates text,
    verbatimcoordinatesystem text,
    verbatimdepth text,
    verbatimelevation text,
    verbatimeventdate text,
    verbatimlatitude text,
    verbatimlocality text,
    verbatimlongitude text,
    verbatimsrs text,
    verbatimtaxonrank text,
    vernacularname text,
    waterbody text,
    year text,
    dq_revised boolean DEFAULT false
);


--
-- TOC entry 1872 (class 2606 OID 16398)
-- Name: occurrence_raw_dwcaid_sourcefileid_key; Type: CONSTRAINT; Schema: quality; Owner: -
--

ALTER TABLE ONLY occurrence_raw
    ADD CONSTRAINT occurrence_raw_dwcaid_sourcefileid_key UNIQUE (dwcaid, sourcefileid);


--
-- TOC entry 1874 (class 2606 OID 16400)
-- Name: occurrence_raw_pkey; Type: CONSTRAINT; Schema: quality; Owner: -
--

ALTER TABLE ONLY occurrence_raw
    ADD CONSTRAINT occurrence_raw_pkey PRIMARY KEY (auto_id);


-- Completed on 2014-10-11 17:47:13 BRT

--
-- PostgreSQL database dump complete
--

