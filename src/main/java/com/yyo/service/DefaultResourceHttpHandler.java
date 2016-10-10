package com.yyo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.undertow.io.IoCallback;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.util.Headers;

public class DefaultResourceHttpHandler implements HttpHandler {

	private final Resource indexHtml;

	public DefaultResourceHttpHandler() throws IOException {
		try (ResourceManager staticResources =
	            new ClassPathResourceManager(this.getClass().getClassLoader(), "dist");) {

			indexHtml = staticResources.getResource("index.html");
		}
	}

	private static String getFile(File file) throws FileNotFoundException {
		StringBuilder result = new StringBuilder("");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				result.append(scanner.nextLine());
			}
			scanner.close();
		}

		return result.toString();
	}

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html; charset=UTF-8");
		indexHtml.serve(exchange.getResponseSender(), exchange, IoCallback.END_EXCHANGE);
	}
}