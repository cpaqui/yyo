package com.yyo.main;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.yyo.config.RootRoute;
import com.yyo.config.YoRoute;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;

public final class Main {

	private static final String SERVER_LOG_LEVEL_PROPERTY = "server.log.level";
	private static final String UNDERTOW_SERVER_PORT = "undertow.server.port";
	private static final String UNDERTOW_SERVER_HOST = "undertow.server.host";
	private static final String SERVER_LOG_LEVEL = Level.INFO.getName();
	private static final String SERVE_PORT = "9090";
	private static final String SERVER_HOST = "0.0.0.0";
	private static final Logger LOGGER = Logger.getLogger(Main.class.getCanonicalName());

	private Main() {
	}

	public static void main(final String[] args) throws PropertyVetoException {

		try {
			LOGGER.setLevel(Level.parse(System.getProperty(SERVER_LOG_LEVEL_PROPERTY, SERVER_LOG_LEVEL)));
		} catch (Exception e) {
			LOGGER.setLevel(Level.parse(SERVER_LOG_LEVEL));
			LOGGER.log(Level.WARNING, String.format("Invalid value to Property %s, set value to %s", SERVER_LOG_LEVEL_PROPERTY, SERVER_LOG_LEVEL));
		}

		RootRoute routes = new RootRoute();
		routes.add(new YoRoute());

		String host = System.getProperty(UNDERTOW_SERVER_HOST, SERVER_HOST);
		int port;
		try {
			port = Integer.parseInt(System.getProperty(UNDERTOW_SERVER_PORT, SERVE_PORT));
		} catch (NumberFormatException e) {
			String msg = String.format("Property %s must be a positive integer, start server at %s", UNDERTOW_SERVER_PORT, SERVE_PORT);
			LOGGER.log(Level.WARNING, msg);
			port = Integer.parseInt(SERVE_PORT);
		}

		LOGGER.info(String.format("Start server at %s:%s", host, port));

		Undertow server = Undertow.builder()
				.addHttpListener(port, host)
				.setServerOption(UndertowOptions.IDLE_TIMEOUT, 3000)
				.setHandler(routes.routes())
				.build();
		server.start();
    }
}
