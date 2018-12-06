package com.github.se307;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseDriver {
	private static final Logger logger = LogManager.getLogger();
	
	private static final String DB_FILE_NAME = "music.sqlite";
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
			logger.error("Failed to connect to database: %s", e.getMessage());

			throw new IllegalStateException(e);
		}
	}

	private static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private static void loadTables() {
		URL tableDefinitionsLocation = DatabaseDriver.class.getResource(TABLE_DEF_FILE_NAME);

		if (tableDefinitionsLocation == null) {
			logger.error("Unable to load table definition file resource: %s", DatabaseDriver.class.getResource("DatabaseDriver.class"));

			throw new IllegalStateException("Unable to get table definitions");
		}

		try (Statement createTables = connection.createStatement()){

			String tableDefinitions = readFile(tableDefinitionsLocation.getFile(), StandardCharsets.UTF_8);
			createTables.executeUpdate(tableDefinitions);
		} catch (IOException | SQLException e) {
			logger.error("Unable to create tables: %s", e.getMessage());

			throw new IllegalStateException(e);
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
