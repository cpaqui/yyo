package com.yyo.service;

import java.io.IOException;

import com.yyo.config.AngularJsHttpHandler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class DefaultHttpHandler implements HttpHandler {

	private static HttpHandler defaultHandler;

	static {
		try {
			defaultHandler = AngularJsHttpHandler.create(DefaultHttpHandler.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
//		exchange.setRelativePath("/");
		exchange.dispatch(defaultHandler);
	}
}