package br.gov.sibbr.services.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import br.gov.sibbr.services.model.ValidacaoOrtografica;

public interface ValidacaoOrtograficaMapper {
	final String GET_AVAL = "SELECT v.id id, v.dwca_id dwcaId, v.nome_cientifico nomeCientifico, v.taxon_rank taxonRank, v.fonte_avaliadora fonteAvaliadora, v.is_nome_encontrado isNomeEncontrado, v.is_nome_fornecido isNomeFornecido, v.is_sinonimo isSinonimo, v.sinonimo_de sinonimoDe, v.classificacao_superior classificacaoSuperior FROM quality.validacao_ortografica v WHERE v.dwca_id = #{dwcaidParameter}";

	@Select(GET_AVAL)
	public List<ValidacaoOrtografica> findAllByDwcaId(String dwcaId);
}
