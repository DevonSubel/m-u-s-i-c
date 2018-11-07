package com.github.se307;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
	private Scene scene;
	
    @Override public void start(Stage stage) {
        // create the scene
        stage.setTitle("Web View");
        scene = new Scene(new Browser(),2560,1600, Color.web("#211E1E"));
        stage.setScene(scene);
        
//        URL css = this.getClass().getResource("/com/github/se307/App.css");
//        scene.getStylesheets().add(css.toString());
        stage.show();
    }
	
	public static void main(String[] args) {
		Connection connection = DatabaseDriver.getConnection();

		try {
			Statement stmnt = connection.createStatement();

			stmnt.executeUpdate("INSERT INTO song(name, artist_name, album_name) VALUES ('dye', 'tycho', 'awake')");
			
			launch(args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
