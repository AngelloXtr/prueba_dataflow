package com.bbva.ws.impl;

import com.bbva.beans.ExampleBean;
import com.bbva.biz.impl.ExampleBiz;
import com.bbva.constants.ExampleConstants;
import com.bbva.constants.ExampleRestConstants;
import com.bbva.ws.GenericWs;
import com.google.api.client.http.HttpStatusCodes;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;

@Path(ExampleRestConstants.EXAMPLE_REST_URL)
public class ExampleWS extends GenericWs {

	@GET
	@Path(ExampleRestConstants.EXAMPLE_GET_URL)
	@Produces(MediaType.APPLICATION_JSON + GenericWs.CHARSET_UTF8)
	public Response getRequest(@Context HttpServletRequest request,
							 @PathParam(ExampleConstants.PARAM_NAME) String name,
							 @QueryParam(ExampleConstants.PARAM_SURNAME) String surname) {
		
		Response response;

        try {
            ExampleBiz exampleBiz = new ExampleBiz();
            ExampleBean exampleBean = exampleBiz.get(name, surname);

            response = generateResponse("200", "Service executed successfully", exampleBean,
                                        HttpStatusCodes.STATUS_CODE_OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            response = generateResponse("500", e.getMessage(), e, HttpStatusCodes.STATUS_CODE_SERVER_ERROR);
        }

		return response;
	}
}