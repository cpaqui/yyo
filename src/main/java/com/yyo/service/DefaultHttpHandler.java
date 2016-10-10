package com.yyo.service;

import com.yyo.config.AngularJsHttpHandler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class DefaultHttpHandler implements HttpHandler {

	private static final HttpHandler defaultHandler = AngularJsHttpHandler.create(DefaultHttpHandler.class);

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.setRelativePath("/");
		exchange.dispatch(defaultHandler);
	}
}