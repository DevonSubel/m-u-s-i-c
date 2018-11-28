package com.github.se307;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocalResources {
	private static Logger logger = LogManager.getLogger();
	private static LocalResources instance = null;

	private static final String RESOURCES_DIR_NAME = ".m-u-s-i-c/";

	private final Path userHome;
	private final Path userResourcesDir;

	private LocalResources() {
		this.userHome = Paths.get(System.getProperty("user.home"));
		this.userResourcesDir = userHome.resolve(RESOURCES_DIR_NAME);
		logger.info("Loading local resources path at %s", this.userResourcesDir);

		if (!userResourcesDir.toFile().exists()) {
			userResourcesDir.toFile().mkdirs();
		}
	}

	public Path getUserHome() {
		return this.userHome;
	}

	public Path getUserResourcesDir() {
		return this.userResourcesDir;
	}

	public static LocalResources getResources() {
		if (instance == null) {
			instance = new LocalResources();
		}

		return instance;
	}
}
