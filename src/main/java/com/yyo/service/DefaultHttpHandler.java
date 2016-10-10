package com.yyo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.util.Headers;

public class DefaultHttpHandler implements HttpHandler {

	private final String indexHtml;

	public DefaultHttpHandler() throws IOException {
		try (ResourceManager staticResources =
	            new ClassPathResourceManager(this.getClass().getClassLoader(), "dist");) {

			indexHtml = getFile(staticResources.getResource("index.html").getFile());
		}
	}

	private static String getFile(File file) throws FileNotFoundException {
		StringBuilder result = new StringBuilder("");

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();
		}

		return result.toString();
	}

	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/html; charset=UTF-8");
		exchange.getResponseSender().send(indexHtml);
	}
}