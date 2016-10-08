package com.yyo.config;

import java.time.Duration;

import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.resource.ResourceManager;

public class AngularJsHttpHandler {

	public static HttpHandler create (@SuppressWarnings("rawtypes") Class clazz) {
	    final ResourceManager staticResources =
	            new ClassPathResourceManager(clazz.getClassLoader(), "dist");
	    // Cache tuning is copied from Undertow unit tests.
	    final ResourceManager cachedResources =
	            new CachingResourceManager(100, 65536,
	                                       new DirectBufferCache(1024, 10, 10480),
	                                       staticResources,
	                                       (int)Duration.ofDays(1).getSeconds());
	    final ResourceHandler resourceHandler = new ResourceHandler(cachedResources);
	    resourceHandler.setWelcomeFiles("index.html");
	    resourceHandler.setDirectoryListingEnabled(true);
	    return resourceHandler;
	}
}
