portal-qualidade
================

Ferramenta de qualidade de dados do portal de dados de biodiversidade do SiBBr.

## dataquality-services
Serviços de verificação da qualidade dos dados de ocorrências de espécies, implementados através de API RESTful.

### Tecnologias e arquitetura
* [JAX-RS API] (https://jersey.java.net)
* [FLEXJSON] (http://flexjson.sourceforge.net)
* [MyBatis] (https://mybatis.github.io/mybatis-3/)
* [Logback] (http://logback.qos.ch)
* [PostgreSQL] (http://www.postgresql.org)
* [tomcat servlet container] (http://tomcat.apache.org)
* [Maven build tool] (https://maven.apache.org)

### Como consumir os serviço de verificação de nomes

http://<host[:porta]>/dataquality-services/api/ocorrencia/busca/dwca_id

Este serviço retornará um objeto json contendo o resultado do serviço de vericação de nomes.
