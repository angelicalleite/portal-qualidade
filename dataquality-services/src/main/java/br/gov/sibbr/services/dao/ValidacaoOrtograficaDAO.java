package br.gov.sibbr.services.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import br.gov.sibbr.services.model.ValidacaoOrtografica;
import br.gov.sibbr.services.utils.MyBatisUtils;

public class ValidacaoOrtograficaDAO {

	public List<ValidacaoOrtografica> getValidacaoesPor(String dwcaId) {
		SqlSession session = MyBatisUtils.getSQLSession();
		ValidacaoOrtograficaMapper validacao = session.getMapper(ValidacaoOrtograficaMapper.class);
		List<ValidacaoOrtografica> avaliacoes = validacao.findAllByDwcaId(dwcaId);
		session.close();
		return avaliacoes;
	}
}
