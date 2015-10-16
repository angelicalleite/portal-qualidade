portal-qualidade
================

Ferramenta de qualidade de dados do portal de dados de biodiversidade do SiBBr.

## dataquality-services
Serviços de verificação da qualidade dos dados de ocorrências de espécies, implementados através de API RESTful.

### Tecnologias e arquitetura
* [JAX-RS API] (https://jersey.java.net)
* [FLEXJSON] (http://flexjson.sourceforge.net)
* [MyBatis] (https://mybatis.github.io/mybatis-3/)
* [Simple Logging Facade for Java (SLF4J)] (http://www.slf4j.org)
* [Logback] (http://logback.qos.ch)
* [PostgreSQL] (http://www.postgresql.org)
* [tomcat servlet container] (http://tomcat.apache.org)
* [Maven build tool] (https://maven.apache.org)
* [Java SE 8] (http://www.oracle.com/technetwork/java/javase/overview/index.html)

### Como consumir os serviço de verificação de nomes

http://<host[:porta]>/dataquality-services/api/ocorrencia/busca/dwca_id

Este serviço retornará um objeto json contendo o resultado do serviço de vericação de nomes.

## check-names
Processo de verificação de nomes científicos em catálogos e fontes de referência.

### Nomes verificados
Atualmente, o serviço verifica nome científico com rank = espécie

### Fontes de referência
* [GBIF Species API] (http://www.gbif.org/developer/species)
* [Brazilian Flora Checklist - Lista de Espécies da Flora do Brasil] (http://ipt.jbrj.gov.br/jbrj/resource?r=lista_especies_flora_brasil)

### Tecnologias e arquitetura
Aplicação Java
* [Google guava] (https://github.com/google/guava)
* [Simple Logging Facade for Java (SLF4J)] (http://www.slf4j.org)
* [Logback] (http://logback.qos.ch)
* [json-simple] (https://github.com/fangyidong/json-simple)
* [Java SE 8] (http://www.oracle.com/technetwork/java/javase/overview/index.html)
 
### Trabalhos futuros e melhorias
* Validar outros ranks;
* Utilizar full-text search, utilizando ferramentas como Elasticsearch, Solr ou PostgreSQL
