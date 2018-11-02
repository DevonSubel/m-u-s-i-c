package com.github.se307;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
	public static void main(String[] args) {
		Connection connection = DatabaseDriver.getConnection();

		try {
			Statement stmnt = connection.createStatement();

			stmnt.executeUpdate("INSERT INTO song(name, artist_name, album_name) VALUES ('dye', 'tycho', 'awake')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}