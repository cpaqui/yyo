package com.yyo.service;

import javax.servlet.ServletContext;

import io.undertow.Handlers;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

public class NonBlockingHandlerExtension implements ServletExtension {

	public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext servletContext) {
        deploymentInfo.addInitialHandlerChainWrapper(new HandlerWrapper() {
            @Override
            public HttpHandler wrap(final HttpHandler handler) {
                return Handlers.path()
                        .addPrefixPath("/", handler);
            }
        });
    }
}