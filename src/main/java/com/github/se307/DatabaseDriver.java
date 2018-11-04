package com.github.se307;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {
	private static final String DB_FILE_NAME = "music.db";
	private static final String TABLE_DEF_FILE_NAME = "../../../table_definitions.sql";
	private static Connection connection;

	private DatabaseDriver() {
	}

	private static void createConnection() {
		try {
			LocalResources resources = LocalResources.getResources();
			Path userSettingsDir = resources.getUserResourcesDir();
			Path musicDB = userSettingsDir.resolve(DB_FILE_NAME);

			connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", musicDB.toString()));
		} catch (SQLException e) {
			System.err.println(e.getMessage());

			throw new RuntimeException(e);
		}
	}

	private static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private static void loadTables() {
		URL tableDefinitionsLocation = DatabaseDriver.class.getResource(TABLE_DEF_FILE_NAME);

		if (tableDefinitionsLocation == null) {
			System.out.println(DatabaseDriver.class.getResource("DatabaseDriver.class"));
			throw new RuntimeException("Unable to get table definitions");
		}

		try {
			Statement createTables = connection.createStatement();

			String tableDefinitions = readFile(tableDefinitionsLocation.getFile(), Charset.forName("UTF-8"));
			createTables.executeUpdate(tableDefinitions);
		} catch (IOException | SQLException e) {
			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		if (connection == null) {
			createConnection();
			loadTables();
		}

		return connection;
	}
}
