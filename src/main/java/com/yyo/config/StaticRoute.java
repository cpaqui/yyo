package com.yyo.config;

import java.io.IOException;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

public class StaticRoute implements Route {

	private static RoutingHandler routes = new RoutingHandler();
	private final HttpHandler staticHandler;

	public StaticRoute() throws IOException {
		staticHandler = AngularJsHttpHandler.create(Route.class);
		routes
        .get("/", staticHandler)
        .get("/bower_components/*", staticHandler);
	}

	@Override
	public RoutingHandler routes () {
		return routes;
	}
}