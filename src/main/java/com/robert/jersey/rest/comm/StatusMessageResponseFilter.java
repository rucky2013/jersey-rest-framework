package com.robert.jersey.rest.comm;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.server.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class StatusMessageResponseFilter implements ContainerResponseFilter {
	private static final Logger log = LoggerFactory
			.getLogger(StatusMessageResponseFilter.class);

	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		BaseResult br = null;

		Object entity = responseContext.getEntity();
		if (responseContext.getStatus() < 200
				|| responseContext.getStatus() >= 300) {
			log.warn("Uri: {}, Error: {}, Http Status: {}",
					requestContext.getUriInfo(), responseContext.getEntity(),
					responseContext.getStatus());

			br = new BaseResult();
			br.setStatus(responseContext.getStatus());

			if (entity instanceof List
					&& ((List<? extends Object>) entity).size() > 0
					&& ((List<? extends Object>) entity).get(0) instanceof ValidationError) {
				// Validation error - 400+
				List<ValidationError> errors = (List<ValidationError>) entity;
				StringBuilder sb = new StringBuilder("Parameter Validation: ");
				for (ValidationError ve : errors)
					sb.append(ve.getPath()).append("=")
							.append(ve.getInvalidValue()).append("-->")
							.append(ve.getMessage()).append(";");
				br.setMessage(sb.toString());
			} else if (entity != null) {
				// Server error - 500+
				br.setMessage(entity.toString());
			} else {
				// Other error
				br.setMessage("Unknow Error.");
			}
		} else if (entity == null || entity.equals(Boolean.TRUE)) {
			// TODO It doesn't work when the handler for a path doens't have a
			// return value. Now return true to make it work
			log.warn("The request {} returns the empty value or true.",
					requestContext.getUriInfo());

			br = new BaseResult();
		} else
			br = new DataResult(entity);

		String result = JSON.toJSONString(br);
		responseContext.setEntity(result);
	}
}