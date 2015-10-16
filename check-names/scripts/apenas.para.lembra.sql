--
-- INDICES
--

CREATE INDEX ON films ((lower(title)));

CREATE INDEX dq_validacao_gbif_idx ON public.occurrence_raw(dq_validacao_gbif);
CREATE INDEX dq_validacao_listaflorabrasil_idx ON public.occurrence_raw(dq_validacao_listaflorabrasil);

CREATE INDEX kingdom_idx ON occurrence_raw (kingdom);

REINDEX TABLE public.occurrence_raw;


CREATE INDEX scientificname_idx ON listaflora.lista_da_flora_brasil(scientificname);
REINDEX TABLE listaflora.lista_da_flora_brasil;

-----------------------------------------------
--
-- Conexão com o banco de dados
--
psql -U dbadmin -d dataportal

-----------------------------------------------

select count(*) from public.occurrence_raw where taxonrank is not null;
select count(*) from public.occurrence_raw where taxonrank 'SPECIES';

--
-- Como fazer dump no postgresql
--
copy (select scientificname, taxonrank from public.occurrence_raw where taxonrank = 'SPECIES' and taxonrank is not null LIMIT 100) to '/tmp/occur.sql';

pg_dump -t copy_occurrence_raw -U dbadmin dataportal --insert > /tmp/copy_occurrence_raw.sql
pg_dump -t occurrence_fiocruz -U dbadmin dataportal --insert > /tmp/occurrence_fiocruz.sql 

--
-- DIVERSOS SELECTS
--

select count(*) from quality.validacao_ortografica;
select count(*) from quality.cache_dataquality;

select count(*) from public.occurrence_raw where dq_validacao_gbif = true;

select count(*) from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL';

select count(*) from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL' and is_nome_encontrado = true;

select * from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL' LIMIT 100;

delete from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL';

--
-- updates da validação gbif
-- Executar sempre que finalizar uma validação de nomes pelo api gbif
--
update public.occurrence_raw set dq_validacao_gbif = true where dq_validacao_gbif = false and dwcaid in (select distinct dwca_id from quality.validacao_ortografica);
update public.occurrence_raw set dq_validacao_listaflorabrasil = true where dq_validacao_listaflorabrasil = false and dwcaid in (select distinct dwca_id from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL');	



select count(*) from listaflora.LISTA_DA_FLORA_BRASIL;

ALTER TABLE listaflora.LISTA_DA_FLORA_BRASIL OWNER TO dbadmin;

-- http://stackoverflow.com/questions/9736085/run-a-postgresql-sql-file-using-command-line-args
psql -U username -d myDataBase -a -f myInsertFile

psql -U dbadmin -d dataportal -W -a -f dataquality-sequences.sql
psql -U dbadmin -d dataportal -W -a -f validacao-ortografica.sql
psql -U dbadmin -d dataportal -W -a -f cache_dataquality.sql
psql -U dbadmin -d dataportal -W -a -f listaflora-schema-database-datas.sql

delete from quality.validacao_ortografica;

drop sequence quality.validation_name_seq cascade;
drop table quality.validacao_ortografica;

update public.occurrence_raw set dq_validacao_gbif = false;
update public.occurrence_raw set dq_validacao_gbif = true where dwcaid in (select distinct dwca_id from quality.validacao_ortografica);

---- 
--- criação de esquemas
---
CREATE SCHEMA IF NOT EXISTS test AUTHORIZATION joe;
CREATE SCHEMA IF NOT EXISTS quality AUTHORIZATION dbadmin;

====================================================================
--delete from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL';
--update quality.occurrence_raw set dq_validacao_gbif = false;
--update quality.occurrence_raw set dq_validacao_listaflorabrasil = false;

SELECT auto_id, dwcaid, kingdom, phylum,_class, _order, family, genus, scientificname, taxonrank FROM quality.occurrence_raw ORDER BY auto_id LIMIT 5 OFFSET 5;
SELECT auto_id, dwcaid, kingdom, phylum,_class, _order, family, genus, scientificname, taxonrank FROM quality.occurrence_raw WHERE dq_validacao_gbif = false LIMIT 5 OFFSET 0;
SELECT auto_id, dwcaid, kingdom, phylum,_class, _order, family, genus, scientificname, taxonrank FROM quality.occurrence_raw WHERE dq_validacao_gbif = false ORDER BY auto_id LIMIT 5;
SELECT auto_id, dwcaid, kingdom, phylum,_class, _order, family, genus, scientificname, taxonrank FROM quality.occurrence_raw WHERE dq_validacao_gbif = false LIMIT 5;
===================================================================



-- commit;
-- delete from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL';

select count(1) from quality.validacao_ortografica where fonte_avaliadora = 'CATÁLOGO DA FLORA DO BRASIL';
select count(1) from quality.validacao_ortografica where fonte_avaliadora = 'BACKBONE TAXONÔMICO DO GBIF';
-- kingdom, phylum,_class, _order, family, genus, scientificname
select count(kingdom) from quality.occurrence_raw;
select count(phylum) from quality.occurrence_raw where phylum <> '';
select count(_class) from quality.occurrence_raw where _class <> '';
select count(_order) from quality.occurrence_raw where _order <> '';
select count(family) from quality.occurrence_raw where family <> '';
select count(genus) from quality.occurrence_raw where genus <> '';
select count(scientificname) from quality.occurrence_raw where scientificname <> '';
select count(scientificname) from quality.occurrence_raw where scientificname = '';
select * from quality.occurrence_raw where scientificname = '';
select count(*) from quality.occurrence_raw;

select count(phylum), count(_class), count(_order), count(family), count(genus), count(scientificname) from quality.occurrence_raw;

select count(classificacao_superior) from quality.validacao_ortografica where classificacao_superior is not null;
select count(*) from quality.validacao_ortografica where classificacao_superior is not null;
=============================

mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp \
                -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false \
                -DgroupId=br.gov.sibbr -DartifactId=dataquality-services -Dpackage=br.gov.sibbr.services \
                -DarchetypeVersion=2.13






java -classpath /home/francisco/qualidade/bin:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/deploy.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/charsets.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/jce.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/management-agent.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/plugin.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/javaws.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/resources.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/rt.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/jfr.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/jfxrt.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/jsse.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/ext/zipfs.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/ext/localedata.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/ext/sunpkcs11.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/ext/dnsns.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/ext/sunec.jar:/home/francisco/qualidade/jdk1.7.0_71/jre/lib/ext/sunjce_provider.jar:/home/francisco/qualidade/lib/json-simple-1.1.1.jar:/home/francisco/qualidade/lib/logback-classic-1.1.2.jar:/home/francisco/qualidade/lib/logback-core-1.1.2.jar:/home/francisco/qualidade/lib/slf4j-api-1.7.7.jar:/home/francisco/qualidade/lib/postgresql-9.2-1004.jdbc4.jar:/home/francisco/qualidade/lib/guava-18.0.jar br.gov.sibbr.Main &