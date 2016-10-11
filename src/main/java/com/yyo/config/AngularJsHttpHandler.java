package com.yyo.config;

import java.io.IOException;
import java.time.Duration;

import com.yyo.service.DefaultResourceHttpHandler;

import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.util.StatusCodes;

public class AngularJsHttpHandler {

	public static HttpHandler create (@SuppressWarnings("rawtypes") Class clazz) throws IOException {
	    final ResourceManager staticResources =
	            new ClassPathResourceManager(clazz.getClassLoader(), "dist");

	    // Cache tuning is copied from Undertow unit tests.
	    final ResourceManager cachedResources =
	            new CachingResourceManager(100, 65536,
	                                       new DirectBufferCache(1024, 10, 10480),
	                                       staticResources,
	                                       (int)Duration.ofDays(1).getSeconds());

	    DefaultResourceHttpHandler exceptionHandler = new DefaultResourceHttpHandler();

	    final ResourceHandler resourceHandler = new ResourceHandler(cachedResources);
	    resourceHandler.setWelcomeFiles("index.html");
	    resourceHandler.setDirectoryListingEnabled(false);
	    resourceHandler.addExceptionHandler(StatusCodes.NOT_FOUND, exceptionHandler);
	    resourceHandler.addExceptionHandler(StatusCodes.FORBIDDEN, exceptionHandler);

	    return resourceHandler;
	}
}
