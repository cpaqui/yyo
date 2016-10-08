package com.yyo.config;

import com.yyo.service.YoHttpHandler;

import io.undertow.server.RoutingHandler;

public class YoRoute implements Route {

	private static RoutingHandler routes = new RoutingHandler();

	public YoRoute() {
		routes
        .get("/", new YoHttpHandler());
	}

	@Override
	public RoutingHandler routes () {
		return routes;
	}
}