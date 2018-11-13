package com.github.se307;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.nio.file.Path;

import java.util.logging.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
	public static final String LOG_FILE_OUTPUT = "music.log";
	
	private Logger logger;
	private Scene scene;
	
	
	
	/**
	 * Starting point of the application. 
	 * All initialization code should go here.
	 * The start function is automatically called once init() has completed.
	 */
	@Override 
	public void init() {

		Path resourcesDir = LocalResources.getResources().getUserResourcesDir();
		
		
		// Set up the logger for the MUSIC program 
		// All classes can access the logger using the getLogger()
		Path logFilePath = resourcesDir.resolve(LOG_FILE_OUTPUT);
		
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		FileHandler file_handler;
		
		try {
			file_handler = new FileHandler(logFilePath.toString(), false);
			logger.addHandler(file_handler);
			SimpleFormatter simple_formatter = new SimpleFormatter();
			file_handler.setFormatter(simple_formatter);
			
			logger.info("Logger has been successfully initialized...");
			
		} catch(IOException | SecurityException e) {
			e.printStackTrace();
			
			throw new RuntimeException();
		}


		// Set up the database
		// TODO: change to do the load checking, followed by data initialization if necessary
		logger.info("Loading database...");
		Connection connection = DatabaseDriver.getConnection();
		
		
		// Load user settings
		// TODO: read settings from file
	}
	
	
	/**
	 * Starting point for the application GUI.
	 */
	@Override 
    public void start(Stage stage) {
        // create the scene
        stage.setTitle("Web View");
        scene = new Scene(new Browser(),2560,1600, Color.web("#211E1E"));
        stage.setScene(scene);
        
//        URL css = this.getClass().getResource("/com/github/se307/App.css");
//        scene.getStylesheets().add(css.toString());
        stage.show();
    }
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
