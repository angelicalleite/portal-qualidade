package br.gov.sibbr;

import br.gov.sibbr.utils.DataQualityExcpetion;
import br.gov.sibbr.utils.DataQualityUtils;
import br.gov.sibbr.utils.Rank;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BD {

    private static Logger logger = LoggerFactory.getLogger(BD.class);
    private int contaTotalDeRegistros = 0;
    private int totalRegistrosOcorrenciasNoBD = 0;

    private int totalDeIteracoesNecessarias = 0;
    private final int limit = 500;
    private int offset = limit * totalDeIteracoesNecessarias + 1;

    private int iteracaoAtual = -1;
    private boolean existeMaisIteracao = false;


    private Connection connection;
    private ResultSet resultSet;

    // produção
/*
    private final String BASE_SQL_SELECT_LISTA_FLORA = "SELECT SCIENTIFICNAME, PHYLUM, CLASSNAME, ORDERNAME, FAMILYNAME, GENUS, TAXONRANK, TAXONOMICSTATUS, NOMENCLATURALSTATUS, HIGHERCLASSIFICATION, ACCEPTEDNAMEUSAGE FROM listaflora.lista_da_flora_brasil WHERE (UPPER(SCIENTIFICNAME) LIKE UPPER(?) AND TAXONRANK = ?)";
    private final String BASE_SQL_UPDATE_OCCURRENCE_GBIF = "UPDATE public.occurrence_raw SET dq_validacao_gbif = true WHERE dwcaid = ?";
    private final String BASE_SQL_UPDATE_OCCURRENCE_FLORABR = "UPDATE public.occurrence_raw SET dq_validacao_listaflorabrasil = true WHERE dwcaid = ?";
*/

    private final String user = "dbadmin";
    private final String password = "dbadmin@bacilo";

    // desenvolvimento

    private final String SQL_CONTA_OCORRENCIAS = "SELECT COUNT(*) TOTAL FROM public.occurrence_raw WHERE dq_validacao_listaflorabrasil = false AND taxonrank IN ('SPECIES', 'ESPECIE', 'EspÈcie')";
    private final String SQL_SELECT_OCORRENCIAS = "SELECT auto_id, dwcaid, kingdom, scientificname, taxonrank FROM public.occurrence_raw WHERE taxonrank IS NOT NULL AND %s = false AND kingdom = 'Plantae' AND taxonrank IN ('SPECIES', 'ESPECIE', 'EspÈcie') ORDER BY auto_id LIMIT ? OFFSET ?";

    private final String SQL_INSERT_VALIDACOES = "INSERT INTO quality.validacao_ortografica (dwca_id, nome_cientifico, taxon_rank, fonte_avaliadora, is_nome_fornecido, is_nome_encontrado, is_sinonimo, sinonimo_de, classificacao_superior) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String SQL_INSERT_CACHE = "INSERT INTO quality.gbif_cache_names (nome_cientifico, taxon_rank, canonical_name, classificacao_superior, is_sinonimo) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_SELECT_CACHE = "SELECT nome_cientifico, taxon_rank, canonical_name, classificacao_superior, is_sinonimo FROM quality.gbif_cache_names WHERE (nome_cientifico = ? AND taxon_rank = ?) OR (taxon_rank = ? AND canonical_name LIKE ?)";



    //private final String SQL_SELECT_OCORRENCIAS = "SELECT dwcaid, kingdom, phylum,_class, _order, family, genus, scientificname, taxonrank FROM quality.occurrence_raw WHERE %s = false ORDER BY auto_id LIMIT ? OFFSET ?";
    //private final String BASE_SQL_INSERT_LISTA = "INSERT INTO quality.validacao_ortografica (dwca_id, nome, rank, fonte_avaliadora, is_encontrado, is_sinonimo, sinonimo_de, classificacao_superior, nivel_taxonomico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //private final String BASE_SQL_SELECT_LISTA_FLORA = "SELECT SCIENTIFICNAME, PHYLUM, CLASSNAME, ORDERNAME, FAMILYNAME, GENUS, TAXONRANK, TAXONOMICSTATUS, NOMENCLATURALSTATUS, HIGHERCLASSIFICATION, ACCEPTEDNAMEUSAGE FROM listaflora.lista_da_flora_brasil WHERE (UPPER(SCIENTIFICNAME) LIKE UPPER(?) AND TAXONRANK = ?)";
    private final String BASE_SQL_SELECT_LISTA_FLORA = "SELECT SCIENTIFICNAME, PHYLUM, CLASSNAME, ORDERNAME, FAMILYNAME, GENUS, TAXONRANK, TAXONOMICSTATUS, NOMENCLATURALSTATUS, HIGHERCLASSIFICATION, ACCEPTEDNAMEUSAGE FROM listaflora.lista_da_flora_brasil WHERE (UPPER(SCIENTIFICNAME) LIKE UPPER(?) AND TAXONRANK = ?)";

    // não serão utilizados
    private final String BASE_SQL_UPDATE_OCCURRENCE_GBIF = "UPDATE quality.occurrence_raw SET dq_validacao_gbif = true WHERE dwcaid = ?";
    private final String BASE_SQL_UPDATE_OCCURRENCE_FLORABR = "UPDATE quality.occurrence_raw SET dq_validacao_listaflorabrasil = true WHERE dwcaid = ?";



    //private final String password = "1234";
    //private final String user = "daniele";




    // jdbc:postgresql://host:port/database
    private final String PG_DATABASE_CONNECT = "jdbc:postgresql://%s:%s/%s";

    // Configuration - daniele (localhost):
    private final String host = "localhost";
    private final String port = "5432";
    private final String database = "dataportal";



    public BD() {
        try {
            abreConexao();
        } catch (DataQualityExcpetion e) {
            logger.error("Exception: {}", e);
        }
    }

	/*
     * String server = "146.134.8.135"; String port = "5432"; String database =
	 * "dataportal"; String user = "dbadmin"; String password = "dbadmin";
	 */

    private void abreConexao() throws DataQualityExcpetion {
        try {
            if (connection == null || connection.isClosed()) {
                final String url = String.format(PG_DATABASE_CONNECT, host, port, database);
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, password);
                logger.info("Connection Established");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error("Exception: {}", e);
            throw new DataQualityExcpetion("Falha na abertura da conexão com o banco de dados");
        }
    }

    private void fechaResultSet() throws DataQualityExcpetion {


    }

    public void fechaConexao() {
        logger.debug("----> fechou a coneão");
        try {
            fechaResultSet();
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Conexão com o BD foi fechada");
            }
        } catch (SQLException | DataQualityExcpetion e) {
            logger.error("Exception: {}", e);
        }
    }


    public void gravarResultadosAvaliacao(List<ResultadoAvaliacaoNome> resultados) {
        try {
            //abreConexao();
            NomeCientifico nome = null;
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_VALIDACOES);
            ResultadoAvaliacaoNome resultado = null;
            for (ResultadoAvaliacaoNome resultadoAvaliacao : resultados) {
                nome = resultadoAvaliacao.getNomeAvaliado();
// dwca_id, nome_cientifico, taxon_rank, fonte_avaliadora, is_nome_fornecido, is_nome_encontrado, is_sinonimo, sinonimo_de, classificacao_superior
// dwca_id, nome_cientifico, taxon_rank, fonte_avaliadora, is_nome_fornecido, is_nome_encontrado, is_sinonimo, sinonimo_de, classificacao_superior

                // dwca_id
                ps.setString(1, nome.getDwcaId());
                // nome_cientifico
                ps.setString(2, nome.getNome());
                // taxon_rank
                ps.setString(3, nome.getTaxonRank());
                // fonte_avaliadora
                ps.setString(4, resultadoAvaliacao.getFonteAvaliadora());
                // is_nome_fornecido boolean
                ps.setBoolean(5, resultadoAvaliacao.isNomeFornecido());
                // is_nome_encontrado boolean
                ps.setBoolean(6, resultadoAvaliacao.isNomeEncontrado());
                // is_sinonimo
                ps.setBoolean(7, resultadoAvaliacao.isSinonimo());
                // sinonimo_de
                ps.setString(8, resultadoAvaliacao.getSinonimoDe());
                // classificacao_superior
                ps.setString(9, nome.getClassificacaoSuperior());

                ps.addBatch();
            }


            int[] totalInserido = ps.executeBatch();

            // fazer alguma coisa se falhar
            if (totalInserido.length == 0) {
                logger.error("A gravação em batch não foi bem sucedida!");
            }
        } catch (SQLException e) {
            logger.error("Falha na gravação dos resultados da avaliação: {}, {}", e, e.getNextException());
        }
    }

    public void gravarResultadosNoCache(Set<GBIFCacheNames> nomes) {
        try {
            //abreConexao();
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CACHE);
            for (GBIFCacheNames nome : nomes) {
               // if (resultadoAvaliacao.isNomeEncontrado()) {

                GBIFCacheNames s = estaNoCacheNomesGBIF(nome.buildNomeCientifico());
                // s == null: nome não está no cache
                    if (s == null || !s.estaNoCache()) {
                        // nome_cientifico
                        ps.setString(1, nome.getNomeCientifico());
                        // taxon_rank
                        ps.setString(2, nome.getTaxonRank());
                        // canonical_name
                        ps.setString(3, nome.getCanonicalName());
                        // classificacao_superior
                        ps.setString(4, nome.getClassificacaoSuperior());
                        // is_sinonimo
                        ps.setBoolean(5, nome.isSinonimo());
                        ps.addBatch();
                    }
                //}
            }

            int[] totalInserido = ps.executeBatch();

        } catch (SQLException e) {
            logger.error("Falha na inserção do cache dataquality: {}", e, e.getNextException());
        } catch (DataQualityExcpetion e) {
            logger.error("Falha na inserção do cache dataquality - DataQualityExcpetion: {}", e);
        }
    }

    public void marcarRegistroValidado(Set<NomeCientifico> registro, String fonteAvaliadora) {
        try {

            //abreConexao();

            PreparedStatement ps = null;

            if (fonteAvaliadora.equals(DataQualityUtils.FONTE_VALIDACAO_GBIF)) {
                ps = connection.prepareStatement(BASE_SQL_UPDATE_OCCURRENCE_GBIF);
            } else if (fonteAvaliadora.equals(DataQualityUtils.FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL)) {
                ps = connection.prepareStatement(BASE_SQL_UPDATE_OCCURRENCE_FLORABR);
            } else {
                throw new IllegalArgumentException("Mande a fonte, né?");
            }

            for (NomeCientifico nome : registro) {
                // dwca_id
                ps.setString(1, nome.getDwcaId());
                ps.addBatch();
            }
            int[] totalInserido = ps.executeBatch();
            if (totalInserido.length == 0) {
                logger.error("Falha na atualização dos registros validados");
            }
        } catch (SQLException e) {
            logger.error("Falha na atualização dos registros validados: {}", e, e.getNextException());
        }
    }

    // necessario pq os primeiros registros nao sao plantas
    public boolean existeMaisIteracao() {
        return ((iteracaoAtual < totalDeIteracoesNecessarias) && (contaTotalDeRegistros < totalRegistrosOcorrenciasNoBD));
    }

    // Nomes para avaliação pela lista da flora
    // todo: separar a validacao dos nomes não fornecidos. IDEIA: ter um processo que valida somente nomes fornecidos e outro que avalia os nomes não fornecidos
    public List<NomeCientifico> getNomesParaAvaliacaoListaFlora(String fonteAvaliadora) {
        List<NomeCientifico> nomes = new ArrayList<NomeCientifico>();

        try {

            preparaTotalRegistrosOcorrenciasBD();
            preparaTotalDeIteracoes();
            //existeMaisIteracao = (iteracaoAtual < totalDeIteracoesNecessarias) && (contaTotalDeRegistros % limit == 0 && contaTotalDeRegistros < totalRegistrosOcorrenciasNoBD);

            existeMaisIteracao = ((iteracaoAtual < totalDeIteracoesNecessarias) && (contaTotalDeRegistros < totalRegistrosOcorrenciasNoBD));

            logger.debug("--> iteracaoAtual: {}", iteracaoAtual);
            logger.debug("--> totalDeIteracoesNecessarias: {}", totalDeIteracoesNecessarias);
            logger.debug("--> contaTotalDeRegistros: {}", contaTotalDeRegistros);
            logger.debug("--> totalRegistrosOcorrenciasNoBD: {}", totalRegistrosOcorrenciasNoBD);
            logger.debug("--> existeMaisIteracao: {}", existeMaisIteracao);

            if (existeMaisIteracao) {
                iteracaoAtual++;
                preparaOffset(iteracaoAtual);
                carregaRegistrosDeOcorrencias(fonteAvaliadora);
            }

            while (resultSet.next()) {
                contaTotalDeRegistros++;
                //Não é possível validar TODOS_OS_REINOS pela lista da flora
				/*NomeCientifico reino = new NomeCientifico();
				reino.setDwcaId(resultSet.getString("dwcaid"));
				reino.setNome(resultSet.getString("kingdom"));
				reino.setTaxonRank(Rank.REINO.getNivelTaxonomicoFloraBR());
				if (!Strings.isNullOrEmpty(reino.getNome())) {
					nomes.add(reino);
				}*/
/*
                NomeCientifico filo = new NomeCientifico();
                filo.setDwcaId(resultSet.getString("dwcaid"));
                filo.setNome(resultSet.getString("phylum"));
                filo.setTaxonRank(Rank.FILO.getNivelTaxonomicoFloraBR());
                if (!Strings.isNullOrEmpty(filo.getNome())) {
                    nomes.add(filo);
                }

                NomeCientifico classe = new NomeCientifico();
                classe.setDwcaId(resultSet.getString("dwcaid"));
                classe.setNome(resultSet.getString("_class"));
                classe.setTaxonRank(Rank.CLASSE.getNivelTaxonomicoFloraBR());
                if (!Strings.isNullOrEmpty(classe.getNome())) {
                    nomes.add(classe);
                }

                NomeCientifico ordem = new NomeCientifico();
                ordem.setDwcaId(resultSet.getString("dwcaid"));
                ordem.setNome(resultSet.getString("_order"));
                ordem.setTaxonRank(Rank.ORDEM.getNivelTaxonomicoFloraBR());
                if (!Strings.isNullOrEmpty(ordem.getNome())) {
                    nomes.add(ordem);
                }

                NomeCientifico familia = new NomeCientifico();
                familia.setDwcaId(resultSet.getString("dwcaid"));
                familia.setNome(resultSet.getString("family"));
                familia.setTaxonRank(Rank.FAMILIA.getNivelTaxonomicoFloraBR());
                if (!Strings.isNullOrEmpty(familia.getNome())) {
                    nomes.add(familia);
                }

                NomeCientifico genero = new NomeCientifico();
                genero.setDwcaId(resultSet.getString("dwcaid"));
                genero.setNome(resultSet.getString("genus"));
                genero.setTaxonRank(Rank.GENERO.getNivelTaxonomicoFloraBR());
                if (!Strings.isNullOrEmpty(genero.getNome())) {
                    nomes.add(genero);
                }
*/
                // Validando somente os nomes fornecidos.
                // Os nomes não fornecidos já foram validados no processo de validação da API do gbif
                // obter somente os nomes de plantas
                if (!Strings.isNullOrEmpty(resultSet.getString("kingdom"))
                        && resultSet.getString("kingdom").equals("Plantae")
                        && !Strings.isNullOrEmpty(resultSet.getString("scientificname"))) {

                    NomeCientifico especie = new NomeCientifico();
                    especie.setDwcaId(resultSet.getString("dwcaid"));
                    especie.setNome(resultSet.getString("scientificname"));
                    especie.setTaxonRank(Rank.ESPECIE.getNivelTaxonomicoFloraBR());
                    //if (!Strings.isNullOrEmpty(especie.getNome())) {
                        nomes.add(especie);
                    //}
                }

            }

        } catch (SQLException | DataQualityExcpetion e) {
            logger.debug("Exception: {}", e);
        }

        // o resultset não possui mais registros
        if (nomes.isEmpty()) {
            // fechar resultset
            // desfazer registros carregados
            //registrosCarregados = false;
            //totalDeIteracoesNecessarias++;
            logger.info("Total de registros: {}", contaTotalDeRegistros);
        }

        return nomes;
    }

    public List<NomeCientifico> getNomesParaAvaliacao(String fonteAvaliadora) throws DataQualityExcpetion{
        List<NomeCientifico> nomes = new ArrayList<NomeCientifico>();

        try {

            // todo: pense em mover esses metodos para o construtor - executará uma única vez
            preparaTotalRegistrosOcorrenciasBD();
            preparaTotalDeIteracoes();
            //existeMaisIteracao = ((iteracaoAtual < totalDeIteracoesNecessarias) && (contaTotalDeRegistros % limit == 0 && contaTotalDeRegistros < totalRegistrosOcorrenciasNoBD));

            existeMaisIteracao = ((iteracaoAtual < totalDeIteracoesNecessarias) && (contaTotalDeRegistros < totalRegistrosOcorrenciasNoBD));

            //logger.info("Iteração atual: {}", iteracaoAtual);
            //logger.info("Total de iterações necessárias: {}", totalDeIteracoesNecessarias);
            //logger.info("contado de registros no banco: {}", contaTotalDeRegistros);
            //logger.info("Total de registros no bd: {}", totalRegistrosOcorrenciasNoBD);


            if (existeMaisIteracao) {
                iteracaoAtual++;
                preparaOffset(iteracaoAtual);
                carregaRegistrosDeOcorrencias(fonteAvaliadora);
            }

            while (resultSet.next()) {
                contaTotalDeRegistros++;

                /*
                NomeCientifico reino = new NomeCientifico();
                reino.setDwcaId(resultSet.getString("dwcaid"));
                reino.setNome(resultSet.getString("kingdom"));
                reino.setTaxonRank(Rank.REINO.getNivelTaxonomicoGBIF());
                nomes.add(reino);

                NomeCientifico filo = new NomeCientifico();
                filo.setDwcaId(resultSet.getString("dwcaid"));
                filo.setNome(resultSet.getString("phylum"));
                filo.setTaxonRank(Rank.FILO.getNivelTaxonomicoGBIF());
                nomes.add(filo);

                NomeCientifico classe = new NomeCientifico();
                classe.setDwcaId(resultSet.getString("dwcaid"));
                classe.setNome(resultSet.getString("_class"));
                classe.setTaxonRank(Rank.CLASSE.getNivelTaxonomicoGBIF());
                nomes.add(classe);

                NomeCientifico ordem = new NomeCientifico();
                ordem.setDwcaId(resultSet.getString("dwcaid"));
                ordem.setNome(resultSet.getString("_order"));
                ordem.setTaxonRank(Rank.ORDEM.getNivelTaxonomicoGBIF());
                nomes.add(ordem);

                NomeCientifico familia = new NomeCientifico();
                familia.setDwcaId(resultSet.getString("dwcaid"));
                familia.setNome(resultSet.getString("family"));
                familia.setTaxonRank(Rank.FAMILIA.getNivelTaxonomicoGBIF());
                nomes.add(familia);

                NomeCientifico genero = new NomeCientifico();
                genero.setDwcaId(resultSet.getString("dwcaid"));
                genero.setNome(resultSet.getString("genus"));
                genero.setTaxonRank(Rank.GENERO.getNivelTaxonomicoGBIF());
                nomes.add(genero);
                */

                // retorno da consulta: dwcaid, scientificname, taxonrank
                NomeCientifico especie = new NomeCientifico();
                especie.setDwcaId(resultSet.getString("dwcaid"));
                especie.setNome(resultSet.getString("scientificname"));
                especie.setTaxonRank(Rank.ESPECIE.getNivelTaxonomicoGBIF());
                nomes.add(especie);
            }

        } catch (SQLException e) {
            logger.error("Exception: {}", e);
            throw new DataQualityExcpetion("Falha na recuperação dos registros dos nomes científicos padrão GBIF.");
        } catch (DataQualityExcpetion e) {
            throw e;
        }

        // o resultset não possui mais registros
        if (nomes.isEmpty()) {
            // fechar resultset
            // desfazer registros carregados
            //registrosCarregados = false;
            //totalDeIteracoesNecessarias++;
            logger.info("Total de registros: {}", contaTotalDeRegistros);
        }

        return nomes;
    }


    /*
     * Prepara o deslocamento para buscar os proximos n registros
     *
     */
    private void preparaOffset(int interacao) {
        offset = limit * interacao;
    }

    /*
     * Prepara o numero de iteracoes que serao necessarias para executar queries no banco de dados
     */
    private void preparaTotalDeIteracoes() {

        //logger.debug("totalDeIteracoesNecessarias: {}", totalDeIteracoesNecessarias);
        // totalizou
        if (totalDeIteracoesNecessarias > 0) {
            return;
        }
        totalDeIteracoesNecessarias = (int) Math.ceil(new Double(totalRegistrosOcorrenciasNoBD).doubleValue() / limit);
    }

    private void preparaTotalRegistrosOcorrenciasBD() throws DataQualityExcpetion {

        //logger.debug("totalRegistrosOcorrenciasNoBD: {}", totalRegistrosOcorrenciasNoBD);

        // ja contou
        if (totalRegistrosOcorrenciasNoBD > 0) {
            return;
        }

        try {
            //abreConexao();
            PreparedStatement ps = connection.prepareStatement(SQL_CONTA_OCORRENCIAS);
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                totalRegistrosOcorrenciasNoBD = resultSet.getInt("TOTAL");
            }
            resultSet.close();
            logger.info("Total de registros na base de dados: {}", totalRegistrosOcorrenciasNoBD);
        } catch (SQLException e) {
            logger.error("Exception: {}", e, e.getNextException());
            throw new DataQualityExcpetion("Falha na contagem dos registros de ocorrências. Mais detalhes no log error.");
        }
    }

    private void carregaRegistrosDeOcorrencias(String fonteAvaliadora) throws DataQualityExcpetion {

        if (fonteAvaliadora == null) {
            throw new DataQualityExcpetion("Fonte avaliadora nula");
        }

        //abreConexao();
        //fechaResultSet();
        //preparaTotalRegistrosOcorrenciasBD();
        StringBuilder sql = null;
        if (fonteAvaliadora != null && fonteAvaliadora.equals(DataQualityUtils.FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL)) {
            sql = new StringBuilder(String.format(SQL_SELECT_OCORRENCIAS, "dq_validacao_listaflorabrasil"));
        } else if (fonteAvaliadora != null && fonteAvaliadora.equals(DataQualityUtils.FONTE_VALIDACAO_GBIF)) {
            sql = new StringBuilder(String.format(SQL_SELECT_OCORRENCIAS, "dq_validacao_gbif"));
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            resultSet = ps.executeQuery();
            //registrosCarregados = true;
            logger.info("SQL: {}", ps.toString());
        } catch (SQLException e) {
            logger.debug("Exception: {}", e);
            throw new DataQualityExcpetion("Falha no carregamento dos registros de ocorrências.");
        }
    }

    /**
     * Método para converter o nome de rank, que está em português,
     * para o atributo correspondente na tabela lista_da_flora_brasil
     */
    private String converteRank(String rank) {
        String conversao = null;

        switch (rank) {
            case "REINO":
                conversao = "KINGDON";
                break;
            case "FILO":
                conversao = "PHYLUM";
                break;
            case "CLASSE":
                conversao = "CLASSNAME";
                break;
            case "ORDEM":
                conversao = "ORDERNAME";
                break;
            case "FAMILIA":
                conversao = "FAMILYNAME";
                break;
            case "GENERO":
                conversao = "GENUS";
                break;
            case "ESPECIE":
                conversao = "SCIENTIFICNAME";
                break;
            default:
                break;
        }

        return conversao;
    }

    public ResultSet consultaListaFlora(String nomeCientifico, String rank) throws DataQualityExcpetion {
        //abreConexao();
        ResultSet rs = null;
        try {
            //String sql = BASE_SQL_SELECT_LISTA_FLORA + nomeCientifico + "' AND taxonrank = '" + rank + "'" ;
            //System.out.println("SQL: " + sql);
            //Statement stmt = connection.createStatement();
            //PreparedStatement ps = connection.prepareStatement(BASE_SQL_SELECT_LISTA_FLORA + nomeCientifico + "' AND taxonrank = '" + rank + "'" );
            PreparedStatement ps;
            if (rank.equals("ESPECIE")) {
                //ps = connection.prepareStatement(BASE_SQL_SELECT_LISTA_FLORA);
                ps = connection.prepareStatement("SELECT SCIENTIFICNAME, PHYLUM, CLASSNAME, ORDERNAME, FAMILYNAME, GENUS, TAXONRANK, TAXONOMICSTATUS, NOMENCLATURALSTATUS, HIGHERCLASSIFICATION, ACCEPTEDNAMEUSAGE FROM listaflora.lista_da_flora_brasil WHERE (UPPER(SCIENTIFICNAME) LIKE UPPER(?))");
            } else {
                ps = connection.prepareStatement(BASE_SQL_SELECT_LISTA_FLORA + " or (UPPER(" + converteRank(rank) + ") LIKE UPPER(?))");
            }

            ps.setString(1, nomeCientifico + "%");

            if (!rank.equals("ESPECIE")) {
                ps.setString(2, rank);
                ps.setString(3, nomeCientifico + "%");
            }
            //System.out.println("SQL: " + ps.toString());
            //logger.debug("SQL: {}", ps.toString());
            rs = ps.executeQuery();

        } catch (SQLException e) {
            //System.out.println("Falha na consulta!");
            //e.printStackTrace();
            logger.error("Exception: {}", e);
        }
        return rs;
    }


    //public boolean estaNoCacheNomesGBIF(String nome, String fonte_avaliadora, String rank) throws DataQualityExcpetion {
    public GBIFCacheNames estaNoCacheNomesGBIF(NomeCientifico nome) throws DataQualityExcpetion {
        //abreConexao();
        ResultSet rs = null;
        GBIFCacheNames cache = new GBIFCacheNames();
        try {

            // todo: construir o canonical name
            String[] nomeSplit = nome.getNome().split(" ");
            StringBuilder parteCanonicalName = new StringBuilder(nomeSplit[0]);
            // segunda parte
            if (nomeSplit != null && nomeSplit.length > 1 && Strings.isNullOrEmpty(nomeSplit[1])) {
                parteCanonicalName.append(" ").append(nomeSplit[1]);
            }

            parteCanonicalName.append("%");

            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_CACHE);

            ps.setString(1, nome.getNome());
            ps.setString(2, nome.getTaxonRank());
            ps.setString(3, nome.getTaxonRank());
            ps.setString(4, parteCanonicalName.toString());

            rs = ps.executeQuery();

            if (rs.next()) {
                cache.setCanonicalName(rs.getString("canonical_name"));
                cache.setClassificacaoSuperior(rs.getString("classificacao_superior"));
                cache.setSinonimo(rs.getBoolean("is_sinonimo"));
                cache.setNomeCientifico(rs.getString("nome_cientifico"));
                cache.setTaxonRank(rs.getString("taxon_rank"));
            }
        } catch (SQLException e) {
            logger.error("Falha na consulta ao cache. {}", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }
        return cache;
    }
}
