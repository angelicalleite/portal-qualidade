package br.gov.sibbr;

import com.google.common.base.Strings;

/**
 * Created by francisco on 16/11/14.
 */
public class GBIFCacheNames {
    private String nomeCientifico;
    private String canonicalName;
    private String taxonRank;
    private String classificacaoSuperior;
    private boolean isSinonimo;

    public GBIFCacheNames() {
    }

    public boolean estaNoCache() {
        return !Strings.isNullOrEmpty(nomeCientifico) && !Strings.isNullOrEmpty(taxonRank);
    }

    public GBIFCacheNames(String nomeCientifico, String canonicalName, String taxonRank, String classificacaoSuperior, boolean isSinonimo) {
        this.nomeCientifico = nomeCientifico;
        this.canonicalName = canonicalName;
        this.taxonRank = taxonRank;
        this.classificacaoSuperior = classificacaoSuperior;
        this.isSinonimo = isSinonimo;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getTaxonRank() {
        return taxonRank;
    }

    public void setTaxonRank(String taxonRank) {
        this.taxonRank = taxonRank;
    }

    public String getClassificacaoSuperior() {
        return classificacaoSuperior;
    }

    public void setClassificacaoSuperior(String classificacaoSuperior) {
        this.classificacaoSuperior = classificacaoSuperior;
    }

    public boolean isSinonimo() {
        return isSinonimo;
    }

    public void setSinonimo(boolean isSinonimo) {
        this.isSinonimo = isSinonimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GBIFCacheNames that = (GBIFCacheNames) o;

        //if (!canonicalName.equals(that.canonicalName)) return false;
        if (!nomeCientifico.equals(that.nomeCientifico)) return false;
        if (!taxonRank.equals(that.taxonRank)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nomeCientifico.hashCode();
        result = 31 * result + taxonRank.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GBIFCacheNames{" +
                "nomeCientifico='" + nomeCientifico + '\'' +
                ", canonicalName='" + canonicalName + '\'' +
                ", taxonRank='" + taxonRank + '\'' +
                ", classificacaoSuperior='" + classificacaoSuperior + '\'' +
                ", isSinonimo=" + isSinonimo +
                '}';
    }

    public NomeCientifico buildNomeCientifico() {
        NomeCientifico nome = new NomeCientifico();
        nome.setCanonicalName(canonicalName);
        nome.setClassificacaoSuperior(classificacaoSuperior);
        nome.setTaxonRank(taxonRank);
        nome.setNome(nomeCientifico);

        return  nome;
    }
}
