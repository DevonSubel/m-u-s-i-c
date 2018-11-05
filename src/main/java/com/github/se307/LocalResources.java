package com.github.se307;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalResources {
	private static LocalResources instance = null;

	private static final String RESOURCES_DIR_NAME = ".m-u-s-i-c/";

	private final Path userHome;
	private final Path userResourcesDir;

	private LocalResources() {
		this.userHome = Paths.get(System.getProperty("user.home"));
		this.userResourcesDir = userHome.resolve(RESOURCES_DIR_NAME);

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
