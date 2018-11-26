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
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Maxence Weyrich
 *
 */
public class SongSetDatabaseDriver {

	private static SongSetDatabaseDriver singleton;
	
	private final String CREATE_SONG_SET_CONST = "INSERT INTO song_set('name') VALUES(?)";
	private final String UPDATE_SONG_SET_CONST = "UPDATE song_set SET ? = ? WHERE id = ?";
 	private final String REMOVE_SONG_SET_CONST = "DELETE FROM song_set WHERE id = ?";
 	private final String QUERY_FIELD_SONG_SET_CONST = "SELECT ? FROM song_set WHERE id = ?";
 	
	private final String ADD_SONG_TO_SET_CONST = "INSERT INTO song_to_set('song_id', 'song_set_id') VALUES(?, ?)";
	private final String REMOVE_SONG_FROM_SET_CONST = "DELETE FROM song_to_set WHERE song_id = ? AND song_set_id = ?";

 	private final String QUERY_SONG_FROM_SET_CONST = "SELECT sts.song_id FROM song_to_set AS sts WHERE sts.song_set_id = ?";
	
 	
 	private Connection dbConnection;
 	
 	private PreparedStatement updateSongSet;
 	private PreparedStatement removeSongSet;
 	private PreparedStatement createSongSet;
 	private PreparedStatement querySongSetField;
 	private PreparedStatement addSongToSongSet;
 	private PreparedStatement removeSongFromSet;
 	private PreparedStatement querySongsFromSet;
 	
	
 	private Logger logger;
 	
 	/**
 	 * Initialize the Singleton by creating all the prepared statements that are used inside the SongSet classes
 	 */
	private SongSetDatabaseDriver() {
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		try {
			dbConnection = DatabaseDriver.getConnection();
			
			updateSongSet = dbConnection.prepareStatement(UPDATE_SONG_SET_CONST);
			removeSongSet = dbConnection.prepareStatement(REMOVE_SONG_SET_CONST);
			createSongSet = dbConnection.prepareStatement(CREATE_SONG_SET_CONST, Statement.RETURN_GENERATED_KEYS);
			querySongSetField = dbConnection.prepareStatement(QUERY_FIELD_SONG_SET_CONST);
			addSongToSongSet = dbConnection.prepareStatement(ADD_SONG_TO_SET_CONST);
			removeSongFromSet = dbConnection.prepareStatement(REMOVE_SONG_FROM_SET_CONST);
			querySongsFromSet = dbConnection.prepareStatement(QUERY_SONG_FROM_SET_CONST);
			
			
		} catch(SQLException e) {
			logger.severe("Failed to load prepared statements for SongSetDatabaseDriver: " + e.getMessage());
		}
	}
	
	/**
	 * Get the requested column for the SongSet identified by the key. 
	 * This method is synchronized (thread-safe).
	 * 
	 * @param colName 	the name of the column being queried
	 * @param key		the key identifying the SongSet to query
	 * @return			Object containing the resulting value
	 * 					null is returned on error or if the database entry was null
	 */
	public synchronized Object querySongSet(String colName, long key) {
		Object returnValue = null;
		try {
			this.querySongSetField.setString(1, colName);
			this.querySongSetField.setLong(2, key);
			
			ResultSet rs = this.querySongSetField.executeQuery();
			if(rs.next()) 
				returnValue = rs.getObject(1);
		} catch(SQLException e) {
			logger.severe("Failed to execute querySongSet prepared statement: " + e.getMessage());
		}
		return returnValue;
	}
	
	/**
	 * Update the SongSet with the provided column with the specified value. 
	 * This method is synchronized (thread-safe).
	 * 
	 * @param colName 	the name of the column being updated
	 * @param value		the value to update the database with
	 * @param key		the key identifying the SongSet to update
	 * @return			true if the update was successful
	 */
	public synchronized boolean updateSongSet(String colName, Object value, long key) {
		try {
			this.updateSongSet.setString(1, colName);
			this.updateSongSet.setObject(2, value);
			this.updateSongSet.setLong(3, key);
			
			this.updateSongSet.executeUpdate();
		} catch(SQLException e) {
			logger.severe("Failed to execute updateSongSet prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Add the song identified by the key to the SongSet in the database.
	 * This method is synchronized (thread-safe).
	 * 
	 * @param songKey		the key identifier of the song being added
	 * @param songSetKey	the key identifier of the SongSet 
	 * @return				true if the update was successful
	 */
	public synchronized boolean addSongToSongSet(long songKey, long songSetKey) {
		try {
			this.addSongToSongSet.setLong(1, songKey);
			this.addSongToSongSet.setLong(2, songSetKey);
			
			this.addSongToSongSet.executeUpdate();
		} catch(SQLException e) {
			logger.severe("Failed to execute addSongToSongSet prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Remove the song identified by the key from the SongSet in the database.
	 * This method is synchronized (thread-safe).
	 * @param songKey		the key identifier of the song being removed
	 * @param songSetKey	the key identifier of the SongSet 
	 * @return				true if the update was successful
	 */
	public synchronized boolean removeSongFromSet(long songKey, long songSetKey) {
		try {
			this.removeSongFromSet.setLong(1, songKey);
			this.removeSongFromSet.setLong(2, songSetKey);
			
			this.removeSongFromSet.executeUpdate();
		} catch(SQLException e) {
			logger.severe("Failed to execute removeSongFromSet prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Remove the SongSet identified by the key from the database.
 	 * This method is synchronized (thread-safe).
	 * @param songSetKey	key of the SongSet to remove
	 * @return				true if the update was successful
	 */
	public synchronized boolean removeSongSet(long songSetKey) {
		try {
			this.removeSongSet.setLong(1, songSetKey);
			
			this.removeSongSet.executeUpdate();
		} catch(SQLException e) {
			logger.severe("Failed to execute removeSongSet prepared statement: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Creates a new SongSet in the database.
	 * This method is synchronized (thread-safe).
	 * @return 		the primary key of the newly created SongSet.
	 * 				0 signifies that an error occurred and that it was not saved to the database
	 */
	public synchronized long createSongSet(String name) {
		long songSetKey = 0;
		try {
			this.createSongSet.setString(1, name);
			
			this.updateSongSet.executeUpdate();
			
			ResultSet keys = this.updateSongSet.getGeneratedKeys();
			if(keys.next()) {
				songSetKey = keys.getLong(1);
			}
		} catch(SQLException e) {
			logger.severe("Failed to insert a new SongSet into the database: " + e.getMessage());
		}
		return songSetKey;
	}

	/**
	 * Queries the database and creates the Song objects for the SongSet specified by the given key.
	 * This method is synchronized (thread-safe).
	 * @param songSetKey	the key of the SongSet
	 * @return				a list containing the Song objects inside the SongSet. 
	 * 						null is returned if the query fails.
	 */
	public synchronized List<Song> querySong(long songSetKey) {
		ArrayList<Song> songs = null;
		try {
			songs = new ArrayList<Song>();
			
			this.querySongsFromSet.setLong(1, songSetKey);
			ResultSet rs = this.querySongsFromSet.executeQuery();
			
			while(rs.next()) {
				songs.add(new Song(rs.getLong(1)));
			}
			
		} catch(SQLException e) {
			logger.severe("Failed to query the SongSet: " + e.getMessage());
		} 
		return songs;
	}

	/**
	 * Queries the database with the given query string and uses the result to construct a list
	 * of Song objects.
	 * 
	 * 
	 * The query must return a result set that contains the primary keys of the Song entries 
	 * in the song table. The best way to do this is to only select the song keys
	 * (e.g. SELECT sg.id FROM song AS sg ... ).  
	 * Note: Only the first column is observed to find the key for the Song object.
	 * 
	 * @param query		the query to execute
	 * @return			a list containing the Song objects from the query.
	 * 					null is returned if the query fails
	 */
	public List<Song> querySong(String query) {
		ArrayList<Song> songs = null;
		try {
			songs = new ArrayList<Song>();
			
			Statement stmt = dbConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				songs.add(new Song(rs.getLong(1)));
			}
			
		} catch(SQLException e) {
			logger.severe("Failed to query the song set: " + e.getMessage());
		} 
		return songs;
	}
	
	/**
	 * Get the singleton instance. 
	 * A singleton is created if it does not already exist.
	 * @return 		a reference to the singleton
	 */
	public static synchronized SongSetDatabaseDriver getInstance() {
		if(SongSetDatabaseDriver.singleton == null) {
			SongSetDatabaseDriver.singleton = new SongSetDatabaseDriver();
		}
		return SongSetDatabaseDriver.singleton;
	}
}
 