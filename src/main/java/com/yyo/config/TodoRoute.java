package com.yyo.config;

import com.yyo.service.TodoHttpHandler;

import io.undertow.server.RoutingHandler;

public class TodoRoute implements Route {

	private static RoutingHandler routes = new RoutingHandler();
	private static final TodoHttpHandler TODO_HANDLER = new TodoHttpHandler();

	public TodoRoute() {
		routes
        .get("/todos", TODO_HANDLER.getAll)
        .get("/todos/{id}", TODO_HANDLER.getById)
        .post("/todos", TODO_HANDLER.post)
        ;
	}

	@Override
	public RoutingHandler routes () {
		return routes;
	}
}