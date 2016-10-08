package com.yyo.config;

import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

public final class RootRoute {

	private final RoutingHandler routes = new RoutingHandler();

	public HttpHandler routes () {
		return routes;
	}

	public RootRoute add (Route route) {
		routes.addAll(route.routes());
		return this;
	}
}
