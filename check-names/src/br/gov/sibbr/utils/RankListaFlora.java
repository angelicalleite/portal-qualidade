package br.gov.sibbr.utils;

public enum RankListaFlora {
    //Não é possível validar REINO pela lista da flora
    //REINO("REINO"),
    FILO("FILO"),
    CLASSE("CLASSE"),
    ORDEM("ORDEM"),
    FAMILIA("FAMILIA"),
    GENERO("GENERO"),
    ESPECIE("ESPECIE");

    private String rank;

    RankListaFlora(String nome) {
        this.rank = nome;
    }

    public String getRank() {
        return this.rank;
    }
}

