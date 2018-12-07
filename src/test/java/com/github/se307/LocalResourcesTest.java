package com.github.se307;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author Declan Kelly
 *
 */
public class LocalResourcesTest {

	@Test
	public void checkUserHome() {
		LocalResources localResources = LocalResources.getResources();

		assertEquals(System.getProperty("user.home"), localResources.getUserHome().toString());
	}

	@Test
	public void checkLocalResourceFolder() {
		LocalResources localResources = LocalResources.getResources();

		String expectedPath = Paths.get(System.getProperty("user.home"), ".m-u-s-i-c").toString();
		assertEquals(expectedPath, localResources.getUserResourcesDir().toString());
	}

}
