package com.yyo.config;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

public class StaticRoute implements Route {

	private static RoutingHandler routes = new RoutingHandler();
	private static HttpHandler staticHandler = AngularJsHttpHandler.create(Route.class);

	public StaticRoute() {
		routes
        .get("/", staticHandler)
        .get("/bower_components/*", staticHandler);
	}

	@Override
	public RoutingHandler routes () {
		return routes;
	}
}