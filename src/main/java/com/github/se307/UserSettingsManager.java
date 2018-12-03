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

/**
 * The UserSettingsManager class is responsible for loading, saving, and
 * refreshing the in memory version of the user's settings. The settings are
 * persisted to disk at the "~/.m-u-s-i-c/settings.xml" path.
 * 
 * @author Declan
 *
 */
public class UserSettingsManager {

	private static final String USER_SETTINGS_FILENAME = "settings.xml";

	private static Logger logger = LogManager.getLogger();
	private static UserSettings instance;

	/**
	 * Private constructor to prevent initialization of the class
	 */
	private UserSettingsManager() {
		throw new IllegalArgumentException("UserSettingsManager cannot be instantiated");
	}
	
	
	/**
	 * Get the user's settings, loading from disk if necessary.
	 * 
	 * If the settings file does not exist, then it is created with the default
	 * settings, written to disk, and returned.
	 * 
	 * @return An up to date version of the user's settings
	 */
	public static UserSettings getUserSettings() {
		if (instance == null) {
			loadSettings();
		}

		return instance;
	}

	/**
	 * Save the user's settings to disk.
	 * 
	 * If the file does not exist it will be created, if the file does exist it
	 * will be overwritten.
	 */
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
	
	public static void resetUserSettings() {
		instance = null;
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

	/**
	 * The user's settings.
	 * 
	 * @author Declan
	 *
	 */
	public static class UserSettings implements Serializable {
		private static final long serialVersionUID = 1920819660163308L;

		private String guiColor;

		/**
		 * Create a user's settings with the default value: - Color is black
		 */
		public UserSettings() {
			// visible no argument constructor for java bean

			setGuiColor("black");
		}

		/**
		 * Create a user's settings with the provided fields.
		 * 
		 * @param guiColor
		 *            a string representation of a color
		 */
		public UserSettings(String guiColor) {
			this.guiColor = guiColor;
		}

		/**
		 * Get the user's gui color setting
		 * 
		 * @return a string representing the appropriate color of the gui
		 */
		public String getGuiColor() {
			return guiColor;
		}

		/**
		 * Get the user's gui color setting as a JavaFX color
		 * 
		 * @return the gui color setting
		 */
		public Color getFXColor() {
			return Color.web(guiColor);
		}

		/**
		 * Set the gui color to an appropriate string representation
		 * 
		 * @param guiColor the new gui color 
		 */
		public void setGuiColor(String guiColor) {
			this.guiColor = guiColor;
		}

		/**
		 * Set the gui color directly.
		 * 
		 * The Color object is converted to the web hex format (#rrggbb)
		 * 
		 * @param guiColor the new gui color
		 */
		public void setGuiColor(Color guiColor) {
			this.guiColor = String.format("#%02X%02X%02X", (int) (guiColor.getRed() * 255),
					(int) (guiColor.getGreen() * 255), (int) (guiColor.getBlue() * 255));
		}

		@Override
		public String toString() {
			return "UserSettings [guiColor=" + guiColor + "]";
		}
	}
}
