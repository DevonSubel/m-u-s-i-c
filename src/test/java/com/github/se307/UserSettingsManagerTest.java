package com.github.se307;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.se307.UserSettingsManager.UserSettings;

import javafx.scene.paint.Color;

/**
 * Unit test for simple App.
 */
public class UserSettingsManagerTest {

	private void cleanSettings() {
		File settingsFile = LocalResources.getResources().getUserResourcesDir().resolve("settings.xml").toFile();

		if (settingsFile.exists()) {
			assertTrue(settingsFile.delete());
		}
	}

	@Before
	public void beforeTest() {
		cleanSettings();
	}

	@After
	public void afterTest() {
		cleanSettings();

		UserSettingsManager.resetUserSettings();
	}

	@Test
	public void getDefaultSettings() {
		UserSettings settings = UserSettingsManager.getUserSettings();

		assertEquals("black", settings.getGuiColor());
	}

	@Test
	public void modifySettings() {
		UserSettings settings = UserSettingsManager.getUserSettings();

		assertEquals("black", settings.getGuiColor());

		settings.setGuiColor("red");

		UserSettingsManager.saveUserSettings();

		settings = UserSettingsManager.getUserSettings();

		assertEquals("red", settings.getGuiColor());
	}

	@Test
	public void modifyGuiColor() {
		UserSettings settings = UserSettingsManager.getUserSettings();

		assertEquals(Color.BLACK, settings.getFXColor());

		settings.setGuiColor(Color.RED);

		UserSettingsManager.saveUserSettings();

		settings = UserSettingsManager.getUserSettings();

		assertEquals(Color.RED, settings.getFXColor());
	}
}
