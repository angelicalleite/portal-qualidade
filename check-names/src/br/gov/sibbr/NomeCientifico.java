package br.gov.sibbr;

import br.gov.sibbr.utils.DataQualityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NomeCientifico {

    private static Logger logger = LoggerFactory.getLogger(NomeCientifico.class);

    private String dwcaId;
    private String nome;
    private String taxonRank;
    private String canonicalName;
    // classificação superior
    private String classificacaoSuperior;

    public NomeCientifico() {
    }

    public boolean isReino() {
        return nome.toUpperCase().matches(DataQualityUtils.TODOS_OS_REINOS.toString());
    }

    public boolean isReinoPlanta() {
        return nome.toUpperCase().matches(DataQualityUtils.REINO_PLANTA);
    }

    public String getDwcaId() {
        return dwcaId;
    }

    public void setDwcaId(String dwcaId) {
        this.dwcaId = dwcaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTaxonRank() {
        return taxonRank;
    }

    public void setTaxonRank(String taxonRank) {
        this.taxonRank = taxonRank;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public String getClassificacaoSuperior() {
        return classificacaoSuperior;
    }

    public void setClassificacaoSuperior(String classificacaoSuperior) {
        this.classificacaoSuperior = classificacaoSuperior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NomeCientifico that = (NomeCientifico) o;

        if (!dwcaId.equals(that.dwcaId)) return false;
        if (!nome.equals(that.nome)) return false;
        if (!taxonRank.equals(that.taxonRank)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dwcaId.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + taxonRank.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "NomeCientifico{" +
                "dwcaId='" + dwcaId + '\'' +
                ", nome='" + nome + '\'' +
                ", taxonRank='" + taxonRank + '\'' +
                ", canonicalName='" + canonicalName + '\'' +
                ", classificacaoSuperior='" + classificacaoSuperior + '\'' +
                '}';
    }

    public GBIFCacheNames buildGBIFCacheName() {
        // String nomeCientifico, String canonicalName, String taxonRank, String classificacaoSuperior, boolean isSinonimo
        GBIFCacheNames n = new GBIFCacheNames();
        n.setNomeCientifico(nome);
        n.setCanonicalName(canonicalName);
        n.setTaxonRank(taxonRank);
        n.setClassificacaoSuperior(classificacaoSuperior);

        return  n;
    }

    public static void main(String[] args) {
        NomeCientifico nome = new NomeCientifico();
        nome.setNome("ARCHAEA");
        Boolean aval = new Boolean(nome.isReino());
        logger.info("{} is reino: {}", nome.getNome(), aval.toString());
    }
}
