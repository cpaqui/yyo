package com.yyo.config;

import io.undertow.server.RoutingHandler;

public interface Route {

	RoutingHandler routes ();
}
