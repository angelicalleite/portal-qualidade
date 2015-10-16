package br.gov.sibbr.services.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import flexjson.JSONSerializer;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("test")
public class APITest {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "SiBBr dataquality services is running!";
    }
    
    @GET
	@Path("/json/{parameter}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@PathParam("parameter") String parameter) {
    	
    	SiBBrTest resposta = new SiBBrTest(parameter);
		    	
    	JSONSerializer serializer = new JSONSerializer();
    	String str = serializer.deepSerialize(resposta);
    	Response res;
		res = Response.status(200).entity(str).build();
		System.out.println("==>" + parameter);
		return res;
    }
}

class SiBBrTest {
	String parameter;

	public SiBBrTest(String parameter) {
		this.parameter = "Hello stranger " + parameter + "!";
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
