package com.robert.jersey.rest.comm;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class FallbackThrowableMapper implements ExceptionMapper<Throwable> {
	private static final Logger log = LoggerFactory
			.getLogger(FallbackThrowableMapper.class);

	public Response toResponse(Throwable t) {
		log.error("Fallback to handle exception:", t);

		if (t instanceof WebApplicationException)
			return Response
					.status(((WebApplicationException) t).getResponse()
							.getStatus()).entity(t.getMessage())
					.type("text/plain").build();

		return Response.status(500).entity(t.getMessage()).type("text/plain")
				.build();
	}
}