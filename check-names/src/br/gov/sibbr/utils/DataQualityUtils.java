package br.gov.sibbr.utils;

public final class DataQualityUtils {

    /*
     * para excluir
     */
    public static final String NOME_NAO_CONFIRMADO = "";
    public static final String NOME_CONFIRMADO = "";

    /*
     * Fontes de validacoes
     */
    public static final String FONTE_VALIDACAO_GBIF = "BACKBONE TAXONÔMICO DO GBIF";
    public static final String FONTE_VALIDACAO_CATALOGO_FLORA_BRASIL = "CATÁLOGO DA FLORA DO BRASIL";
    public static final String FONTE_VALIDACAO_EOL = "ENCICLOPEDIA OF LIFE (EOL)";

    /*
     * URLs de fontes externas
     */
    // GBIF URL - parametros: primeiro RANK, segundo NOME
    public static final String GBIF_BASE_URL = "http://api.gbif.org/v1/species/match?verbose=false&%s=%s";

    /*
     * Taxon dos reinos
     * TODO validar esses nomes com o Danny
     */
    public static final String REINO_ANIMAL = "ANIMALIA|ANIMAL";
    public static final String REINO_PLANTA = "PLANTAE|PLANTA";
    public static final String REINO_FUNGO = "FUNGI|FUNGO";
    public static final String REINO_PROTOZOA = "PROTOZOA";
    public static final String REINO_CROMISTA = "CHROMISTA";
    public static final String REINO_BACTERIA = "BACTERIA";
    public static final String REINO_ARCHAEA = "ARCHAEA";
    public static final StringBuilder TODOS_OS_REINOS;

    static {
        TODOS_OS_REINOS = new StringBuilder();
        TODOS_OS_REINOS.append(REINO_ANIMAL);
        TODOS_OS_REINOS.append("|");
        TODOS_OS_REINOS.append(REINO_PLANTA);
        TODOS_OS_REINOS.append("|");
        TODOS_OS_REINOS.append(REINO_FUNGO);
        TODOS_OS_REINOS.append("|");
        TODOS_OS_REINOS.append(REINO_PROTOZOA);
        TODOS_OS_REINOS.append("|");
        TODOS_OS_REINOS.append(REINO_CROMISTA);
        TODOS_OS_REINOS.append("|");
        TODOS_OS_REINOS.append(REINO_BACTERIA);
        TODOS_OS_REINOS.append("|");
        TODOS_OS_REINOS.append(REINO_ARCHAEA);
    }
}
