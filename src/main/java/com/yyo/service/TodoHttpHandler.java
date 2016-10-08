package com.yyo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;

public final class TodoHttpHandler {

	private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
	private static final Logger LOGGER = Logger.getLogger(TodoHttpHandler.class.getCanonicalName());


	public HttpHandler getAll = new HttpHandler() {
		@Override
		public void handleRequest(HttpServerExchange exchange) throws Exception {
			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
			exchange.getResponseSender().send("[{\"todoMessage\":\"test\"}]");
		}
	};


	public HttpHandler getById = new HttpHandler() {
		@Override
		public void handleRequest(HttpServerExchange exchange) throws Exception {
			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
			try {
				String id = getId(exchange.getPathParameters());
				exchange.getResponseSender().send(String.format("[{\"id\": %s \"todoMessage\":\"test lalal lalla\"}]", id));
			} catch (Exception e) {
				exchange.setStatusCode(StatusCodes.NOT_FOUND);
				LOGGER.log(Level.INFO, e.getMessage());
			}
		}
	};

	public HttpHandler post = new BlockingHandler(new HttpHandler() {
		@Override
		public void handleRequest(HttpServerExchange exchange) throws Exception {
			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
			try {
				String payload = readBody(exchange);
				exchange.getResponseSender().send(payload);
			} catch (Exception e) {
				exchange.setStatusCode(StatusCodes.NOT_FOUND);
				LOGGER.log(Level.INFO, e.getMessage());
			}
		}
	});

	private String getId (Map<String, Deque<String>> params) {
		String id = params.get("id").getFirst();

		if (id.trim().isEmpty()) {
			throw new RuntimeException("Parameter id invalid.");
		}

		return id;
	}

	private String readBody (HttpServerExchange exchange) {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder( );

		try {
		    exchange.startBlocking( );
		    reader = new BufferedReader( new InputStreamReader( exchange.getInputStream( ) ) );

		    String line;
		    while( ( line = reader.readLine( ) ) != null ) {
		        builder.append( line );
		    }
		} catch( IOException e ) {
			throw new RuntimeException("Can't load request payload .");
		} finally {
		    if( reader != null ) {
		        try {
		            reader.close( );
		        } catch( IOException e ) {
		            e.printStackTrace( );
		        }
		    }
		}

		return builder.toString( );
	}

}