package br.gov.sibbr.utils;

public enum Rank {
    REINO("kingdom-REINO"),
    FILO("phylum-FILO"),
    CLASSE("class-CLASSE"),
    ORDEM("order-ORDEM"),
    FAMILIA("family-FAMILIA"),
    GENERO("genus-GENERO"),
    ESPECIE("SPECIES-ESPECIE");

    private String nivelTaxonomico;

    Rank(String nivelTaxonomico) {
        this.nivelTaxonomico = nivelTaxonomico;
    }

    public String getNivelTaxonomicoGBIF() {
        return this.nivelTaxonomico.split("-")[0];
    }

    public String getNivelTaxonomicoFloraBR() {
        return this.nivelTaxonomico.split("-")[1];
    }
}

