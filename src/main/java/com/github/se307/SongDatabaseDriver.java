/**
 * 
 */
package com.github.se307;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Maxence Weyrich
 *
 */
public class SongDatabaseDriver {

	private static SongDatabaseDriver singleton;

	public static final String NAME_F = "name";
	public static final String ARTIST_NAME_F = "artist_name";
	public static final String ALBUM_NAME_F = "album_name";
	public static final String SONG_LENGTH_F = "song_length";
	public static final String GENRE_ID_F = "genre_id";
	public static final String SONG_YEAR_F = "song_year";
	public static final String BPM_F = "bpm";
	public static final String ADDITIONAL_NOTES_F = "additional_notes";
	public static final String URI_F = "uri";

	private final String UPDATE_STATEMENT_CONST = "UPDATE song SET ? = ? WHERE id = ?;";
	private final String DELETE_STATEMENT_CONST = "DELETE FROM song WHERE id = ?";
	private final String QUERY_FIELDS_CONST = "SELECT * FROM song WHERE id = ?";
	private final String CREATE_STATEMENT_CONST = "INSERT INTO song(%s) VALUES(%s)";
	private final String QUERY_ALL_SONG_KEYS_CONST = "SELECT key FROM song";

	private Connection dbConnection;

	private PreparedStatement updateStatement;
	private PreparedStatement deleteStatement;
	private PreparedStatement queryStatement;
	private PreparedStatement queryAllStatement;

	private Logger logger;

	/**
	 * Initialize the Singleton by creating all the prepared statements that are
	 * used inside the SongSet classes
	 */
	private SongDatabaseDriver() {
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		try {
			dbConnection = DatabaseDriver.getConnection();

			updateStatement = dbConnection.prepareStatement(UPDATE_STATEMENT_CONST);
			deleteStatement = dbConnection.prepareStatement(DELETE_STATEMENT_CONST);
			queryStatement = dbConnection.prepareStatement(QUERY_FIELDS_CONST);
			queryAllStatement = dbConnection.prepareStatement(QUERY_ALL_SONG_KEYS_CONST);

		} catch (SQLException e) {
			logger.severe("Failed to load prepared statements for SongDatabaseDriver: " + e.getMessage());
		}
	}

	/**
	 * Create a new song using the specified field names and their associated
	 * values. The order of the field name and the values must be in the same order
	 * and of the same size This method is thread-safe (unsynchronized).
	 * 
	 * @param fieldList the list of field (column) names to insert
	 * @param paramList the list of the values to be inserted
	 * @return the primary key of the newly created song 0 will be returned if there
	 *         was an error adding the song to the database
	 */
	public long createSong(String[] fieldList, Object[] paramList) {
		long songPrimaryKey = 0;

		if (fieldList.length != paramList.length || fieldList.length <= 0) {
			logger.severe("Number of fields is empty or not matching with number of values");
			return songPrimaryKey;
		}

		// Insert the correct number of ? characters
		String qMarkString = "";
		for (int i = 0; i < fieldList.length - 1; i++) {
			qMarkString = qMarkString + "?, ";
		}
		qMarkString = qMarkString + "?";
		String createSongStatement = String.format(CREATE_STATEMENT_CONST, qMarkString, qMarkString);

		try {
			// Create the prepared statement
			PreparedStatement creationStatement = dbConnection.prepareStatement(createSongStatement,
					Statement.RETURN_GENERATED_KEYS);

			// Insert the values into the prepared statement
			for (int i = 1; i <= fieldList.length; i++) {
				creationStatement.setString(i, fieldList[i - 1]);
				creationStatement.setObject(i + fieldList.length, paramList[i - 1]);
			}

			// execute the statement and save the primary key
			creationStatement.executeUpdate();
			ResultSet keys = creationStatement.getGeneratedKeys();
			if (keys.next()) {
				songPrimaryKey = keys.getLong(1);
			}
		} catch (SQLException e) {
			logger.severe("Failed to execute createSong prepared statement: " + e.getMessage());
			return songPrimaryKey;
		}
		return songPrimaryKey;
	}

	/**
	 * Update the song with the provided column with the specified value. This
	 * method is synchronized (thread-safe).
	 * 
	 * @param colName the name of the column being updated
	 * @param value   the value to update the database with
	 * @param key     the key identifying the Song entry to update
	 * @return true if the update was successful
	 */
	public synchronized boolean updateSong(String colName, Object value, long key) {
		try {
			this.updateStatement.setString(1, colName);
			this.updateStatement.setObject(2, value);
			this.updateStatement.setLong(3, key);

			this.updateStatement.executeUpdate();
		} catch (SQLException e) {
			logger.severe("Failed to execute updateSong prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Remove the song identified by the key from the database. This method is
	 * synchronized (thread-safe).
	 * 
	 * @param songKey the key identifier of the song being added
	 * @return true if the update was successful
	 */
	public synchronized boolean removeSong(long songKey) {
		try {
			this.deleteStatement.setLong(1, songKey);

			this.deleteStatement.executeUpdate();
		} catch (SQLException e) {
			logger.severe("Failed to execute removeSong prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Returns a result set for the song identified by the songKey The result set
	 * contains all the fields for an entry in the song table
	 * 
	 * @param songKey the key of the song being retrieved
	 * @return the ResultSet of the query. null is returned on error.
	 */
	public synchronized ResultSet getSong(long songKey) {
		ResultSet returnValue = null;
		try {
			this.queryStatement.setLong(1, songKey);
			returnValue = this.queryStatement.executeQuery();
		} catch (SQLException e) {
			logger.severe("Failed to execute getSong prepared statement: " + e.getMessage());
		}
		return returnValue;
	}

	/**
	 * Returns a list of the keys of every song in the database. This method is
	 * thread-safe.
	 * 
	 * @return ArrayList containing all the keys.
	 */
	public ArrayList<Long> getAllSongs() {
		ArrayList<Long> songKeys = new ArrayList<Long>();

		try {
			ResultSet returnValue = this.queryAllStatement.executeQuery();
			while (returnValue.next()) {
				songKeys.add(returnValue.getLong(0));
			}

		} catch (SQLException e) {
			logger.severe("Failed to execute getSong prepared statement: " + e.getMessage());
			return null;
		}
		return songKeys;
	}

	/**
	 * Get the singleton instance. A singleton is created if it does not already
	 * exist.
	 * 
	 * @return a reference to the singleton
	 */
	public static synchronized SongDatabaseDriver getInstance() {
		if (SongDatabaseDriver.singleton == null) {
			SongDatabaseDriver.singleton = new SongDatabaseDriver();
		}
		return SongDatabaseDriver.singleton;
	}

	/**
	 * Return an String array with the fields used in the database.
	 * 
	 * @return array of field names
	 */
	public static String[] getFields() {
		return new String[] { NAME_F, ARTIST_NAME_F, ALBUM_NAME_F, SONG_LENGTH_F, GENRE_ID_F, SONG_YEAR_F, BPM_F,
				ADDITIONAL_NOTES_F, URI_F };
	}
}
