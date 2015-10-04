package com.robert.jersey.rest;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.Path;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class RestServer implements ApplicationContextAware {
	private static final Logger log = LoggerFactory.getLogger(RestServer.class);

	// 0.0.0.0 means that it listens at any local network interfaces
	private static final String BASE_URI_TEMP = "http://0.0.0.0:%d/%s/";

	private ApplicationContext applicationContext;

	private HttpServer httpServer;

	private int port = 8080;

	private String context = "rest";

	private String baseUri = String.format(BASE_URI_TEMP, port, context);

	public RestServer() {

	}

	public RestServer(int port, String context) {
		init(port, context);
	}

	protected void init(int port, String context) {
		this.port = port;
		this.context = context;
		this.baseUri = String.format(BASE_URI_TEMP, port, context);
	}

	public void startup(int port, String context) {
		init(port, context);

		startup();
	}

	public void startup() {
		log.info("Startup the server.");
		final ResourceConfig rc = new ResourceConfig();

		rc.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

		Map<String, Object> beans = applicationContext
				.getBeansWithAnnotation(Path.class);
		for (Map.Entry<String, Object> entry : beans.entrySet()) {
			log.info("Register the resouce: {}.", entry.getKey());
			rc.register(entry.getValue());
		}

		rc.register(applicationContext.getBean("defaultAuthorizationRequestFilter"));

		// Register the response converter
		rc.register(com.robert.jersey.rest.comm.StatusMessageResponseFilter.class);

		rc.register(com.robert.jersey.rest.comm.FallbackThrowableMapper.class);

		rc.register(org.glassfish.jersey.server.validation.ValidationFeature.class);

		log.info("Create Http Server: {}.", baseUri);
		httpServer = GrizzlyHttpServerFactory.createHttpServer(
				URI.create(baseUri), rc);

		// Use hook to shutdown the server
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				log.info("Shutdown hook starts to run.");
				RestServer.this.shutdown();
			}
		});
	}

	public void shutdown() {
		log.info("Shutdown the server.");
		httpServer.shutdownNow();
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
