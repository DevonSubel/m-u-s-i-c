package com.github.se307;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
	public static final String LOG_FILE_OUTPUT = "music.log";
	private static final Logger logger = LogManager.getLogger();
	
	
	
	/**
	 * Starting point of the application. 
	 * All initialization code should go here.
	 * The start function is automatically called once init() has completed.
	 */
	@Override 
	public void init() {
		// Pre loading resources before UI starts
		LocalResources.getResources().getUserResourcesDir();

		// Set up the database
		// Followed by data initialization if necessary
		logger.info("Loading database...");
		DatabaseDriver.getConnection();
		
		
		// Load user settings
		// Read settings from file
		logger.info("Loading settings file");
		UserSettingsManager.getUserSettings();
	}
	
	
	/**
	 * Starting point for the application GUI.
	 */
	@Override 
    public void start(Stage stage) {
        // create the scene
		Scene scene;
		
        stage.setTitle("Web View");
        scene = new Scene(new Browser(), 2560, 1600, Color.web("#211E1E"));
        stage.setScene(scene);
        
        stage.show();
    }
	
	
	
	public static void main(String[] args) {		
		launch(args);
	}
}
