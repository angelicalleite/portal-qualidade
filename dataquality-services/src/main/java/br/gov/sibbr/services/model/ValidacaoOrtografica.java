package br.gov.sibbr.services.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import flexjson.JSON;

@Alias("ValidacaoOrtografica")
public class ValidacaoOrtografica implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JSON(name = "id", include=false)
	private Integer id;	
	
	@JSON(name = "darwinCoreArchiveID", include=true)
	private String dwcaId;
	
	@JSON(name = "nomeCientifico", include=true)
	private String nomeCientifico;
	
	@JSON(name = "taxonRank", include=true)
	private String taxonRank;
	
	@JSON(name = "fonteAvaliadora", include=true)
	private String fonteAvaliadora;
	
	@JSON(name = "nomeFoiEncontrado", include=true)
	private Boolean isNomeEncontrado;
	
	@JSON(name = "nomeFoiFornecido", include=true)
	private Boolean isNomeFornecido;
	
	@JSON(name = "eSinonimo", include=true)
	private Boolean isSinonimo;
	
	@JSON(name = "eSinonimoDe", include=true)
	private String sinonimoDe;
	
	@JSON(name = "classificacaoSuperior", include=true)
	private String classificacaoSuperior;
	
	public ValidacaoOrtografica() {
	}

	public ValidacaoOrtografica(Integer id, String dwcaId,
			String nomeCientifico, String taxonRank, String fonteAvaliadora,
			Boolean isNomeEncontrado, Boolean isNomeFornecido,
			Boolean isSinonimo, String sinonimoDe, String classificacaoSuperior) {
		super();
		this.id = id;
		this.dwcaId = dwcaId;
		this.nomeCientifico = nomeCientifico;
		this.taxonRank = taxonRank;
		this.fonteAvaliadora = fonteAvaliadora;
		this.isNomeEncontrado = isNomeEncontrado;
		this.isNomeFornecido = isNomeFornecido;
		this.isSinonimo = isSinonimo;
		this.sinonimoDe = sinonimoDe;
		this.classificacaoSuperior = classificacaoSuperior;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDwcaId() {
		return dwcaId;
	}

	public void setDwcaId(String dwcaId) {
		this.dwcaId = dwcaId;
	}

	public String getNomeCientifico() {
		return nomeCientifico;
	}

	public void setNomeCientifico(String nomeCientifico) {
		this.nomeCientifico = nomeCientifico;
	}

	public String getTaxonRank() {
		return taxonRank;
	}

	public void setTaxonRank(String taxonRank) {
		this.taxonRank = taxonRank;
	}

	public String getFonteAvaliadora() {
		return fonteAvaliadora;
	}

	public void setFonteAvaliadora(String fonteAvaliadora) {
		this.fonteAvaliadora = fonteAvaliadora;
	}

	public Boolean getIsNomeEncontrado() {
		return isNomeEncontrado;
	}

	public void setIsNomeEncontrado(Boolean isNomeEncontrado) {
		this.isNomeEncontrado = isNomeEncontrado;
	}

	public Boolean getIsNomeFornecido() {
		return isNomeFornecido;
	}

	public void setIsNomeFornecido(Boolean isNomeFornecido) {
		this.isNomeFornecido = isNomeFornecido;
	}

	public Boolean getIsSinonimo() {
		return isSinonimo;
	}

	public void setIsSinonimo(Boolean isSinonimo) {
		this.isSinonimo = isSinonimo;
	}

	public String getSinonimoDe() {
		return sinonimoDe;
	}

	public void setSinonimoDe(String sinonimoDe) {
		this.sinonimoDe = sinonimoDe;
	}

	public String getClassificacaoSuperior() {
		return classificacaoSuperior;
	}

	public void setClassificacaoSuperior(String classificacaoSuperior) {
		this.classificacaoSuperior = classificacaoSuperior;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dwcaId == null) ? 0 : dwcaId.hashCode());
		result = prime * result
				+ ((fonteAvaliadora == null) ? 0 : fonteAvaliadora.hashCode());
		result = prime * result
				+ ((nomeCientifico == null) ? 0 : nomeCientifico.hashCode());
		result = prime * result
				+ ((taxonRank == null) ? 0 : taxonRank.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidacaoOrtografica other = (ValidacaoOrtografica) obj;
		if (dwcaId == null) {
			if (other.dwcaId != null)
				return false;
		} else if (!dwcaId.equals(other.dwcaId))
			return false;
		if (fonteAvaliadora == null) {
			if (other.fonteAvaliadora != null)
				return false;
		} else if (!fonteAvaliadora.equals(other.fonteAvaliadora))
			return false;
		if (nomeCientifico == null) {
			if (other.nomeCientifico != null)
				return false;
		} else if (!nomeCientifico.equals(other.nomeCientifico))
			return false;
		if (taxonRank == null) {
			if (other.taxonRank != null)
				return false;
		} else if (!taxonRank.equals(other.taxonRank))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ValidacaoOrtografica [dwcaId=" + dwcaId + ", nomeCientifico="
				+ nomeCientifico + ", taxonRank=" + taxonRank
				+ ", fonteAvaliadora=" + fonteAvaliadora
				+ ", isNomeEncontrado=" + isNomeEncontrado
				+ ", isNomeFornecido=" + isNomeFornecido + ", isSinonimo="
				+ isSinonimo + ", sinonimoDe=" + sinonimoDe
				+ ", classificacaoSuperior=" + classificacaoSuperior + "]";
	}
}
