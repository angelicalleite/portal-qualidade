package br.gov.sibbr;

import br.gov.sibbr.utils.DataQualityExcpetion;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws DataQualityExcpetion {
        /*
		 * Logar a inicialização da configuração do log
		 */
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);


        AvaliacaoNomesCientitificos vl = new AvaliacaoNomesCientitificos();

        //logger.info("Iniciando validação de nomes NA API DO GBIF...");

        //vl.avaliarNomesNaAPIdoGBIF();

        //logger.info("Finalizada validação de nomes NA API DO GBIF");



        logger.info("Iniciando validação de nomes NA LISTA DA FLORA...");

        vl.avaliarNomesNaListaDaFloraDoBrasil();

        logger.info("Finalizada validação de nomes NA LISTA DA FLORA");

    }

}