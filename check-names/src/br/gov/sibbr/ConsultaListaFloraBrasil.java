package br.gov.sibbr;

import br.gov.sibbr.utils.DataQualityUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

public class ConsultaListaFloraBrasil {

    private static Logger logger = LoggerFactory.getLogger(ConsultaListaFloraBrasil.class);

    public static ResultadoAvaliacaoNome avaliarListaFlora(NomeCientifico nomeCientifico, BD bd) {

        ResultadoAvaliacaoNome resultado = new ResultadoAvaliacaoNome();
        resultado.setFonteAvaliadora(DataQualityUtils.FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL);
        resultado.setNomeAvaliado(nomeCientifico);
        resultado.setNomeFornecido(true);

        //BD bd = new BD();
        ResultSet rs;
        try {
            rs = bd.consultaListaFlora(nomeCientifico.getNome(), nomeCientifico.getTaxonRank());

            //Nome confirmado
            if (rs.next()) {
                resultado.setNomeEncontrado(true);

                //verifica se o nome é um sinonimo ou não
                //NOME_ACEITO: não é sinônimo
                // SINONIMO: o nome é um sinônimo
                if (!Strings.isNullOrEmpty(rs.getString(8)) && rs.getString(8).equals("NOME_ACEITO")) {
                    resultado.setSinonimo(false);
                } else if (!Strings.isNullOrEmpty(rs.getString(8)) && rs.getString(8).equals("SINONIMO")) {
                    resultado.setSinonimo(true);
                    //Pega o sinonimo do nome que está sendo consultado
                    resultado.setSinonimoDe(rs.getString(11));
                }
                //Pega a classificação superior do nome consultado
               // resultado.setClassificacaoSuperior(rs.getString(10));
                nomeCientifico.setClassificacaoSuperior(rs.getString(10));
            }
        } catch (Exception e) {
           // e.printStackTrace();
            logger.debug("Exception: {}", e);
        }

        //bd.fechaConexao();
        return resultado;

    }

}
