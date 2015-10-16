package br.gov.sibbr;

import br.gov.sibbr.utils.DataQualityExcpetion;
import br.gov.sibbr.utils.DataQualityUtils;
import br.gov.sibbr.utils.Rank;
import com.google.common.base.Strings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class ConsultaGBIF {

    private static Logger logger = LoggerFactory.getLogger(ConsultaGBIF.class);

    private static String convertStreamToString(InputStream is) {
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    //public static ResultadoAvaliacaoNome avaliar(Rank rank, String nome) {
    public static ResultadoAvaliacaoNome avaliar(NomeCientifico nome) throws DataQualityExcpetion {

        if (nome == null) {
            throw new DataQualityExcpetion("O taxon informado para avaliação é nulo.");
        }

        ResultadoAvaliacaoNome resultado = new ResultadoAvaliacaoNome();
        resultado.setNomeAvaliado(nome);
        boolean isNomeFornecido = !Strings.isNullOrEmpty(nome.getNome());

        // valida se o nome foi fornecido
        if (!isNomeFornecido) {
            // nome não fornecido: gravar null no banco
            nome.setNome(null);
        } else {
            resultado.setNomeFornecido(isNomeFornecido);
            resultado.setFonteAvaliadora(DataQualityUtils.FONTE_VALIDACAO_GBIF);

            // Cria o parse de tratamento
            JSONParser parser = new JSONParser();

            StringBuilder sbUrl = new StringBuilder();

            try {
                //String s = String.format(DataQualityUtils.GBIF_BASE_URL, rank, URLEncoder.encode(nome, "UTF-8"));
                if (nome.getTaxonRank().equals(Rank.ESPECIE.getNivelTaxonomicoGBIF())) {
                    sbUrl.append(String.format(DataQualityUtils.GBIF_BASE_URL, "name", URLEncoder.encode(nome.getNome(), "UTF-8")));
                } else {
                    sbUrl.append(String.format(DataQualityUtils.GBIF_BASE_URL, nome.getTaxonRank(), URLEncoder.encode(nome.getNome(), "UTF-8")));
                }

                logger.info("URL: {}", sbUrl);

                URL url = new URL(sbUrl.toString());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(1000); // 1 segundo
                connection.setReadTimeout(1000 * 10); // 10 segundos

                String content = null;

                int tentativas = 0;
                timeout:
                do {
                    // tentar lê 3 vezes o canal
                    tentativas++;
                    if (tentativas > 2) {
                        break;
                    }
                    try {
                        content = convertStreamToString(connection.getInputStream());
                    } catch (IOException e) {
                        continue timeout;
                    }
                    break;
                } while (true);

                // todo: tratar o content == null apropriadamente. Foi tratado no catch
                JSONObject jsonObject = (JSONObject) parser.parse(content);

                if (jsonObject != null) {

                    String rank = (String) jsonObject.get("rank");
                    String scientificName = (String) jsonObject.get("scientificName");
                    String canonicalName = (String) jsonObject.get("canonicalName");
                    boolean synonym = (boolean) jsonObject.get("synonym");
                    String kingdom = (String) jsonObject.get("kingdom");
                    String phylum = (String) jsonObject.get("phylum");
                    String classe = (String) jsonObject.get("class");
                    String order = (String) jsonObject.get("order");
                    String family = (String) jsonObject.get("family");
                    String genus = (String) jsonObject.get("genus");


                    //boolean isNomeEncontrado = false;

                    if (rank != null && scientificName != null && canonicalName != null) {
                        boolean isRankMatch = rank.equalsIgnoreCase(nome.getTaxonRank());
                        // existem nomes que possuem o autor no final
                        //boolean isNomeMatch = nomeRetornado.toUpperCase().contains(nome.getNome().toUpperCase());
                        boolean isNomeMatch = scientificName.equalsIgnoreCase(nome.getNome());
                        boolean isCanonicalNameMatch = canonicalName.equalsIgnoreCase(nome.getNome());
                        boolean isNomeEncontrado = ((isNomeMatch && isRankMatch) || (isCanonicalNameMatch && isRankMatch));
                        resultado.setNomeEncontrado(isNomeEncontrado);

                        nome.setCanonicalName(canonicalName);

                        resultado.setSinonimo(synonym);

                        // tenta construir a classificação superior
                        StringBuilder classificacaoSuperior = new StringBuilder();
                        if (kingdom != null) {
                            classificacaoSuperior.append(kingdom);
                        }

                        if (phylum != null) {
                            classificacaoSuperior.append("; ").append(phylum);
                        }

                        if (classe != null) {
                            classificacaoSuperior.append("; ").append(classe);
                        }

                        if (order != null) {
                            classificacaoSuperior.append("; ").append(order);
                        }

                        if (family != null) {
                            classificacaoSuperior.append("; ").append(family);
                        }

                        if (genus != null) {
                            classificacaoSuperior.append("; ").append(genus);
                        }

                        if (classificacaoSuperior.length() > 0) {
                            nome.setClassificacaoSuperior(classificacaoSuperior.toString());
                        }
                    }

                    //logger.info("Nome retornado: {}", nomeRetornado);
                    //logger.info("Rank retornado: {}", rankRetornado);
                    //logger.info("Nome avaliado:  {}", nome.getNome());
                    //logger.info("Rank avaliado:  {}", nome.getTaxonRank());
                    //logger.info("Resultado avaliação:  {}", isNomeEncontrado);

				/*
                 * EXACT = correspondência exata do nome consultado
				 * HIGHERRANK = Correspondência em uma rank mais elevada que o menor nome dado.
				 */

                }
            } catch (NullPointerException | IOException | ParseException e) {
                logger.error("Exception: {}, {}", e.getCause(), e.getStackTrace());
                // retorna nada
                resultado = null;
            }
        }

        return resultado;
    }
}