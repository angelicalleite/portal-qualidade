package br.gov.sibbr;

import br.gov.sibbr.utils.DataQualityExcpetion;
import br.gov.sibbr.utils.DataQualityUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AvaliacaoNomesCientitificos {

    private static Logger logger = LoggerFactory.getLogger(AvaliacaoNomesCientitificos.class);

    /**
     * A pedido do Danny validaremos somente o nome cientifico da espécie
     * @throws DataQualityExcpetion
     */
    public void avaliarNomesNaAPIdoGBIF() throws DataQualityExcpetion {

        // bd devolve nomes para avaliação
        BD bd = new BD();
        List<NomeCientifico> nomesParaAvaliacao = null;
        //Set<NomeCientifico> nomesAvaliados = new HashSet<NomeCientifico>();

        Set<GBIFCacheNames> nomesParaCache = new HashSet<GBIFCacheNames>();

        List<ResultadoAvaliacaoNome> resultadosDaAvaliacao = new ArrayList<ResultadoAvaliacaoNome>();
        //List<ResultadoAvaliacaoNome> nomesParaCache = new ArrayList<ResultadoAvaliacaoNome>();
        int conta = 0;

        logger.info("====================================================================");

        do {
            if (nomesParaAvaliacao != null && !nomesParaAvaliacao.isEmpty()) {
                nomesParaAvaliacao.clear();
            }
            nomesParaAvaliacao = bd.getNomesParaAvaliacao(DataQualityUtils.FONTE_VALIDACAO_GBIF);
            conta++;

            logger.info("===> Vai no bd: {}", conta);

            // avaliar cada nome contra a API de nomes do GBIF
            for (NomeCientifico nome : nomesParaAvaliacao) {

                ResultadoAvaliacaoNome resultadoAvaliacaoNome = null;

                //logger.info("DWCA_ID: {} - Rank: {} - Nome: {} ", nome.getDwcaId(), nome.getTaxonRank(), nome.getNome());

                boolean isNomeFornecido = !Strings.isNullOrEmpty(nome.getNome());

                // consulta o cache
                // não consultar nomes não fornecidos
                boolean estaNoCache = false;
                GBIFCacheNames gbifCacheNames = null;

                // consulta cache por nome fornecido
                if (isNomeFornecido) {
                    gbifCacheNames = bd.estaNoCacheNomesGBIF(nome);
                    estaNoCache = gbifCacheNames != null && gbifCacheNames.estaNoCache();
                }

                // valida na API do GBIF
                if (!estaNoCache && isNomeFornecido) {
                    try {
                        resultadoAvaliacaoNome = ConsultaGBIF.avaliar(nome);
                        if (resultadoAvaliacaoNome == null) {
                            continue;
                        }

                        // não gravar nomes nulo no cahce
                        //nomesParaCache.add(resultadoAvaliacaoNome);
                        GBIFCacheNames nomeCache = new GBIFCacheNames(nome.getNome(), nome.getCanonicalName(), nome.getTaxonRank(), nome.getClassificacaoSuperior(), resultadoAvaliacaoNome.isSinonimo());
                        nomesParaCache.add(nomeCache);

                    } catch (DataQualityExcpetion e) {
                        logger.error("Exception: {}, {}", e.getMessage(), e.getStackTrace());
                    }
                } else if (estaNoCache && isNomeFornecido) {
                    // necessário completar os dados da avaliação
                    //TODO: codigo duplicado - olhar na validação do gbif
                    resultadoAvaliacaoNome = new ResultadoAvaliacaoNome();
                    resultadoAvaliacaoNome.setNomeAvaliado(nome);
                    //boolean isNomeFornecido = !Strings.isNullOrEmpty(nome.getNome());
                    resultadoAvaliacaoNome.setNomeFornecido(isNomeFornecido);
                    resultadoAvaliacaoNome.setFonteAvaliadora(DataQualityUtils.FONTE_VALIDACAO_GBIF);
                    resultadoAvaliacaoNome.setNomeEncontrado(true);
                    nome.setClassificacaoSuperior(gbifCacheNames.getClassificacaoSuperior());
                    resultadoAvaliacaoNome.setSinonimo(gbifCacheNames.isSinonimo());
                } else {
                    // nome não fornecido
                    resultadoAvaliacaoNome = new ResultadoAvaliacaoNome();
                    resultadoAvaliacaoNome.setNomeAvaliado(nome);
                    nome.setNome(null);
                }

                resultadosDaAvaliacao.add(resultadoAvaliacaoNome);
                //nomesAvaliados.add(nome);
            }

            logger.info("====================================================================");

            // encontrado no GBIF: grava no cache
            if (!nomesParaCache.isEmpty()) {
                bd.gravarResultadosNoCache(nomesParaCache);
                nomesParaCache.clear();
            }

            // grava uma quantidade de resultados da avaliação
            if (!resultadosDaAvaliacao.isEmpty()) {
                bd.gravarResultadosAvaliacao(Collections.unmodifiableList(resultadosDaAvaliacao));
                //logger.info("---> gravou 1 {}", resultadosDaAvaliacao.size());
                resultadosDaAvaliacao.clear();

                // todo: marca o registro como avaliado na occurrence_raw
                //Grava no BD dataportal o flag informando que o registro foi validado
                // melhor criar um procedimento ao final do processo de validação, lendo todos os registros na
                // validação_ortografica e atualizando na occurrence_raw
                //bd.marcarRegistroValidado(nomesAvaliados, DataQualityUtils.FONTE_VALIDACAO_GBIF);
            }

        } while (nomesParaAvaliacao != null && !nomesParaAvaliacao.isEmpty());

        logger.info("===> Quantas vezes fui ao bd: {}", conta);

        // gravar no banco de dados o resultado da avaliação
        if (!resultadosDaAvaliacao.isEmpty()) {
            bd.gravarResultadosAvaliacao(resultadosDaAvaliacao);
            //logger.info("---> gravou 2: {}", resultadosDaAvaliacao.size());
            resultadosDaAvaliacao.clear();

            // todo: marca o registro como avaliado na occurrence_raw
            //Grava no BD dataportal o flag informando que o registro foi validado
            //bd.marcarRegistroValidado(nomesAvaliados, DataQualityUtils.FONTE_VALIDACAO_GBIF);
        }

        bd.fechaConexao();

    }

    public static void avaliarNomesNaListaDaFloraDoBrasil() throws DataQualityExcpetion {

        // bd devolve nomes para avaliação
        BD bd = new BD();
        List<NomeCientifico> nomesParaAvaliacao = null;
        //Set<NomeCientifico> nomesAvaliados = new HashSet<NomeCientifico>();
        List<ResultadoAvaliacaoNome> resultadosDaAvaliacao = new ArrayList<ResultadoAvaliacaoNome>();
        //List<ResultadoAvaliacaoNome> nomesParaCache = new ArrayList<ResultadoAvaliacaoNome>();
        int conta = 0;


        // avaliar nomes pelo catalogo da flora do brasil
        logger.info("====================================================================");

        do {
            conta++;

            logger.info("===> Vai no bd: {}", conta);

            if (nomesParaAvaliacao != null && !nomesParaAvaliacao.isEmpty()) {
                nomesParaAvaliacao.clear();
            }

            nomesParaAvaliacao = bd.getNomesParaAvaliacaoListaFlora(DataQualityUtils.FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL);

            // avaliar cada nome
            for (NomeCientifico nome : nomesParaAvaliacao) {

                ResultadoAvaliacaoNome resultadoAvaliacaoNome = null;

                //logger.info("DWCA_ID: {} - Rank: {} - Nome: {} ", nome.getDwcaId(), nome.getTaxonRank(), nome.getNome());

                    // avaliar contra a Lista da Flora do Brasil

                    ResultadoAvaliacaoNome resultado = ConsultaListaFloraBrasil.avaliarListaFlora(nome, bd);
                    //logger.info("DWCA_ID: {} - Rank: {} - Nome: {} ", nome.getDwcaId(), nome.getTaxonRank(), nome.getNome());
                    resultadosDaAvaliacao.add(resultado);

                //nomesAvaliados.add(nome);
            }
            logger.info("====================================================================");

            // grava uma quantidade de resultados da avaliação
            if (!resultadosDaAvaliacao.isEmpty()) {
                bd.gravarResultadosAvaliacao(Collections.unmodifiableList(resultadosDaAvaliacao));
                //logger.info("---> gravou 1 {}", resultadosDaAvaliacao.size());
                resultadosDaAvaliacao.clear();

                //Grava no BD dataportal o flag informando que o registro foi validado
                //bd.marcarRegistroValidado(nomesAvaliados, DataQualityUtils.FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL);
            }


        } while(bd.existeMaisIteracao());//while (nomesParaAvaliacao != null && !nomesParaAvaliacao.isEmpty());

        logger.info("===> Quantas vezes fui ao bd: {}", conta);

        // gravar no banco de dados o resultado da avaliação
        if (!resultadosDaAvaliacao.isEmpty()) {
            bd.gravarResultadosAvaliacao(resultadosDaAvaliacao);
            //logger.info("---> gravou 2 {}", resultadosDaAvaliacao.size());
            resultadosDaAvaliacao.clear();

            //Grava no BD dataportal o flag informando que o registro foi validado
            //bd.marcarRegistroValidado(nomesAvaliados, DataQualityUtils.FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL);
        }

        bd.fechaConexao();

    }

}
