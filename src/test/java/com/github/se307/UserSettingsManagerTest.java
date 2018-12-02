package com.github.se307;

import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.se307.UserSettingsManager.UserSettings;

import javafx.scene.paint.Color;

/**
 * Unit test for simple App.
 */
public class UserSettingsManagerTest 
{
	
	private void cleanSettings() {
		File settingsFile = Paths.get(System.getProperty("user.home"), ".m-u-s-i-c", "settings.xml").toFile();
		
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
		
		assertEquals(settings.getGuiColor(), "red");
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
