package br.gov.sibbr.services.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.sibbr.services.dao.ValidacaoOrtograficaDAO;
import br.gov.sibbr.services.model.ValidacaoOrtografica;
import flexjson.JSONSerializer;

@Path("ocorrencia")
public class Names {
	private static Logger logger = LoggerFactory.getLogger(Names.class);

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "application/json" media type.
	 * 
	 * @param dwcaId
	 * @return Response that contain the results of the data quality check
	 *         process.
	 */
	@GET
	//@Path("/busca/{parameter}")
	// @Produces({"application/json; charset=UTF-8"})
	@Path("/busca")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAvaliacaoPara(@QueryParam("dwca_id") String dwcaId) {
		ValidacaoOrtograficaDAO dao = new ValidacaoOrtograficaDAO();
		List<ValidacaoOrtografica> validacoes = dao.getValidacaoesPor(dwcaId);

		//Gson gson = new Gson();
		//String str = gson.toJson(validacoes);
		
		JSONSerializer serializer = new JSONSerializer();
		//String str = serializer.deepSerialize(validacoes);
		String json = serializer.serialize(validacoes);
		
		logger.debug("Json: {}", json);
		
		
		Response res = Response.status(200).entity(json).encoding("UTF-8").build();

		return res;
	}

	// http://localhost:8080/dataquality-services/api/ocorrencia/busca/urn:catalog:JBRJ:RB:530105
	// http://localhost:8080/dataquality-services/api/ocorrencia/busca/686137540
	// http://localhost:8080/dataquality-services/api/ocorrencia/query?dwca_id=686137540

}
