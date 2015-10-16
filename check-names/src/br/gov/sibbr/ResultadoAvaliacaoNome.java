package br.gov.sibbr;

/**
 * Created by francisco on 10/11/14.
 */
public class ResultadoAvaliacaoNome {

    // nome foi fornecido pelo publicador
    private boolean isNomeFornecido;
    // nome foi encontrado na fonte avaliadora
    private boolean isNomeEncontrado;
    // nome encontrado é sinônimo
    private boolean isSinonimo;
    // nome da fonte avaliadora
    private String fonteAvaliadora;
    // sinônimo de outro nome
    private String sinonimoDe;
    // informações sobre o taxon avaliado
    private NomeCientifico nomeAvaliado;

    public ResultadoAvaliacaoNome() {
    }

    public boolean isNomeFornecido() {
        return isNomeFornecido;
    }

    public void setNomeFornecido(boolean isNomeFornecido) {
        this.isNomeFornecido = isNomeFornecido;
    }

    public boolean isNomeEncontrado() {
        return isNomeEncontrado;
    }

    public void setNomeEncontrado(boolean isNomeEncontrado) {
        this.isNomeEncontrado = isNomeEncontrado;
    }

    public boolean isSinonimo() {
        return isSinonimo;
    }

    public void setSinonimo(boolean isSinonimo) {
        this.isSinonimo = isSinonimo;
    }

    public String getFonteAvaliadora() {
        return fonteAvaliadora;
    }

    public void setFonteAvaliadora(String fonteAvaliadora) {
        this.fonteAvaliadora = fonteAvaliadora;
    }

    public String getSinonimoDe() {
        return sinonimoDe;
    }

    public void setSinonimoDe(String sinonimoDe) {
        this.sinonimoDe = sinonimoDe;
    }

    public NomeCientifico getNomeAvaliado() {
        return nomeAvaliado;
    }

    public void setNomeAvaliado(NomeCientifico nomeAvaliado) {
        this.nomeAvaliado = nomeAvaliado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultadoAvaliacaoNome that = (ResultadoAvaliacaoNome) o;

        if (isNomeFornecido != that.isNomeFornecido) return false;
        if (!nomeAvaliado.equals(that.nomeAvaliado)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (isNomeFornecido ? 1 : 0);
        result = 31 * result + nomeAvaliado.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ResultadoAvaliacaoNome{" +
                "isNomeFornecido=" + isNomeFornecido +
                ", isNomeEncontrado=" + isNomeEncontrado +
                ", isSinonimo=" + isSinonimo +
                ", fonteAvaliadora='" + fonteAvaliadora + '\'' +
                ", sinonimoDe='" + sinonimoDe + '\'' +
                ", nomeAvaliado=" + nomeAvaliado.toString() +
                '}';
    }
}
