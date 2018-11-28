package com.github.se307;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.paint.Color;

public class UserSettingsManager {

	private static final String USER_SETTINGS_FILENAME = "settings.xml";
	
	private static Logger logger = LogManager.getLogger();
	private static UserSettings instance;

	public static UserSettings getUserSettings() {
		if (instance == null) {
			loadSettings();
		}

		return instance;
	}

	public static void refreshUserSettings() {
		loadSettings();
	}

	public static void saveUserSettings() {
		Path settingsPath = getSettingsPath();

		try (OutputStream os = new BufferedOutputStream(new FileOutputStream(settingsPath.toString()));
				XMLEncoder e = new XMLEncoder(os)) {

			e.writeObject((Object) instance);
		} catch (FileNotFoundException e) {
			logger.error("Settings file not found at %s", settingsPath.toString());
		} catch (IOException e) {
			logger.error("Settings IO exception: %s", e.getMessage());
		}
	}

	private static Path getSettingsPath() {
		return LocalResources.getResources().getUserResourcesDir().resolve(USER_SETTINGS_FILENAME);
	}

	private static void loadSettings() {
		Path settingsPath = getSettingsPath();

		if (!settingsPath.toFile().exists()) {
			// Else write default settings
			instance = new UserSettingsManager.UserSettings();

			saveUserSettings();
		} else {
			// Read from file if it exists
			try (InputStream is = new BufferedInputStream(new FileInputStream(getSettingsPath().toString()));

					XMLDecoder d = new XMLDecoder(is)) {

				instance = (UserSettings) d.readObject();

			} catch (IOException e) {
				logger.error("Settings IO exception: %s", e.getMessage());
			}
		}
	}

	public static class UserSettings implements Serializable {
		private static final long serialVersionUID = 1920819660163308L;

		private String guiColor;

		public UserSettings() {
			// visible no argument constructor for java bean
			
			setGuiColor("black");
		}

		public UserSettings(String guiColor) {
			this.guiColor = guiColor;
		}

		public UserSettings(Color guiColor) {
			this.guiColor = String.format("#%02X%02X%02X", (int) (guiColor.getRed() * 255),
					(int) (guiColor.getGreen() * 255), (int) (guiColor.getBlue() * 255));
		}

		public String getGuiColor() {
			return guiColor;
		}

		public Color getFXColor() {
			return Color.web(guiColor);
		}

		public void setGuiColor(String guiColor) {
			this.guiColor = guiColor;
		}
	}
}
