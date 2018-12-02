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
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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

	private static final String UPDATE_STATEMENT_CONST = "UPDATE song SET ? = ? WHERE id = ?;";
	private static final String DELETE_STATEMENT_CONST = "DELETE FROM song WHERE id = ?";
	private static final String QUERY_FIELDS_CONST = "SELECT * FROM song WHERE id = ?";
	private static final String CREATE_STATEMENT_CONST = "INSERT INTO song(%s) VALUES(%s)";
	private static final String QUERY_ALL_SONG_KEYS_CONST = "SELECT key FROM song";

	private Connection dbConnection;

	private PreparedStatement updateStatement;
	private PreparedStatement deleteStatement;
	private PreparedStatement queryStatement;
	private PreparedStatement queryAllStatement;

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Initialize the Singleton by creating all the prepared statements that are
	 * used inside the SongSet classes
	 */
	private SongDatabaseDriver() {
		try {
			dbConnection = DatabaseDriver.getConnection();

			updateStatement = dbConnection.prepareStatement(UPDATE_STATEMENT_CONST);
			deleteStatement = dbConnection.prepareStatement(DELETE_STATEMENT_CONST);
			queryStatement = dbConnection.prepareStatement(QUERY_FIELDS_CONST);
			queryAllStatement = dbConnection.prepareStatement(QUERY_ALL_SONG_KEYS_CONST);

		} catch (SQLException e) {
			logger.error("Failed to load prepared statements for SongDatabaseDriver: " + e.getMessage());
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
			logger.error("Number of fields is empty or not matching with number of values");
			return songPrimaryKey;
		}

		// Insert the correct number of ? characters
		StringBuilder qMarkString = new StringBuilder();
		for (int i = 0; i < fieldList.length - 1; i++) {
			qMarkString.append("?, ");
		}
		qMarkString.append("?");
		String createSongStatement = String.format(CREATE_STATEMENT_CONST, qMarkString.toString(), qMarkString.toString());

		PreparedStatement creationStatement = null;
		ResultSet keys = null;
		try {
			// Create the prepared statement
			creationStatement = dbConnection.prepareStatement(createSongStatement,
					Statement.RETURN_GENERATED_KEYS);

			// Insert the values into the prepared statement
			for (int i = 1; i <= fieldList.length; i++) {
				creationStatement.setString(i, fieldList[i - 1]);
				creationStatement.setObject(i + fieldList.length, paramList[i - 1]);
			}

			// execute the statement and save the primary key
			creationStatement.executeUpdate();
			keys = creationStatement.getGeneratedKeys();
			if (keys.next()) {
				songPrimaryKey = keys.getLong(1);
			}
		} catch (SQLException e) {
			logger.error("Failed to execute createSong prepared statement: " + e.getMessage());
			return songPrimaryKey;
		} finally {
			// Attempt to close the PreparedStatement resource
			try {
				if (creationStatement != null)
					creationStatement.close();
			} catch (SQLException e) {
				logger.error("Failed to close the PreparedStatment " + e.getMessage());
			}
			// Attempt to close the ResultSet resource
			try {
				if (keys != null)
					keys.close();
			} catch (SQLException e) {
				logger.error("Failed to close ResultSet resource " + e.getMessage());
			}
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
			logger.error("Failed to execute updateSong prepared statement: " + e.getMessage());
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
		} catch(SQLException e) {
			logger.error("Failed to execute removeSong prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Returns a result set for the song identified by the songKey The result set
	 * contains all the fields for an entry in the song table
	 * 
	 * @param songKey the key of the song being retrieved
	 * @return a SongBuilder with the values pre-populated with the results of the query. 
	 *			null is returned on error.
	 */
	public synchronized Song.SongBuilder getSong(long songKey) {
		Song.SongBuilder sb = new Song.SongBuilder();
		ResultSet rs = null;
		try {
			this.queryStatement.setLong(1, songKey);
			rs = this.queryStatement.executeQuery();
			
			sb.setSongName(rs.getString(NAME_F))
			  .setArtistName(rs.getString(ARTIST_NAME_F))
			  .setAlbumName(rs.getString(ALBUM_NAME_F));

			Integer songLength = rs.getInt(SONG_LENGTH_F);
			if (rs.wasNull())
				songLength = null;

			Integer genreID = rs.getInt(GENRE_ID_F);
			if (rs.wasNull())
				genreID = null;

			Integer songYear = rs.getInt(SONG_YEAR_F);
			if (rs.wasNull())
				songYear = null;

			Integer bpm = rs.getInt(BPM_F);
			if (rs.wasNull())
				bpm = null;

			sb.setSongLength(songLength)
			  .setGenreID(genreID)
			  .setSongYear(songYear)
			  .setBPM(bpm)
			  .setNotes(rs.getString(ADDITIONAL_NOTES_F))
			  .setURL(rs.getString(URI_F));
		} catch (SQLException e) {
			logger.error("Failed to execute getSong prepared statement: " + e.getMessage());
			return null;
		} finally {
			// Attempt to close the ResultSet resource
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				logger.error("Failed to close ResultSet resource " + e.getMessage());
			}
		}
		return sb;
	}

	/**
	 * Returns a list of the keys of every song in the database. This method is
	 * thread-safe.
	 * 
	 * @return List containing all the keys. An empty list is returned if an error was encountered.
	 */
	public List<Long> getAllSongs() {
		List<Long> songKeys = new ArrayList<Long>();

		ResultSet returnedValue = null;
		try {
			returnedValue = this.queryAllStatement.executeQuery();
			while (returnedValue.next()) {
				songKeys.add(returnedValue.getLong(1));
			}
		} catch (SQLException e) {
			logger.error("Failed to execute getSong prepared statement: " + e.getMessage());
			return Collections.emptyList();
		} finally {
			// Attempt to close the ResultSet resource
			try {
				if (returnedValue != null)
					returnedValue.close();
			} catch (SQLException e) {
				logger.error("Failed to close ResultSet resource " + e.getMessage());
			}
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
