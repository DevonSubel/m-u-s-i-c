/**
 * 
 */
package com.github.se307;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * @author Maxence Weyrich
 *
 */
public class SongDatabaseDriver {

	private static SongDatabaseDriver singleton;
	

	private final String UPDATE_STATEMENT_CONST = "UPDATE song SET ? = ? WHERE id = ?;";
	private final String DELETE_STATEMENT_CONST = "DELETE FROM song WHERE id = ?";
	private final String QUERY_FIELDS_CONST = "SELECT * FROM song WHERE id = ?";

	// private static final String CREATE_STATEMENT_CONST = "INSERT INTO song("
	
 	private Connection dbConnection;
	
	private PreparedStatement updateStatement;
	private PreparedStatement deleteStatement;
	private PreparedStatement queryStatement;
	
	
 	private Logger logger;
 	
 	/**
 	 * Initialize the Singleton by creating all the prepared statements that are used inside the SongSet classes
 	 */
	private SongDatabaseDriver() {
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		try {
			dbConnection = DatabaseDriver.getConnection();
			
			updateStatement = dbConnection.prepareStatement(UPDATE_STATEMENT_CONST);
			deleteStatement = dbConnection.prepareStatement(DELETE_STATEMENT_CONST);
			queryStatement = dbConnection.prepareStatement(QUERY_FIELDS_CONST);
					
			
		} catch(SQLException e) {
			logger.severe("Failed to load prepared statements for SongDatabaseDriver: " + e.getMessage());
		}
	}
	
	/**
	 * Update the song with the provided column with the specified value. 
	 * This method is synchronized (thread-safe).
	 * 
	 * @param colName 	the name of the column being updated
	 * @param value		the value to update the database with
	 * @param key		the key identifying the Song entry to update
	 * @return			true if the update was successful
	 */
	public synchronized boolean updateSong(String colName, Object value, long key) {
		try {
			this.updateStatement.setString(1, colName);
			this.updateStatement.setObject(2, value);
			this.updateStatement.setLong(3, key);
			
			this.updateStatement.executeUpdate();
		} catch(SQLException e) {
			logger.severe("Failed to execute updateSong prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Remove the song identified by the key from the database.
	 * This method is synchronized (thread-safe).
	 * @param songKey		the key identifier of the song being added
	 * @return				true if the update was successful
	 */
	public synchronized boolean removeSong(long songKey) {
		try {
			this.deleteStatement.setLong(1, songKey);
			
			this.deleteStatement.executeUpdate();
		} catch(SQLException e) {
			logger.severe("Failed to execute removeSong prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Returns a result set for the song identified by the songKey
	 * The result set contains all the fields for an entry in the song table
	 * @param songKey	the key of the song being retrieved
	 * @return			the ResultSet of the query. null is returned on error.
	 */
	public synchronized ResultSet getSong(long songKey) {
		ResultSet returnValue = null;
		try {
			this.queryStatement.setLong(1, songKey);
			returnValue = this.queryStatement.executeQuery();
		} catch(SQLException e) {
			logger.severe("Failed to execute getSong prepared statement: " + e.getMessage());
		}
		return returnValue;
	}
	
	/**
	 * Get the singleton instance. 
	 * A singleton is created if it does not already exist.
	 * @return 		a reference to the singleton
	 */
	public static synchronized SongDatabaseDriver getInstance() {
		if(SongDatabaseDriver.singleton == null) {
			SongDatabaseDriver.singleton = new SongDatabaseDriver();
		}
		return SongDatabaseDriver.singleton;
	}
}
 